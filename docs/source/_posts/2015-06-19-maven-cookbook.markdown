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

**Recipe 1**: `mvn` exit normally wieth test failures.

``` plain Exit normally with test failure
sh "${mvnHome}/bin/mvn -B -Dmaven.test.failure.ignore verify"
```
