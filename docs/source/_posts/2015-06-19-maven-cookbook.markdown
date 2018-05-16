---
layout: post
title: "Maven cookbook"
date: 2015-06-19 14:07:37 -0700
comments: true
categories:
- Maven 
---

This blog post lists out some common `mvn` commands.

<!--more-->

### Recipes

**Recipe 1**: `mvn` exit normally with test failures.

``` plain Exit normally with test failure
sh "${mvnHome}/bin/mvn -B -Dmaven.test.failure.ignore verify"
```

**Recipe 2**: Switch between corporate Nexus and Maven Central.

Corporate Nexus is specified in settings.xml. Some need to use Maven Central for personal projects.
To switch between them, create a [minimal settings.xml](https://maven.apache.org/settings.html) and specify with `mvn -s settings.xml`.

``` xml Minimal settings.xml
<settings xmlns="http://maven.apache.org/SETTINGS/1.0.0"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0
                      https://maven.apache.org/xsd/settings-1.0.0.xsd">
  <localRepository/>
  <interactiveMode/>
  <usePluginRegistry/>
  <offline/>
  <pluginGroups/>
  <servers/>
  <mirrors/>
  <proxies/>
  <profiles/>
  <activeProfiles/>
</settings>
```

Similarly, the `settings.xml` may need to be modified for specific environments, such as Jenkins.

``` xml Jenkins settings.xml
<settings>
  <pluginGroups>
    <pluginGroup>org.jenkins-ci.tools</pluginGroup>
  </pluginGroups>

  <profiles>
    <!-- Give access to Jenkins plugins -->
    <profile>
      <id>jenkins</id>
      <activation>
        <!-- change this to false, if you don't like to have it on per default -->
        <activeByDefault>true</activeByDefault> 
      </activation>
      <repositories>
        <repository>
          <id>repo.jenkins-ci.org</id>
          <url>https://repo.jenkins-ci.org/public/</url>
        </repository>
      </repositories>
      <pluginRepositories>
        <pluginRepository>
          <id>repo.jenkins-ci.org</id>
          <url>https://repo.jenkins-ci.org/public/</url>
        </pluginRepository>
      </pluginRepositories>
    </profile>
  </profiles>
  <mirrors>
    <mirror>
      <id>repo.jenkins-ci.org</id>
      <url>https://repo.jenkins-ci.org/public/</url>
      <mirrorOf>m.g.o-public</mirrorOf>
    </mirror>
  </mirrors>
</settings>
```

**Recipe 3**: Install a jar file into local repository. See [here](http://tdongsi.github.io/blog/2015/11/17/pushing-local-jar-file-into-your-local-maven-m2-repository/).

**Recipe 4**: Findbugs and Checkstyle. See [here](https://www.safaribooksonline.com/library/view/continuous-integration-ci/9781491986547/video307702.html).
Run `mvn clean install site` to generate Findbugs and Checkstyle reports. 

``` xml Adding Findbugs and Checkstyle
    <reporting>
        <plugins>
            <plugin>
                <groupId>org.codehaus.mojo</groupId>
                <artifactId>findbugs-maven-plugin</artifactId>
                <version>3.0.4</version>
                <configuration>
                    <xmlOutput>true</xmlOutput>
                    <findbugsXmlOutput>true</findbugsXmlOutput>
                    <findbugsXmlWithMessages>true</findbugsXmlWithMessages>
                </configuration>
            </plugin>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-checkstyle-plugin</artifactId>
                <version>2.17</version>
                <reportSets>
                    <reportSet>
                      <reports>
                        <report>checkstyle</report>
                      </reports>
                    </reportSet>
                </resportSets>
            </plugin>
        </plugins>
    </reporting>
```

### Recipe 5: "dependencyManagement" tag

From [here](https://stackoverflow.com/questions/2619598/differences-between-dependencymanagement-and-dependencies-in-maven).

In the parent POM, the main difference between the `<dependencies>` and `<dependencyManagement>` is this:

Artifacts specified in the `<dependencies>` section will ALWAYS be included as a dependency of the child module(s).

Artifacts specified in the `<dependencyManagement>` section, will only be included in the child module if they were also specified in the `<dependencies>` section of the child module itself. 
Why is it good you ask? because you specify the version and/or scope in the parent, and you can leave them out when specifying the dependencies in the child POM. 
This can help you use unified versions for dependencies for child modules, without specifying the version in each child module.


### Conventions

Maven follows the principle "Conventions over configurations".
While it helps to get you started quickly, it is hard to understand what's going on underneath without knowing the defaults and conventions.

#### [Plugins](https://www.safaribooksonline.com/library/view/apache-maven-cookbook/9781785286124/ch08s04.html) 

You will recall that when using the Maven JaCoCo plugin, to generate code coverage we had to explicitly specify the `projectId` and `artifactId` values of the plugin to it from the command line. 
However, for most other plugins, we specified the plugin name without additional information.
To avoid it for Jacoco, open the settings.xml file and add the following section:

``` xml
<pluginGroups>
    <pluginGroup>org.jacoco</pluginGroup>
</pluginGroups>
```

Run the following command on the same project for which you ran JaCoCo earlier:

``` plain
# Before
mvn clean org.jacoco:jacoco-maven-plugin:0.7.9:prepare-agent test org.jacoco:jacoco-maven-plugin:0.7.9:report

# After
mvn clean jacoco:prepare-agent test jacoco:report
```

What is going on? There are two types of Maven plugins, which are as follows:

* Plugins maintained by the Maven team itself (official plugins). These are in the default plugin groups **org.apache.maven.plugins** and **org.codehaus.mojo**.
* All other plugins (let's say third-party plugins).

All official plugins have the same `groupId`, namely **org.apache.maven.plugins**. 
They also have a convention for `artifactId`: "maven-${prefix}-plugin", where `prefix` stands for the plugin prefix, the short name to refer to the plugin.
The prefix used to reference the plugin can be customized as well. 
The prefix can be specified directly through the `goalPrefix` configuration parameter on the Maven-plugin-plugin of the plugin's pom file.
So, when we run mvn clean, Maven looks for the "maven-clean-plugin" in the **org.apache.maven.plugins** group.

What about third-party plugins? `pluginGroups` lets Maven know the `groupId` where it should search for additional plugins. 
So in the earlier case, Maven searched for plugins in the org.jacoco group.
Third-party plugins should be named differently from official plugins. 
The conventional way to define the `artifactId` for third-party plugins is "${prefix}-maven-plugin". 
When specified in this way, Maven automatically identifies the shortcut name for the plugin. 
In the earlier case, as the artifactId is jacoco-maven-plugin, the shortcut is jacoco.

Maven will always search specified pluginGroups before it searches the following default groups:

* org.apache.maven.plugins
* org.codehaus.mojo

Maven takes the first match for the shortcut that it finds. 
For instance, if there is a clean shortcut in a user-specified plugin in pluginGroups, it will take precedence over a Maven Clean plugin.

### Reference

* [settings.xml](https://maven.apache.org/settings.html)
* [resource directory](http://maven.apache.org/plugins/maven-resources-plugin/examples/resource-directory.html)
* [Apache Maven Cookbook](https://www.safaribooksonline.com/library/view/apache-maven-cookbook/9781785286124/ch08s04.html) by Raghuram Bharathan