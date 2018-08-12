---
layout: post
title: "Gradle Fundamentals: Part 3"
date: 2018-08-11 03:04:31 -0700
comments: true
categories: 
- Gradle
---

This post corresponds to "Basic Project Builds" section of [this course](https://www.safaribooksonline.com/videos/gradle-fundamentals/9781491937266).

<!--more-->

### Java Projects

Output of `gradle build` for simple Java build file.

``` plain Java plugin
> cat build.gradle
apply plugin: 'java'

> gradle build
:compileJava NO-SOURCE
:processResources NO-SOURCE
:classes UP-TO-DATE
:jar
:assemble
:compileTestJava NO-SOURCE
:processTestResources NO-SOURCE
:testClasses UP-TO-DATE
:test NO-SOURCE
:check UP-TO-DATE
:build

BUILD SUCCESSFUL

Total time: 0.786 secs
```

Gradle's Build Init plugin is similar to Maven's `archetype` tasks.
It can be used to setup a Gradle project.

``` plain Build Init plugin
> gradle init --type java-library
:wrapper
:init

BUILD SUCCESSFUL

Total time: 0.967 secs
> ls
build           build.gradle    gradle          gradlew         gradlew.bat     settings.gradle src\

> tree src
src
├── main
│   └── java
│       └── Library.java
└── test
    └── java
        └── LibraryTest.java

4 directories, 2 files

> gradle build
:compileJava
Download https://jcenter.bintray.com/org/apache/commons/commons-math3/3.6.1/commons-math3-3.6.1.pom
Download https://jcenter.bintray.com/com/google/guava/guava/21.0/guava-21.0.pom
Download https://jcenter.bintray.com/com/google/guava/guava-parent/21.0/guava-parent-21.0.pom
Download https://jcenter.bintray.com/org/apache/commons/commons-math3/3.6.1/commons-math3-3.6.1.jar
Download https://jcenter.bintray.com/com/google/guava/guava/21.0/guava-21.0.jar
:processResources NO-SOURCE
:classes
:jar
:assemble
:compileTestJava
:processTestResources NO-SOURCE
:testClasses
:test
:check
:build

BUILD SUCCESSFUL

Total time: 6.218 secs

> tree build
build
├── classes
│   ├── main
│   │   └── Library.class
│   └── test
│       └── LibraryTest.class
├── libs
│   └── workspace.jar
├── reports
│   └── tests
│       └── test
│           ├── classes
│           │   └── LibraryTest.html
│           ├── css
│           │   ├── base-style.css
│           │   └── style.css
│           ├── index.html
│           ├── js
│           │   └── report.js
│           └── packages
│               └── default-package.html
├── test-results
│   └── test
│       ├── TEST-LibraryTest.xml
│       └── binary
│           ├── output.bin
│           ├── output.bin.idx
│           └── results.bin
└── tmp
    ├── compileJava
    ├── compileTestJava
    └── jar
        └── MANIFEST.MF

18 directories, 14 files
```

``` plain Gradle project properties
> gradle properties
:properties

------------------------------------------------------------
Root project
------------------------------------------------------------

allprojects: [root project 'workspace']
ant: org.gradle.api.internal.project.DefaultAntBuilder@42572e90
antBuilderFactory: org.gradle.api.internal.project.DefaultAntBuilderFactory@39495f8
archivesBaseName: workspace
...
```

Reference:

* [Java plugin](https://docs.gradle.org/3.5/userguide/java_plugin.html)
* [Build init plugin](https://docs.gradle.org/3.5/userguide/build_init_plugin.html)

### Groovy Projects

### DAG and Source Sets

### Project Properties and Dependencies

