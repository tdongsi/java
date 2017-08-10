---
layout: post
title: "Basic Jenkinsfile cookbook"
date: 2017-06-18 11:20:20 -0700
comments: true
categories: 
---

This post shows how to customize standard "steps" in Jenkinsfile.

<!--more-->

### `input` step

### `checkout` step

`scm` is the global variable for the current commit && branch && repository of Jenkinsfile. 
`checkout scm` means checking out all other files with same version as the Jenkinsfile associated with running pipeline.
To check out another repository, you need to specify the paremeters to `checkout` step.

``` groovy Checkout from another Git repo
checkout([$class: 'GitSCM', branches: [[name: '*/master']],
     userRemoteConfigs: [[url: 'http://git-server/user/repository.git']]])

// Short hand form
git branch: 'develop', url: 'https://github.com/WtfJoke/Any.git'
```

Reference:

* [`git` step](https://jenkins.io/doc/pipeline/steps/git/#git-git)
* [`git` example](https://stackoverflow.com/questions/14843696/checkout-multiple-git-repos-into-same-jenkins-workspace)
* [`checkout` step](https://jenkins.io/doc/pipeline/steps/workflow-scm-step/#checkout-general-scm)

### References

* [Basic Jenkinsfile steps](https://jenkins.io/doc/pipeline/steps/)