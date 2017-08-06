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

**Recipe 1**: `mvn` exit normally wieth test failures.

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

**Recipe 3**: Install a jar file into local repository. See [here](http://tdongsi.github.io/blog/2015/11/17/pushing-local-jar-file-into-your-local-maven-m2-repository/).

### Reference

* [settings.xml](https://maven.apache.org/settings.html)
* [resource directory](http://maven.apache.org/plugins/maven-resources-plugin/examples/resource-directory.html)