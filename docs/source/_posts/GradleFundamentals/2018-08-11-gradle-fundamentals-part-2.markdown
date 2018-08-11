---
layout: post
title: "Gradle Fundamentals: Part 2"
date: 2018-08-11 03:03:46 -0700
comments: true
categories: 
- Gradle
- Groovy
---

This post corresponds to "Enough Groovy to Get By" section of [this course](https://www.safaribooksonline.com/videos/gradle-fundamentals/9781491937266).

<!--more-->

### Static vs Dynamic Typing

### POGOs

### Collections and Closures

### Google Geocoder sample

[Code](https://github.com/tdongsi/groovy/blob/develop/learn/src/my/safari/groovy/basic/Geocode.groovy).

``` groovy Geocoding example
String XML_BASE = 'https://maps.googleapis.com/maps/api/geocode/xml?'
List address = ['2700 Coast Ave', 'Mountain View', 'CA']

String encoded = address.collect {
    URLEncoder.encode(it, 'UTF-8')
}.join(',')

String queryString = "address=$encoded"
String url = "$XML_BASE$queryString"
// println url.toURL().text

def root = new XmlSlurper().parse(url)
def loc = root.result[0].geometry.location // Navigate DOM using dot
println "Coordinates: ${loc.lat}, ${loc.lng}"
```

``` groovy ICNDB example
import groovy.json.JsonSlurper

String JSON_BASE = 'http://api.icndb.com/jokes/random?'
// Only the POTUS can claim such hyperbole.
def params = [limitTo:'[nerdy]', firstName: 'Donald', lastName: 'Trump']
def qs = params.collect {k,v -> "$k=$v"}.join('&')
String jsonTxt = "$JSON_BASE$qs".toURL().text
// println jsonTxt
def json = new JsonSlurper().parseText(jsonTxt)
println json.value.joke

// Demo
String HOMEPAGE = 'http://oreilly.com'
println HOMEPAGE.toURL().text.readLines() *. size()
// println HOMEPAGE.toURL().text
```