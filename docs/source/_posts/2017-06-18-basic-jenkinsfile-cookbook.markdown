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

### `podTemplate` step

``` groovy Kubernetes plugin
podTemplate(label:'base-agent', containers: [
    containerTemplate(name: 'maven', 
        image: 'ops0-artifactrepo1-0-prd.data.sfdc.net/tdongsi/matrix-jenkins-aqueduct-agent:13',
        workingDir: '/home/jenkins',
        volumes: [hostPathVolume(mountPath: '/srv/jenkins', hostPath: '/usr/local/npm'),
        secretVolume(mountPath: '/etc/mount2', secretName: 'my-secret')],
        imagePullSecrets: 'sfregistry')
]) {
    node('base-agent') {
        stage('Checkout') {
            checkout scm
        }

        stage('main') {
            sh 'java -version'
            sh 'mvn -version'
            sh 'python -V'
        }
        
        input 'Finished with K8S pod?'
    }
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

``` groovy Binding secret to $username:$password
node {
  withCredentials([usernameColonPassword(credentialsId: 'mylogin', variable: 'USERPASS')]) {
    sh '''
      set +x
      curl -u $USERPASS https://private.server/ > output
    '''
  }
}
```

For secret file, the file will be passed into some secret location and that secret location will be bound to some variable.
If you want the secret files in specific locations, the workaround is to create symlinks to those secret files.

``` groovy Binding secret file
        withCredentials( [file(credentialsId: 'ajna-host-cert', variable: 'HOST_CERT'),
                        file(credentialsId: 'ajna-host-key', variable: 'HOST_KEY'),
                        file(credentialsId: 'ajna-cert-ca', variable: 'CERT_CA')
                        ]) 
        {
            sh """
                mkdir download
                ln -s ${env.HOST_CERT} download/hostcert.crt
                ln -s ${env.HOST_KEY} download/hostcert.key
                ln -s ${env.CERT_CA} download/ca.crt
            """

            // The Python script read those files download/*.* by default
            sh "python python/main.py"
        }
```

### References

* [Basic Jenkinsfile steps](https://jenkins.io/doc/pipeline/steps/)