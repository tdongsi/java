---
layout: post
title: "Gradle Fundamentals: Tasks"
date: 2019-07-07 18:56:56 -0700
comments: true
categories: 
- Gradle
---

This post corresponds to Section 4: "Custom Tasks" of [this course](https://learning.oreilly.com/videos/gradle-fundamentals/9781491937266/9781491937266-video224139).

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

