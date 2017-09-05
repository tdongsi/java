---
layout: post
title: "Basic Jenkinsfile cookbook"
date: 2017-06-18 11:20:20 -0700
comments: true
categories: 
- Jenkins
- Groovy
---

This post shows how to customize standard Pipeline "steps" in Jenkinsfile (excluding their common usage).

<!--more-->

### `checkout`/`git` step

`scm` is the global variable for the current commit AND branch AND repository of Jenkinsfile. 
`checkout scm` means checking out all other files with same version as the Jenkinsfile associated with running pipeline.
To check out another repository, you need to specify the paremeters to `checkout` step.

``` groovy Checkout from another Git repo
checkout([$class: 'GitSCM', branches: [[name: '*/master']],
     userRemoteConfigs: [[url: 'http://git-server/user/repository.git']]])

// From README file.
checkout scm: [$class: 'MercurialSCM', source: 'ssh://hg@bitbucket.org/user/repo', clean: true, credentialsId: '1234-5678-abcd'], poll: false
// If scm is the only parameter, you can omit its name, but Groovy syntax then requires parentheses around the value:
checkout([$class: 'MercurialSCM', source: 'ssh://hg@bitbucket.org/user/repo'])

// Short hand form for Git
git branch: 'develop', url: 'https://github.com/WtfJoke/Any.git'
```

Reference:

* [`git` step](https://jenkins.io/doc/pipeline/steps/git/#git-git)
* [`git` example](https://stackoverflow.com/questions/14843696/checkout-multiple-git-repos-into-same-jenkins-workspace)
* [`checkout` step](https://jenkins.io/doc/pipeline/steps/workflow-scm-step/#checkout-general-scm)
* [`checkout` README](https://github.com/jenkinsci/workflow-scm-step-plugin/blob/master/README.md)

### `findFiles` step

Doing in Bash:

``` groovy Doing in Bash
    sh '''
    for file in target/surefire-reports/*.txt;
    do
        echo $file >> testresult
    done
    cat testresult
    '''
    def result = readFile "testresult"
```

``` groovy Doing in Groovy
    def files = findFiles(glob: 'target/surefire-reports/*.txt')
    for file in files:
      echo """
      ${files[0].name} ${files[0].path} ${files[0].directory} 
      ${files[0].length} ${files[0].lastModified}
      """
```

Reference:

* [`findFiles` step](https://jenkins.io/doc/pipeline/steps/pipeline-utility-steps/)
* Related: `readFile`, `writeFile`.

### `input` step

Simple `input` step can be used to ask for approval to proceed.
For asking input from a list of multiple choices, you can use the advanced version of input.

``` groovy Input from list of choices
       sh "source scripts/findCL.sh > choiceLists.txt"
       def choiceOptions = readFile "${env.WORKSPACE}/choiceLists.txt"
       def choice = input(
       id: 'CHOICE_LIST', message:'Choose a CL' , parameters: [
        [$class: 'ChoiceParameterDefinition', name:'CHOICE_LIST_SELECTED', description:'Select one', choices:choiceOptions]
      ])
```

Reference:

* [`input` step](https://jenkins.io/doc/pipeline/steps/pipeline-input-step/)

### `sendSlack` step



### References

* [Basic Jenkinsfile steps](https://jenkins.io/doc/pipeline/steps/)