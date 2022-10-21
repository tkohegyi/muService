DHT11 for Raspberry Pi 1B
===
Use Raspberry OS, and we will use python2

Packages required:
```
apt-get install build-essential python-dev python-setuptools
```
Then clone the Adafruit DHT lib and install
```
git clone https://github.com/tkohegyi/Adafruit_Python_DHT.git
cd Adafruit_Python_DHT
python setup.py install
```
DHT11 may be tested by calling `python Read_DHT.py 11 4` command (assuming that GPIO4 is used).

Update the `temp_hum_detect.sh` with the necessary data, and schedule it in crontab for every minute.