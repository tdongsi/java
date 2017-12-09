---
layout: post
title: "Groovy Fundamentals: Part 4: Closures & Operators"
date: 2017-12-08 17:17:09 -0800
comments: true
categories: 
- groovy
---

### Closures

``` groovy Closure demo
// Demo
String HOMEPAGE = 'http://oreilly.com'
println HOMEPAGE.toURL().text.readLines() *. size()

```

### Geocoding demo: XML query and parsing

``` groovy
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

### ICNDB demo: JSON query and parsing

``` groovy
String JSON_BASE = 'http://api.icndb.com/jokes/random?'
// Only the POTUS can claim such hyperbole.
def params = [limitTo:'[nerdy]', firstName: 'Donald', lastName: 'Trump']
def qs = params.collect {k,v -> "$k=$v"}.join('&')
String jsonTxt = "$JSON_BASE$qs".toURL().text
// println jsonTxt
def json = new JsonSlurper().parseText(jsonTxt)
println json.value.joke
```

