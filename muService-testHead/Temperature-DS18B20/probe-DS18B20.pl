#!/usr/bin/perl
use strict;
use warnings FATAL => 'all';
use Config::Properties;
use File::Slurper 'read_text';

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

my $sensorId = $properties->getProperty('DS18B20.sensorId');
if (not defined $sensorId) {
    die "Cannot load SENSORID value! Exiting...\n";
}

my $temp = "N/A\n";
my @res = split(/\n/,`cat /sys/bus/w1/devices/$sensorId/w1_slave`);
if ($res[0]=~/YES$/) {
    print "\$res 1: " . $res[1] . "\n";
    if ($res[1] =~ /t=(\d+)/i) {
        print "\$1: " . $1 . "\n";
        my $temperature = ($1/1000);
        $temp = printf("%3.3f\n", $temperature);
    }
    if ($res[1] =~ /t=-(\d+)/i) {
        print "\$1: " . $1 . "\n";
        my $temperature = -($1/1000);
        $temp = printf("%3.3f\n", $temperature);
    }
}

print $temp;
