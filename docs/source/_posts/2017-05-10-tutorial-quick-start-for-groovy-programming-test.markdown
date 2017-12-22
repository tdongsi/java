---
layout: post
title: "Tutorial: Quick-start for Groovy programming test"
date: 2017-05-10 00:07:18 -0700
comments: true
categories: 
- Groovy
- Tutorial
---

This post lists out steps to get started quickly for a **Groovy** programming test, using Eclipse and Maven.

<!--more-->

### Maven-based Java project

The simplest way is to generate a Maven-based Java project and add Groovy codes and dependencies to it. 
In IntelliJ IDEA, all you need to do is to add this into the pom.xml file:

``` xml Groovy dependencies
    <dependency>
      <groupId>org.codehaus.groovy</groupId>
      <artifactId>groovy-all</artifactId>
      <version>2.4.13</version>
    </dependency>
```

Then, you can start adding package and Groovy file, e.g., HelloWorld.groovy, into "src/main/groovy" folder. 
See [example commit](https://github.com/tdongsi/java/commit/3ce5202f9c575f735f14f095ea26759224316576).

In Eclipse, according to [this blog](http://www.s0hel.com/blog/523/compile-groovy-files-with-maven-in-your-java-project/), you might need the following plugins:

``` xml 
<build>
...
<plugins>
  <plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-compiler-plugin</artifactId>
    <version>3.1</version>
    <configuration>
      <compilerId>groovy-eclipse-compiler</compilerId>
    </configuration>
    <dependencies>
      <dependency>
        <groupId>org.codehaus.groovy</groupId>
        <artifactId>groovy-eclipse-compiler</artifactId>
        <version>2.8.0-01</version>
      </dependency>
      <dependency>
        <groupId>org.codehaus.groovy</groupId>
        <artifactId>groovy-eclipse-batch</artifactId>
        <version>2.4.13</version>
      </dependency>
    </dependencies>
  </plugin>
  ...
</plugins>
</build>
```

Note that the versions must match between `groovy-eclipse-batch` and `groovy-all`.

### Pure Groovy project

Alternatively, you can create pure Groovy project in IntelliJ IDEA.

### Reference

* [Compiling Groovy in Eclipse](http://www.s0hel.com/blog/523/compile-groovy-files-with-maven-in-your-java-project/)