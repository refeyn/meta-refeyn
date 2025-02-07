do_compile:append() {
    sed -e 's/console=tty1//' -i boot.cmd
}
