Test Head - WAN IP check - Unix
====================

This check the WAN IP of the test head, and uploads it to the server.
Files:
- muservice-wanip-renew - to be placed in /etc/cron.daily, ensures that at least a daily fresh report will be uploaded to the server.
- muservice-wanip-wanip - to be placed in /etc/cron.hourly, runs the WAN IP check hourly, and if change happens, notifies the server
- renew.sh - to be placed in /opt/mu-service/wanipcheck folder - it resets the WAN IP check, and ensures that at the next check the server will be updated
- wanip.sh - to be placed in /opt/mu-service/wanipcheck folder - does the WAN IP check and notifies the server about it as necessary - it uses wget and curl packages

