---
layout: post
title: "Gradle cookbook"
date: 2017-05-11 10:20:39 -0700
comments: true
categories: 
- Gradle
---

### Basic tasks

``` plain 
./gradlew dependencies
# Subproject
./gradlew :subprojectName:dependencies
# Configuration
./gradlew dependencies --configuration testCompile
```

In the second command based on [this example](http://stackoverflow.com/questions/27763472/how-to-check-dependency-list-at-runtime-gradle), the root project might not have dependencies and you want to list dependencies on one of the subprojects.

In the third command based on [this example](https://discuss.gradle.org/t/how-to-find-which-dependency-is-fetching-a-certain-jar/7319/10), you want to see which task will fetch a certain JAR (e.g., velocity).

### Sample `build.gradle` file

Most of the tutorials show a very simplistic `build.gradle` file to illustrate the basics. 
However, starting from those simple build files will often lead to frequent major changes, coming from frequent Google and StackOverflow searches for how to do something (e.g., Nexus authentication).
This section shows how a `build.gradle` file looks like for a medium-size team working in industry.

``` gradle Sample build file
buildscript {
    repositories {
        maven {
            credentials {
                username nexusUsername
                password nexusPassword
            }
            url { nexusPublic }
        }
    }
    dependencies {
        classpath 'com.h2database:h2:1.3.170'
        classpath 'org.postgresql:postgresql:9.4-1201-jdbc41'
        classpath 'com.example.plugins:some-gradle-plugin:1.1.6'
    }
}

apply plugin: 'com.example.plugins'

subprojects {
    repositories {
        maven {
            credentials {
                username nexusUsername
                password nexusPassword
            }
            url { nexusPublic }
        }
    }

    apply plugin: 'groovy'

    group = 'com.example.app'

    dependencies {
        // Core
        compile ('org.apache.kafka:kafka_2.11:0.8.2.0') {
            exclude module: 'jms'
            exclude module: 'jmxtools'
            exclude module: 'jmxri'
            exclude group: 'org.slf4j'
            exclude module: 'junit'
        }

        compile 'com.amazonaws:aws-java-sdk:1.9.24'
        compile 'io.swagger:swagger-core:1.5.10'

        /*
         * junit is excluded in multiple places, because org.apache.cassandra.cassandra-all:2.1.9
         * includes JUnit 4.8.1 as a compile dependency, and our test framework needing 4.12
         */
        // Support
        compile ("org.apache.cassandra.cassandra-all:2.1.9") {
            exclude module: 'junit'
        }
        compile ("com.example.app:common:${commons_version}") {
            exclude module: 'junit'
        }
        compile "org.flywaydb:flyway-core:3.2.1"
        testCompile 'org.hsqldb:hsqldb:2.3.2'
        compile 'com.google.code.findbugs:findbugs:3.0.1'
    }

    // CassandraSetupTest-based tests fail for newer versions of Guava.
    configurations.all {
        resolutionStrategy {
            force 'com.google.guava:guava:18.0'
        }
    }
}

jacocoCoverage.limits =  [
    'instruction': 40,
    'line'       : 48,
    'complexity' : 30,
    'method'     : 55,
    'class'      : 70
]

project(':server') {
    mainClassName = 'com.example.app.ApiApplication'
    dependencies {
        compile 'com.exacttarget:partner-api:1.0.3'

        testCompile 'com.squareup.okhttp3:mockwebserver:3.2.0'
        testCompile 'org.apache.lucene:lucene-test-framework:5.3.1'
    }

    test {
        exclude '**/SomeClass.class'
        exclude '**/*IntegrationTest.*'
		exclude '**/schema/model/*MoreWildcard.class'
    }

    // generateSwagger is not a Test but depends on the testing framework
    task generateSwagger(type: Test, dependsOn: testClasses) {
        include '**/util/SwaggerFileGenerator.class'
        String swaggerPath = 'build/swagger-test.yaml'

        // Not considered "UP-TO-DATE" if the output file does not exist
        outputs.upToDateWhen {
            file('server/' + swaggerPath).exists()
        }
        systemProperty 'test.swagger.outfile', file(swaggerPath)
        doLast {
            println "\n\nSwagger documentation can be found in api/server/${swaggerPath}\n\n"
        }
    }


    checkstyle {
        checkstyleMain.exclude '**/db/migration/*.java' // Exclude flywaydb migrations: they violate type name checks
    }

    jacocoCoverage.limits =  [
        'instruction': 40,
        'line'       : 48,
        'complexity' : 30,
        'method'     : 55,
        'class'      : 70
    ]

}
```

#### Maven/Nexus authentication

http://stackoverflow.com/questions/12749225/where-to-put-gradle-configuration-i-e-credentials-that-should-not-be-committe

#### `buildscript` block

The difference https://discuss.gradle.org/t/what-is-difference-between-buildscript-classpath-and-dependencies-compile/4290/2

First of all, I assume you meant to include the "dependencies" block between "buildscript" and "classpath".

The "buildscript" block only controls dependencies for the buildscript process itself, not for the application code, which the top-level "dependencies" block controls.

For instance, you could define dependencies in "buildscript/classpath" that represent Gradle plugins used in the build process. Those plugins would not be referenced as dependencies for the application code.

Read the Gradle User Guide for more information (the PDF is easy to search).
