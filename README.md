See wiki for more information: https://github.com/estevaosaleme/PlaySEM_SERenderer/wiki


mvn package
java -Xbootclasspath/p:alpn-boot-$ALPN_VERSION.jar -jar target/jetty-http2-echo-server.jar

RestfulHttp2Service
VMarguments
-Xbootclasspath/p:libs/alpn-boot-8.1.9.v20160720.jar


mvn exec:java -Dexec.mainClass=com.hascode.tutorial.mqtt.Ma