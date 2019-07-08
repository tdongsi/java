---
layout: post
title: "Gradle Fundamentals: Tasks"
date: 2019-07-07 18:56:56 -0700
comments: true
categories: 
- Gradle
---

This post corresponds to Section 4: "Custom Tasks" of [this course](https://learning.oreilly.com/videos/gradle-fundamentals/9781491937266/9781491937266-video224139).

Reference:

* [More about Tasks](https://docs.gradle.org/current/userguide/more_about_tasks.html)

<!--more-->

### Identifying, Defining, and Executing Tasks

Define [a task](https://docs.gradle.org/3.5/dsl/org.gradle.api.Task.html):

``` groovy Define a task
task myTask
task myTask { configure closure }
task myTask(type: SomeType)
task myTask(type: SomeType) { configure closure }
```

List a task:

``` plain List a custom task
$ cat build.gradle
...
task hello {
    description 'Hello'
    doLast {
        println 'Hello, World!'
    }
}
...

$ gradle help --task hello
:help
Detailed task information for hello

Path
     :hello

Type
     Task (org.gradle.api.Task)

Description
     Hello

Group
     -

BUILD SUCCESSFUL
```

### Conditional Task Execution

``` plain Conditional execution with predicate
$ cat build.gradle
...
task hello {
    description 'Hello'
    doLast {
        println 'Hello, World!'
    }
}

hello.dependsOn 'compileJava'
hello.onlyIf { !project.hasProperty('skipHello') }
...

$ gradle hello
:compileJava NO-SOURCE
:hello
Hello, World!

BUILD SUCCESSFUL

$ gradle hello -PskipHello
:compileJava NO-SOURCE
:hello SKIPPED

BUILD SUCCESSFUL
```

#### Up-to-date tasks (Incremental Build)

Besides explicit predicates, another strategy for skipping tasks is to check for inputs if it is updated, as discussed in [this section](https://docs.gradle.org/current/userguide/more_about_tasks.html#sec:up_to_date_checks).

``` groovy Before build.gradle
task transform {
    ext.srcFile = file('mountains.xml')
    ext.destDir = new File(buildDir, 'generated')
    doLast {
        println "Transforming source file."
        destDir.mkdirs()
        def mountains = new XmlParser().parse(srcFile)
        mountains.mountain.each { mountain ->
            def name = mountain.name[0].text()
            def height = mountain.height[0].text()
            def destFile = new File(destDir, "${name}.txt")
            destFile.text = "$name -> ${height}\n"
        }
    }
}
```

Using this naive task definition, the second run will do the same thing even though nothing is changed.

``` groovy After build.gradle
task transform {
    ext.srcFile = file('mountains.xml')
    ext.destDir = new File(buildDir, 'generated')

    // Define inputs and outputs
    inputs.file srcFile
    outputs.dir destDir

    doLast {
        println "Transforming source file."
        destDir.mkdirs()
        def mountains = new XmlParser().parse(srcFile)
        mountains.mountain.each { mountain ->
            def name = mountain.name[0].text()
            def height = mountain.height[0].text()
            def destFile = new File(destDir, "${name}.txt")
            destFile.text = "$name -> ${height}\n"
        }
    }
}
```

Now, Gradle knows which files to check to determine whether the task is up-to-date or not.

``` plain Gradle outputs
> gradle transform
:transform
Transforming source file.

> gradle transform
:transform UP-TO-DATE
```
