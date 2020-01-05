#!/usr/bin/perl
{
    package TestHeadDS18B20;

    use strict;
    use warnings FATAL => 'all';

    use HTTP::Server::Simple::CGI;
    use base qw(HTTP::Server::Simple::CGI);
    use Config::Properties;
    use File::Slurper 'read_text';

    my %dispatch = (
        '/getInformation' => \&resp_getInformation,
        '/getStatus'      => \&resp_getStatus,
        # ...
    );

    my ($cfg) = @ARGV;
    if (not defined $cfg) {
        die "Need property file information! Exiting...\n";
    }

    open my $cfh, '<', './' . $cfg or die "Unable to open property file! Exiting...";
    my $properties = Config::Properties->new();
    $properties->load($cfh);
    if (not defined $properties) {
        die "Cannot load property file! Exiting...\n";
    }
    my $id = $properties->getProperty('DS18B20.id');
    my $comment = $properties->getProperty('DS18B20.comment');
    my $capability = $properties->getProperty('DS18B20.capability');
    my $sensorId = $properties->getProperty('DS18B20.sensorId');

    if (not defined $id) {
        die "Cannot load ID value! Exiting...\n";
    }
    if (not defined $comment) {
        die "Cannot load COMMENT value! Exiting...\n";
    }
    if (not defined $capability) {
        die "Cannot load CAPABILITY value! Exiting...\n";
    }
    if (not defined $sensorId) {
        die "Cannot load SENSORID value! Exiting...\n";
    }

    sub handle_request {
        my $self = shift;
        my $cgi  = shift;

        my $path = $cgi->path_info();
        my $handler = $dispatch{$path};

        if (ref($handler) eq "CODE") {
            print "HTTP/1.0 200 OK\r\n";
            $handler->($cgi);

        } else {
            print "HTTP/1.0 404 Not found\r\n";
            print $cgi->header,
                $cgi->start_html('Not found'),
                $cgi->h1('Not found'),
                $cgi->end_html;
        }
    }

    sub resp_getInformation {
        my $cgi  = shift;   # CGI.pm object
        return if !ref $cgi;

        print $cgi->header('application/json'),'{ "id": "' . $id . '", "software": "testHead-DS18B20.pl", "version": "0.0", capabilityCode": ' . $capability . ', "type": "DS18B20" }';
    }

    sub get_Temperature {
        my $temperature = 0.0;
        my @res = split(/\n/,`cat /sys/bus/w1/devices/$sensorId/w1_slave`);
        if ($res[0]=~/YES$/) {
            if ($res[1] =~ /t=(\d+)/i) {
                $temperature = ($1/1000);
            }
            if ($res[1] =~ /t=-(\d+)/i) {
                $temperature = -($1/1000);
            }
        } else {
            return "N/A";
        }

        return $temperature;
    }

    sub resp_getStatus {
        my $cgi  = shift;   # CGI.pm object
        return if !ref $cgi;

        #get temperature value
        my $tempValue = get_Temperature();

        print $cgi->header('application/json'),'{ "id": "' . $id . '", "comment": "' . $comment . '", "status": "OK", "information": "' . $tempValue . '" }';
    }

}

# start the server on port $port
my ($cfg) = @ARGV;
if (not defined $cfg) {
    die "Need property file information! Exiting...\n";
}
open my $cfh, '<', './' . $cfg or die "Unable to open property file! Exiting...";
my $properties = Config::Properties->new();
$properties->load($cfh);
if (not defined $properties) {
    die "Cannot load property file! Exiting...\n";
}
my $port = $properties->getProperty('DS18B20.port');
if (not defined $port) {
    die "Cannot load PORT value! Exiting...\n";
}

my $pid = TestHeadDS18B20->new($port)->background();
print "Use 'kill $pid' to stop server.\n";