#!/usr/bin/python3
"""
Usage:
    aux-board.py eeprom-dump (carrier | aux | <name>) [--offset=<offset>]
    aux-board.py eeprom-write (carrier | aux | <name>) --hw-rev=<rev> --pcb-id=<id> --pcb-name=<name> --board-serial=<id> [--offset=<offset>]
"""

import dataclasses
import datetime
import docopt
import pathlib
import struct

__version__ = (0, 4)
EEPROM_AUX_NAME = "aux-eeprom"
EEPROM_AUX_ALT_NAME = "aux_eeprom_alt"
EEPROM_CARRIER_NAME = "carrier-eeprom"
EEPROM_DATA_FORMAT = struct.Struct(">BB14x8s8s16s64s64sBBQ")
EEPROM_MMAP_VERSION = 0xAB


@dataclasses.dataclass
class EEPROMData:
    mmap_version: int
    pcb_id: tuple[str, str]
    hw_rev: int
    pcb_name: str
    board_serial: str
    sw_name: str
    sw_version: tuple[int, int]
    write_date: datetime.datetime

    @classmethod
    def from_bytes(cls, data: bytes):
        (
            mmap_version,
            hw_rev,
            pcb_prefix,
            pcb_id,
            board_serial,
            pcb_name,
            sw_name,
            sw_major,
            sw_minor,
            write_date,
        ) = EEPROM_DATA_FORMAT.unpack(data)

        if mmap_version != EEPROM_MMAP_VERSION:
            print("Warning: Unrecognized memory map version, parsing may be incorrect")
        try:
            write_date_parsed = datetime.datetime.fromtimestamp(write_date)
        except OverflowError:
            print("Warning: Timestamp overflow")
            write_date_parsed = datetime.datetime.fromtimestamp(0)

        return cls(
            mmap_version=mmap_version,
            hw_rev=hw_rev,
            pcb_id=(
                pcb_prefix.split(b"\0", 1)[0].decode("ascii", errors="replace"),
                pcb_id.split(b"\0", 1)[0].decode("ascii", errors="replace"),
            ),
            pcb_name=pcb_name.split(b"\0", 1)[0].decode("ascii", errors="replace"),
            board_serial=board_serial.split(b"\0", 1)[0].decode(
                "ascii", errors="replace"
            ),
            sw_name=sw_name.split(b"\0", 1)[0].decode("ascii", errors="replace"),
            sw_version=(sw_major, sw_minor),
            write_date=write_date_parsed,
        )

    def to_bytes(self) -> bytes:
        return EEPROM_DATA_FORMAT.pack(
            self.mmap_version,
            self.hw_rev,
            self.pcb_id[0].encode("ascii", errors="replace"),
            self.pcb_id[1].encode("ascii", errors="replace"),
            self.board_serial.encode("ascii", errors="replace"),
            self.pcb_name.encode("ascii", errors="replace"),
            self.sw_name.encode("ascii", errors="replace"),
            self.sw_version[0],
            self.sw_version[1],
            round(self.write_date.timestamp()),
        )


def eeprom_dump(addr: pathlib.Path, offset: int) -> None:
    with open(addr, "rb") as f:
        f.seek(offset)
        data = EEPROMData.from_bytes(f.read(EEPROM_DATA_FORMAT.size))

    print(
        f"""
Memory map version: 0x{data.mmap_version:x}
PCB ID: {data.pcb_id[0]}{data.pcb_id[1]}-{data.hw_rev:02}
PCB name: {data.pcb_name}
PCB serial: {data.board_serial}
Programming SW name: {data.sw_name}
Programming SW version: {data.sw_version[0]}.{data.sw_version[1]}
Programmed at: {data.write_date}
""".strip()
    )


def eeprom_write(
    hw_rev: int, pcb_id: str, pcb_name: str, board_serial: str, addr: pathlib.Path, offset: int
) -> None:
    pcb_prefix, pcb_id = pcb_id.rsplit("-", 1)
    data = EEPROMData(
        mmap_version=EEPROM_MMAP_VERSION,
        hw_rev=hw_rev,
        pcb_id=(pcb_prefix + "-", pcb_id),
        pcb_name=pcb_name,
        board_serial=board_serial,
        sw_name="aux-board.py",
        sw_version=__version__,
        write_date=datetime.datetime.now(),
    )
    with open(addr, "wb") as f:
        f.seek(offset)
        f.write(data.to_bytes())


def find_i2c_addr(name: str) -> None:
    for i2c_device in pathlib.Path("/sys/bus/i2c/devices/").iterdir():
        if (
            (i2c_device / "eeprom").exists()
            and (i2c_device / "of_node/name").exists()
            and (i2c_device / "of_node/name").read_text().strip("\0") == name
        ):
            return i2c_device / "eeprom"
    raise RuntimeError(f"Could not find i2c device {name}")


if __name__ == "__main__":
    args = docopt.docopt(__doc__)
    if args["carrier"]:
        addr = find_i2c_addr(EEPROM_CARRIER_NAME)
    elif args["aux"]:
        try:
            addr = find_i2c_addr(EEPROM_AUX_NAME)
        except RuntimeError:
            print("Warning: EEPROM not found on normal interface, trying alternative interface")
            addr = find_i2c_addr(EEPROM_AUX_ALT_NAME)
    else:
        addr = find_i2c_addr(args["<name>"])

    print("Using EEPROM interface at", addr)

    if args["eeprom-dump"]:
        eeprom_dump(addr, offset=int(args["--offset"] or 0))
    elif args["eeprom-write"]:
        eeprom_write(
            int(args["--hw-rev"]),
            args["--pcb-id"],
            args["--pcb-name"],
            args["--board-serial"],
            addr,
            offset=int(args["--offset"] or 0)
        )
