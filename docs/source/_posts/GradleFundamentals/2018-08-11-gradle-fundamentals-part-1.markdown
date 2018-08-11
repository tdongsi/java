---
layout: post
title: "Gradle Fundamentals: Part 1"
date: 2018-08-11 02:58:33 -0700
comments: true
categories: 
- Gradle
---

This post corresponds to "Introduction" section of [this course](https://www.safaribooksonline.com/videos/gradle-fundamentals/9781491937266).

<!--more-->

### Installation and Configuration

Installing using `sdkman` (formerly GVM) is recommended.
For example, on macOS, you might not be able to specify version using `homebrew`.

``` plain
> sdk install gradle 3.5.1
> sdk use gradle 3.5.1
> sdk list gradle
================================================================================
Available Gradle Versions
================================================================================
     4.10-rc-1           4.0.1               2.10                1.8
     4.9                 4.0                 2.9                 1.7
     4.8.1           > * 3.5.1               2.8                 1.6
     4.8                 3.5                 2.7                 1.5
     4.7                 3.4.1               2.6                 1.4
     4.6                 3.4                 2.5                 1.3
     4.5.1               3.3                 2.4                 1.2
     4.5                 3.2.1               2.3                 1.1
     4.4.1               3.2                 2.2.1               1.0
     4.4                 3.1                 2.2                 0.9.2
     4.3.1               3.0                 2.1                 0.9.1
     4.3                 2.14.1              2.0                 0.9
     4.2.1               2.14                1.12                0.8
     4.2                 2.13                1.11                0.7
     4.1                 2.12                1.10
     4.0.2               2.11                1.9

================================================================================
+ - local version
* - installed
> - currently in use
================================================================================

> gradle -version

------------------------------------------------------------
Gradle 3.5.1
------------------------------------------------------------

Build time:   2017-06-16 14:36:27 UTC
Revision:     d4c3bb4eac74bd0a3c70a0d213709e484193e251

Groovy:       2.4.10
Ant:          Apache Ant(TM) version 1.9.6 compiled on June 29 2015
JVM:          1.8.0_172 (Oracle Corporation 25.172-b11)
OS:           Mac OS X 10.13.6 x86_64
```

### Documentation

Important docs:

* [User Guide](https://gradle.org/guides/)
* [DSL reference](https://docs.gradle.org/current/dsl/)

### Sample Build Files

### Where to Find Answers


