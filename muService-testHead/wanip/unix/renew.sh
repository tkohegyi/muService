#!/bin/bash

#this script removes all temp files, therefore the newxt wanip.sh run will recheck/renew the WAN IP address.
#this script should work via crontab in every day

#clean up
cd /opt/mu-service/wanipcheck
rm -f index.html
rm -f wget.tmp
rm -f myip.txt
rm -f *.old

exit 0
