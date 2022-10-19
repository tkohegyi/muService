DS18B20 Test head - Perl
=

The Temperature sensor requires some kernel module, so put these lines into /etc/modules file to load them at boot time:
w1-gpio
w1-therm


It is perl based, so need to install the following linux and perl modules:
* apt-get install perl
* apt-get install make
* cpan HTTP::Server::Simple::CGI
* cpan File::Slurper
* cpan Config::Properties

perl may use more memory than a test head may contain (like a Raspberry Pi 2B version), so can be used only when you have enough memory.

Need to set the proper property file, sample content:
```
DS18B20.port=31820
DS18B20.id=<Location>-DS18B20-<Purpose>
DS18B20.comment=<PurposeText>
DS18B20.capability=0
DS18B20.sensorId=28-000005340000
```

Running: perl testHead-DS18B20.pl sample.properties

the "probe..." perl file can be used to test if the sensor is properly will be detected by using the prepared property file. It loads the temperature value and writes to the console.




