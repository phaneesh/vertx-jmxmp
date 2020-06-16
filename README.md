# Vert.x JMXMP Bundle [![Travis build status](https://travis-ci.org/phaneesh/vertx-jmxmp.svg?branch=master)](https://travis-ci.org/phaneesh/vertx-jmxmp)

This library adds support for jmx over jmxmp which is the only way to make jmx work on marathon 
This bundle compiles only on Java 8.
 
## Usage
This makes it possible to use JMX over JMXMP.
 
### Build instructions
  - Clone the source:

        git clone github.com/phaneesh/vertx-jmxmp

  - Build

        mvn install

### Maven Dependency
* Use the following maven dependency:
```
<dependency>
    <groupId>io.raven.vertx</groupId>
    <artifactId>vertx-jmxmp</artifactId>
    <version>3.9.1</version>
</dependency>
```

#### Deploy Vertical
```java
  JsonObject config = new JsonObject().put("jmxmp-port", 5050);
  DeploymentOptions deploymentOptions = new DeploymentOptions();
  deploymentOptions.setWorker(true);
  deploymentOptions.setInstances(1);
  vertx.deployVerticle("io.raven.vertx.jmxmp.JmxMpServer", new DeploymentOptions().setConfig(config));
```

## Note
* Make sure that the port that is being used is exposed in Dockerfile
* Make sure you define port in marathon deployment configuration
* Use these JVM arguments 
```
 -Dcom.sun.management.jmxremote.local.only=false -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false
``` 
* If you need remote debugging support add this JVM argument. Make sure port 5005 is exposed in Dockerfile and mapped in marathon deployment configuration
```
-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005
```
* Use the following commandline to start Jconsole with jmxmp support
```
jconsole -J-Djava.class.path=/usr/lib/jvm/java-8-oracle/lib/jconsole.jar:/usr/lib/jvm/java-8-oracle/lib/tools.jar:$HOME/.m2/repository/org/glassfish/main/external/jmxremote_optional-repackaged/5.0/jmxremote_optional-repackaged-5.0.jar
```
* Use the following commandline to start VisualVM with jmxmp support
```
jvisualvm --cp:a $HOME/.m2/repository/org/glassfish/main/external/jmxremote_optional-repackaged/5.0/jmxremote_optional-repackaged-5.0.jar
```
* To use it with JMC add the following line in the end in ```jmc.ini```
```
-Xbootclasspath/a:~/.m2/repository/org/glassfish/main/external/jmxremote_optional-repackaged/5.0/jmxremote_optional-repackaged-5.0.jar
```

* Service URL format
```
service:jmx:jmxmp://<host>:<port>
```