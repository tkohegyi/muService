DS18B20 Test head
=

It is perl based, so need to install the following perl modules:
* cpan HTTP::Server::Simple::CGI
* cpan File::Slurper
* cpan Config::Properties

Need to set the proper property file, sample content:
```
DS18B20.port=31820
DS18B20.id=<Location>-DS18B20-<Purpose>
DS18B20.comment=<PurposeText>
DS18B20.capability=0
DS18B20.sensorId=28-000005340000
```

Running: perl testHead-DS18B20.pl sample.properties

the "probe..." perl file can be used to test if the senson is properly will be detected by using the prepared property file. It loads the temperature value and writes to the console.




