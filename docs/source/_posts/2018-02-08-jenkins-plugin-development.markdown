---
layout: post
title: "Jenkins plugin development"
date: 2018-02-08 00:07:01 -0800
comments: true
categories: 
---

How to create a Jenkins plugin.

<!--more-->

<!-- Photos:
IMG_[1110..1116]
-->

### Basic plugin

Reference:

* [Tutorial](https://github.com/MarkEWaite/hello-world-plugin/tree/jenkins-world-2017)
* [Video](https://www.youtube.com/watch?feature=player_embedded&v=azyv183Ua6U)

### Pipeline plugin

Reference:

* [Tutorial](https://github.com/jglick/wfdev/tree/pipeline)
* [Slides](https://github.com/jglick/wfdev/blob/master/preso.pdf)
* [Developer's guide](https://github.com/jenkinsci/pipeline-plugin/blob/master/DEVGUIDE.md)

### Blue Ocean plugin

``` plain Local development of Blue Ocean plugin
# this will build and run the plugin in local Jenkins
mvn install hpi:run
# this will recompile js & less while editing
npm run bundle:watch
```

***Gotcha***: extension changes won't update without a Jenkins restart.