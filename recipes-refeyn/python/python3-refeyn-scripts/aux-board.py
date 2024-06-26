#!/usr/bin/python3
"""
Usage:
    aux-board.py eeprom-dump [--sbc]
    aux-board.py eeprom-write --hw-rev=<rev> --pcb-id=<id> --pcb-name=<name> --board-serial=<id> [--sbc]
"""

import dataclasses
import datetime
import docopt
import struct

__version__ = (0, 1)
EEPROM_AUX_ADDR = "/sys/bus/i2c/devices/6-0050/eeprom"
EEPROM_SBC_ADDR = "/sys/bus/i2c/devices/4-0050/eeprom"
EEPROM_DATA_FORMAT = struct.Struct(">BB14x8s8s16s64s64sBBQ")
EEPROM_MMAP_VERSION = 0xab


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
        mmap_version, hw_rev, pcb_prefix, pcb_id, board_serial, pcb_name, sw_name, sw_major, sw_minor, write_date = EEPROM_DATA_FORMAT.unpack(data)
        return cls(
            mmap_version=mmap_version,
            hw_rev=hw_rev,
            pcb_id=(pcb_prefix.split(b"\0", 1)[0].decode("ascii"), pcb_id.split(b"\0", 1)[0].decode("ascii")),
            pcb_name=pcb_name.split(b"\0", 1)[0].decode("ascii"),
            board_serial=board_serial.split(b"\0", 1)[0].decode("ascii"),
            sw_name=sw_name.split(b"\0", 1)[0].decode("ascii"),
            sw_version=(sw_major, sw_minor),
            write_date=datetime.datetime.fromtimestamp(write_date)
        )

    def to_bytes(self) -> bytes:
        return EEPROM_DATA_FORMAT.pack(self.mmap_version, self.hw_rev, self.pcb_id[0].encode("ascii"), self.pcb_id[1].encode("ascii"), self.board_serial.encode("ascii"), self.pcb_name.encode("ascii"), self.sw_name.encode("ascii"), self.sw_version[0], self.sw_version[1], round(self.write_date.timestamp()))


def eeprom_dump(addr: str) -> None:
    with open(addr, "rb") as f:
        data = EEPROMData.from_bytes(f.read(EEPROM_DATA_FORMAT.size))

    print(data)

def eeprom_write(hw_rev: int, pcb_id: str, pcb_name: str, board_serial: str, addr: str) -> None:
    pcb_prefix, pcb_id = pcb_id.rsplit("-", 1)
    data = EEPROMData(
        mmap_version=EEPROM_MMAP_VERSION,
        hw_rev=hw_rev,
        pcb_id=(pcb_prefix + "-", pcb_id),
        pcb_name=pcb_name,
        board_serial=board_serial,
        sw_name="aux-board.py",
        sw_version=__version__,
        write_date=datetime.datetime.now()
    )
    with open(addr, "wb") as f:
        f.write(data.to_bytes())

if __name__ == "__main__":
    args = docopt.docopt(__doc__)
    if args["--sbc"]:
        addr = EEPROM_SBC_ADDR
    else:
        addr = EEPROM_AUX_ADDR

    if args["eeprom-dump"]:
        eeprom_dump(addr)
    elif args["eeprom-write"]:
        eeprom_write(int(args["--hw-rev"]), args["--pcb-id"], args["--pcb-name"], args["--board-serial"], addr)
