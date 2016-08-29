---
layout: post
title: "Tutorial: Quick-start for Timed programming test"
date: 2016-08-29 01:38:40 -0700
comments: true
categories: 
- Java
- Tutorial
- Salesforce
---

For timed programming tests, new test-takers usually underestimate how short two and a half hours can be.
Remember that 30 minutes of that will go into reading the problem instruction and another 10 to 30 minutes go into setting up before you can start writing the first line of code.
And if you are unlukcy, you need another 15-30 minutes to debug any issue that comes up. 
Most of the test takers are experienced and skilled programmers, but they tend to forget that many of those steps above are not needed in their every tasks.

This post lists out steps to get started quickly for a **Java** programming test, using Eclipse and Maven.

(1) Use this command to setup an Eclipse empty project.

``` plain Create an Eclipse project
mvn archetype:generate -DgroupId=my.interview -DartifactId=CompanyName -DarchetypeArtifactId=maven-archetype-quickstart -DinteractiveMode=false
```

(2) In Eclipse, go to File > Import > Existing Maven Project. Import the Eclipse project created above.

(3) Update pom.xml for "Executable jar file" + "specified Java version".

``` xml Add this into pom.xml
    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-assembly-plugin</artifactId>
                <executions>
                    <execution>
                        <goals>
                            <goal>attached</goal>
                        </goals>
                        <phase>package</phase>
                        <configuration>
                            <descriptorRefs>
                                <descriptorRef>jar-with-dependencies</descriptorRef>
                            </descriptorRefs>
                            <archive>
                                <manifest>
                                    <mainClass>com.intuit.sbg.qe.MainApp</mainClass>
                                </manifest>
                            </archive>
                        </configuration>
                    </execution>
                </executions>
            </plugin>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.1</version>
                <configuration>
                    <source>1.7</source>
                    <target>1.7</target>
                </configuration>
            </plugin>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-surefire-plugin</artifactId>
                <version>2.17</version>
                <configuration>
                    <suiteXmlFiles>
                        <suiteXmlFile>testng.xml</suiteXmlFile>
                    </suiteXmlFiles>
                </configuration>
            </plugin>
        </plugins>
    </build>
```

(4) Add logging. Ability to use logging frameworks shows off your skill and experience. 
More importantly, it allows to print you lots of information needed for debugging without worrying about cleaning up console output later.

* Create `resources` source folder in `src/main`.
* Add `log4j.properties`. For simplicity, only log information to console. 
    * Use `logger.debug` for printing debug information and turn on/off logging by setting `rootLogger` to `DEBUG`/`INFO`.
* You need to add into `pom.xml` dependencies for the three followings: slf4j-api (interface), slf4j-log4j12 (route slf4j calls to log4j), log4j (logging backend).

Use the following content for `log4j.properties`, modified from [this example](https://logging.apache.org/log4j/1.2/manual.html).

``` java log4j.properties file
# Set root logger level to DEBUG and its only appender to A1.
log4j.rootLogger= INFO, A1

# A1 is set to be a ConsoleAppender.
log4j.appender.A1= org.apache.log4j.ConsoleAppender

# A1 uses PatternLayout.
log4j.appender.A1.layout= org.apache.log4j.PatternLayout
log4j.appender.A1.layout.ConversionPattern= [%t][%-5p][%c] - %m%n
```
