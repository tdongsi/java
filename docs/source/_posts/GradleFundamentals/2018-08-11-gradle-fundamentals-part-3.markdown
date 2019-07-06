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

### Basics

Some tasks are simple. 
Note that some tasks are derived from the following rules:

``` 
$ gradle tasks
:tasks
...
Rules
-----
Pattern: clean<TaskName>: Cleans the output files of a task.
Pattern: build<ConfigurationName>: Assembles the artifacts of a configuration.
Pattern: upload<ConfigurationName>: Assembles and uploads the artifacts belonging to a configuration.
```

### Java Projects

Output of `gradle build` for simple Java build file.

``` plain Java plugin
> cat build.gradle
apply plugin: 'java'

# Same effect
$ cat build.gradle
apply(plugin: 'java')

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

#### Project properties

The command `gradle properties` will list all the pre-defined properties of the top Project instance. 
Some of the interesting properties of the Project class are:

* `assemble`, `check`, `jar`, etc.: corresponding to defined tasks.
* `version`, `sourceCompatibility`, `targetCompatibility`: commonly overridden properties. 
* `ext`: Extra properties.

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
version: unspecified

BUILD SUCCESSFUL

Total time: 0.779 secs
```

For example, the following `build.gradle` will overwrite some of the base properties:

```
$ cat build.gradle
apply plugin: 'java'
version = '1.0'

> gradle properties
:properties

------------------------------------------------------------
Root project
------------------------------------------------------------
...
version: 1.0

BUILD SUCCESSFUL

Total time: 0.715 secs
```

Reference:

* [Java plugin](https://docs.gradle.org/3.5/userguide/java_plugin.html)
* [Build init plugin](https://docs.gradle.org/3.5/userguide/build_init_plugin.html)

### Groovy Projects

Examples:

* [quickstart](https://github.com/gradle/gradle/blob/master/subprojects/docs/src/samples/groovy/quickstart/build.gradle)
* [mixedJavaAndGroovy](https://github.com/gradle/gradle/blob/master/subprojects/docs/src/samples/groovy/mixedJavaAndGroovy/build.gradle)
* [customizedLayout](https://github.com/gradle/gradle/blob/master/subprojects/docs/src/samples/groovy/customizedLayout/build.gradle)

Reference:

* [Groovy plugin](https://docs.gradle.org/3.5/userguide/groovy_plugin.html)
* [Groovy Examples](https://github.com/gradle/gradle/tree/v3.5.1/subprojects/docs/src/samples/groovy)

### DAG and Source Sets

DAGs mentioned are the dependency graphs of tasks defined in Gradle Java and Groovy plugins.
For example, the tasks `check` and `assemble` must be executed before `build` task, as shown in [here](https://docs.gradle.org/3.5/userguide/java_plugin.html).

Source Sets are Gradle way to organize related source code and resources. 
Using source sets can also enable you to customize your project layout (e.g., Jenkins global library project).

### Project Properties and Dependencies

Each `build.gradle` file is used to instantiate a `Project` object.
During its life cycle, that object will try to create a `Settings` instance for the build.
It will evaluate `settings.gradle` script to configure that `Settings` object (more about its life cycle at [here](https://docs.gradle.org/3.5/dsl/org.gradle.api.Project.html)).

The documentation page has a list of default properties of `Project` class.
It should be noted that adding a plugin can add additional properties to `Project` class, as shown in the same page.
For example, `idea` plugin will add `idea` property for "IdeaModel".

``` groovy Different way to specify dependencies
dependencies {
    runtime group: 'org.springframework', name: 'spring-core', version: '2.5'
    runtime 'org.springframework:spring-core:2.5',
            'org.springframework:spring-aop:2.5'
    runtime(
        [group: 'org.springframework', name: 'spring-core', version: '2.5'],
        [group: 'org.springframework', name: 'spring-aop', version: '2.5']
    )
    runtime('org.hibernate:hibernate:3.0.5') {
        transitive = true
    }
    runtime group: 'org.hibernate', name: 'hibernate', version: '3.0.5', transitive: true
    runtime(group: 'org.hibernate', name: 'hibernate', version: '3.0.5') {
        transitive = true
    }
}
```

``` groovy Artifact only notation
dependencies {
    runtime "org.groovy:groovy:2.2.0@jar"
    runtime group: 'org.groovy', name: 'groovy', version: '2.2.0', ext: 'jar'
}
```

Check depdendencies with Gradle:

``` plain Gradle automatically expands "dep" for "dependencies" task
> gradle dep
:dependencies

------------------------------------------------------------
Root project
------------------------------------------------------------

api - API dependencies for source set 'main'. (n)
\--- org.apache.commons:commons-math3:3.6.1 (n)

apiElements - API elements for main. (n)
No dependencies

archives - Configuration for archive artifacts.
No dependencies

compile - Dependencies for source set 'main' (deprecated, use 'implementation ' instead).
No dependencies

compileClasspath - Compile classpath for source set 'main'.
+--- org.apache.commons:commons-math3:3.6.1
\--- com.google.guava:guava:21.0

compileOnly - Compile only dependencies for source set 'main'.
No dependencies

default - Configuration for default artifacts.
+--- org.apache.commons:commons-math3:3.6.1
\--- com.google.guava:guava:21.0

implementation - Implementation only dependencies for source set 'main'. (n)
\--- com.google.guava:guava:21.0 (n)

runtime - Runtime dependencies for source set 'main' (deprecated, use 'runtimeOnly ' instead).
No dependencies

runtimeClasspath - Runtime classpath of source set 'main'.
+--- org.apache.commons:commons-math3:3.6.1
\--- com.google.guava:guava:21.0

runtimeElements - Elements of runtime for main. (n)
No dependencies

runtimeOnly - Runtime only dependencies for source set 'main'. (n)
No dependencies

testCompile - Dependencies for source set 'test' (deprecated, use 'testImplementation ' instead).
No dependencies

testCompileClasspath - Compile classpath for source set 'test'.
+--- org.apache.commons:commons-math3:3.6.1
+--- com.google.guava:guava:21.0
\--- junit:junit:4.12
     \--- org.hamcrest:hamcrest-core:1.3

testCompileOnly - Compile only dependencies for source set 'test'.
No dependencies

testImplementation - Implementation only dependencies for source set 'test'. (n)
\--- junit:junit:4.12 (n)

testRuntime - Runtime dependencies for source set 'test' (deprecated, use 'testRuntimeOnly ' instead).
No dependencies

testRuntimeClasspath - Runtime classpath of source set 'test'.
+--- org.apache.commons:commons-math3:3.6.1
+--- com.google.guava:guava:21.0
\--- junit:junit:4.12
     \--- org.hamcrest:hamcrest-core:1.3

testRuntimeOnly - Runtime only dependencies for source set 'test'. (n)
No dependencies

(n) - Not resolved (configuration is not meant to be resolved)

BUILD SUCCESSFUL

Total time: 0.72 secs
```

Reference:

* [Project](https://docs.gradle.org/3.5/dsl/org.gradle.api.Project.html)
* [Dependency Management](https://docs.gradle.org/current/userguide/introduction_dependency_management.html)
* [Dependency types](https://docs.gradle.org/current/userguide/dependency_types.html)