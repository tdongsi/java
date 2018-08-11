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

``` groovy Example Hello World build.gradle
// Not overriding any internal property
ext.person = 'Dobby'

task hello {
    doLast {
        println "Hello, $person!"
    }
}

task hi(dependsOn: 'hello') << {
    println 'Hello, World!'
}
```

``` plain
> gradle tasks --all
:tasks

------------------------------------------------------------
All tasks runnable from root project
------------------------------------------------------------

Build Setup tasks
-----------------
init - Initializes a new Gradle build.
wrapper - Generates Gradle wrapper files.

Help tasks
----------
buildEnvironment - Displays all buildscript dependencies declared in root project 'workspace'.
components - Displays the components produced by root project 'workspace'. [incubating]
dependencies - Displays all dependencies declared in root project 'workspace'.
dependencyInsight - Displays the insight into a specific dependency in root project 'workspace'.
dependentComponents - Displays the dependent components of components in root project 'workspace'. [incubating]
help - Displays a help message.
model - Displays the configuration model of root project 'workspace'. [incubating]
projects - Displays the sub-projects of root project 'workspace'.
properties - Displays the properties of root project 'workspace'.
tasks - Displays the tasks runnable from root project 'workspace'.

Other tasks
-----------
hello
hi

BUILD SUCCESSFUL

Total time: 0.691 secs
```

``` plain
> gradle hi
The Task.leftShift(Closure) method has been deprecated and is scheduled to be removed in Gradle 5.0. Please use Task.doLast(Action) instead.
        at build_6vy9edw0bhsp70x0atckfxbnz.run(/Users/tuecuong/dev/hub/java/docs/source/workspace/build.gradle:7)
:hello
Hello, Dobby!
:hi
Hello, World!

BUILD SUCCESSFUL

Total time: 0.688 secs
```

Left-shift operator `<<` is the alias to the `doLast` command.
Using `doLast` is preferrable since you can do more (e.g., setup, configuration) as opposed to actions only in `<<` clauses.

### Sample Build Files

In version 3+, the guides and samples are not included in the Gradle installation.
Instead, you can find it on

* [Samples as shown in video](https://github.com/gradle/gradle/tree/master/subprojects/docs/src/samples)
  * `userguide` folder > `tutorial` folder > sample build.gradle files.
  * Example: [helloEnhanced](https://github.com/gradle/gradle/blob/master/subprojects/docs/src/samples/userguide/tutorial/helloEnhanced/build.gradle).
* [Guide repo](https://github.com/gradle/guides) which directs to the Github Org
* [gradle-guides Github org](https://github.com/gradle-guides)

### Where to Find Answers

* [Blog](https://blog.gradle.org/)
* [Forum](https://discuss.gradle.org/)
* Stackoverflow
* [Book](https://gradle.org/books/)
  * Gradle in Action

