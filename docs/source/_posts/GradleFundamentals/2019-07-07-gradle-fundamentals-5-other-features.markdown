---
layout: post
title: "Gradle Fundamentals 5: Other Features"
date: 2019-07-07 20:18:50 -0700
comments: true
categories: 
- Gradle
---

This post corresponds to Section 5: "Miscellaneous Features" of [this course](https://learning.oreilly.com/videos/gradle-fundamentals/9781491937266/9781491937266-video224140).

<!--more-->

### Gradle daemon

Gradle daemon is a long-lived background process to keep things warm between two builds: JVM startup, TCP connection, memory caching, code optimizaiton (progressive than instantaneous).
While it is strongly recommended to have Gradle daemon running on local/developer box, it is NOT recommended running on CI server/Jenkins.
For CI builds, stability and predictability is more important than speed.
The daemon can kill itself if it is not used for too long.

The `--daemon` and `--no-daemon` CLI options enable and disable usage of the Daemon.

``` plain Stop all Gradle daemons
$ gradle --stop
```

### Other CLI options

``` plain List of options
$ gradle -h   

USAGE: gradle [option...] [task...]

-?, -h, --help          Shows this help message.
-a, --no-rebuild        Do not rebuild project dependencies.
```

Highlight of options:

* Logging: `--debug`, `--info`, and `--quiet` for different levels of logging.
* `-b`, `--build-file`: Specify the build file.
* `--continue`: any task that depends on the failed task will be skipped.
* `--dry-run`: to list tasks to be executed. Very useful when you use a third-party plugin and some files seem missing.
* `--profile`: Profile build execution time and generates a report in the `<build_dir>/reports/profile `directory.
* `--parallel`: Build independent builds in parallel.

### Build init plugin

`gradle init --type pom` to convert a Maven project to Gradle project.
