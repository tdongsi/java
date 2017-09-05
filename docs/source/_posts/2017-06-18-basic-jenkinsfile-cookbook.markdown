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

### `junit` step

JUnit tests + PMD, FindBugs, CheckStyle. 
In Blue Ocean interface, these will be displayed in a separate tab.

``` groovy Related steps
stage('JUnit-Reports'){
    junit allowEmptyResults: true, testResults: '**/build/test-results/*.xml'
}

stage('FindBugs-Reports'){
    step([$class: 'FindBugsPublisher', canComputeNew: false, defaultEncoding: '', excludePattern: '', healthy: '', includePattern: '', pattern: '**/build/reports/findbugs/*.xml', unHealthy: ''])
}

stage('PMD-Reports'){
    step([$class: 'PmdPublisher', canComputeNew: false, defaultEncoding: '', healthy: '', pattern: '**/build/reports/pmd/*.xml', unHealthy: ''])
}

stage('CheckStyle-Reports'){
    step([$class: 'CheckStylePublisher', canComputeNew: false, defaultEncoding: '', healthy: '', pattern: '**/build/reports/checkstyle/*.xml', unHealthy: ''])
}
```

### `sendSlack` step

Standard Jenkinsfile for testing Slack

``` groovy Jenkinsfile
node('test-agent') {
    stage('Checkout') {
        checkout scm
    }
    
    stage('Main') {
        withCredentials([string(credentialsId: 'matrixsfdc-slack', variable: 'TOKEN')]) {
            slackSend ( teamDomain: 'matrixsfdc', channel: '#jenkins-pcloud', token: env.TOKEN,
                   baseUrl: 'https://matrixsfdc.slack.com/services/hooks/jenkins-ci/',
                   color: '#FFFF00', 
                   message: "STARTED: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})"
                   )
        }
    }
    
    input 'Finished with K8S pod?'
}
```

### `withCredentials` step

There are different variations of `withCredentials` step.
The most common ones are:

``` groovy
TODO
```

### References

* [Basic Jenkinsfile steps](https://jenkins.io/doc/pipeline/steps/)