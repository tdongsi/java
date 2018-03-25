---
layout: post
title: "Groovy Fundamentals: Part 2: Numbers & Strings"
date: 2017-12-08 17:16:49 -0800
comments: true
categories: 
- groovy
---


``` groovy Speak Groovy with Java accent
def void healthcheck(String endpoint, int timeoutInSeconds = 120, int expectedCode = 200) {
    println "Doing health check of " + endpoint

    int i = 0
    int code = 0
    while (code != expectedCode) {
        try {
            URL url = new URL(endpoint)
            HttpURLConnection connection = (HttpURLConnection) url.openConnection()
            connection.setRequestMethod("GET")
            connection.connect()
            code = connection.getResponseCode()
            println "Response code:" + code
        } catch (Exception e) {
            println e
        }

        Thread.sleep(1000)

        i += 1
        if (i == timeoutInSeconds) {
            throw new RuntimeException("Healthcheck of " + endpoint + " failed.")
        }
    }
}

def out = healthcheck("https://my.service.com/")
```