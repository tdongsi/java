---
layout: post
title: "Groovy Fundamentals: Part 4: Closures & Operators"
date: 2017-12-08 17:17:09 -0800
comments: true
categories: 
- groovy
---

### Closures

``` groovy Iterating list with closure
List nums = [9,6,7,5,8,6,4,2,3]
for (int num: nums) {
    println num
}

// Pro: not much. You don't have to specify type
for (num in nums) {
    // println num
}

// Idiomatic in Groovy.
nums.each { num ->
    println num
}
nums.eachWithIndex { num, idx ->
    println "nums[$idx] == $num"
}
```

``` groovy Iterating map with closure
Map m = [a:1, b:2, c:2]

// Java idiom
for (String key : m.keySet()) {
    ...
}

// Groovy idiom
// One parameter closure gives Map.Entry
m.each { e ->
    println "${e.key} ${e.value}"
}
// Two parameter closure gives key, value
m.each { k, v ->

}
```

``` groovy collect method
println nums.collect { it * 2 } // map
    .findAll { it % 3 == 0 }    // filter
    .sum()                      // reduce

def factor = 2
println nums.collect { it*factor }
// Spread dot operator
println nums *. multiply(2)
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

### Operators

``` groovy Misc operators in Groovy
// Safe navigation ?.
class Department {
    Manager boss
}
class Manager {
    String name
}
Department d1 = new Department(boss: new Manager(name: 'ABC'))
Department d2 = new Department()
println d1 ?. boss ?. name
// Standard d2.boss.name will throw NPE.
println d2 ?. boss ?. name

// Spaceship operator: Comparable interface
println 1 <=> 2

// See Groovy truth
// Elvis operator
String input
String greet = 'Hello ' + (input ?: 'World')
println greet
```

Groovy truth:

* non-zero numbers
* non-null references
* non-empty strings
* non-empty collections
* regex with a match
* boolean true
