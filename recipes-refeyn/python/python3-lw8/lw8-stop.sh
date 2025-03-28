if [ "$SERVICE_RESULT " != "success" ]; then
    echo 255 > /sys/class/leds/rgb:indicator/brightness
    echo timer > /sys/class/leds/rgb:indicator/trigger
    echo "255 0 0" > /sys/class/leds/rgb:indicator/multi_intensity
fi
