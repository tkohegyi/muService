This is only for testing/demo purposes.
Our keystore and jetty entry password is the same: Rf7856op

Search for jetty-util-*.jar file in Gradle's local lib cache.
Create OBF password to be placed in jetty setup java:
java -cp jetty-util-9.2.27.v20190403.jar org.eclipse.jetty.util.security.Password Rf7856op
place OBF part into WebAppServer.java
(+ build/release)

set property useHttps=true in xxx.conf.properties

+1. In case you are using local App server, you may instruct Chrome to accept incesure localhost calls:
chrome://flags/#allow-insecure-localhost

