DS18B20 Test head - Unix/Raspberry Pi
=
Ensure that 1-wire tools can be used, so put "dtoverlay=w1-gpio" to /boot/config.txt file.
The Temperature sensor requires some kernel modules loaded, so put these lines into /etc/modules file to load them at boot time:

```
w1-gpio
w1-therm
```

It is bash based, so alll what we need is curl module in addition:
* apt-get install curl

Then: 
- configure the temp_detect.sh file
- add it to crontab



