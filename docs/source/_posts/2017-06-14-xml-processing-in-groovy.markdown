---
layout: post
title: "XML processing in Groovy"
date: 2017-06-14 09:57:51 -0700
comments: true
categories: 
- Groovy
---

This post discusses XML processing in Groovy.

<!--more-->

### Parsing: XmlParser vs XmlSlurper

Both are in `groovy.util` packages and both have the same approach to parse an xml: both are based on `SAX` (low memory footprint) and both can update/transform the XML.

Based on this [StackOverflow answer](https://stackoverflow.com/questions/7558019/groovy-xmlslurper-vs-xmlparser), when to use `XmlParser` versus `XmlSlurper` is as follows:

* `XmlSlurper`: when you want to transform an existing XML document to another.
* `XmlSlurper`: when you just want to read a few nodes since `XmlSlurper` evaluates the structure lazily.
* `XmlParser`: when you want to update and read at the same time.

#### Example usage:

``` groovy XmlSlurper
def text = '''
    <list>
        <technology>
            <name>Groovy</name>
        </technology>
    </list>
'''

def list = new XmlSlurper().parseText(text) 

assert list instanceof groovy.util.slurpersupport.GPathResult 
assert list.technology.name == 'Groovy'
```

``` groovy XmlParser
def list = new XmlParser().parseText(text) 

assert list instanceof groovy.util.Node 
assert list.technology.name.text() == 'Groovy'
```

Another option is DOMCategory:

``` groovy DOMCateogry
def reader = new StringReader(CAR_RECORDS)
def doc = DOMBuilder.parse(reader) 
def records = doc.documentElement

use(DOMCategory) { 
    assert records.car.size() == 3
}
```

### GPath navigation

From [here](http://groovy-lang.org/processing-xml.html):

``` groovy GPath example
static final String books = '''
    <response version-api="2.0">
        <value>
            <books>
                <book available="20" id="1">
                    <title>Don Xijote</title>
                    <author id="1">Manuel De Cervantes</author>
                </book>
                <book available="14" id="2">
                    <title>Catcher in the Rye</title>
                   <author id="2">JD Salinger</author>
               </book>
               <book available="13" id="3">
                   <title>Alice in Wonderland</title>
                   <author id="3">Lewis Carroll</author>
               </book>
               <book available="5" id="4">
                   <title>Don Xijote</title>
                   <author id="4">Manuel De Cervantes</author>
               </book>
           </books>
       </value>
    </response>
'''

def response = new XmlSlurper().parseText(books)

def book = response.value.books.book[0] 
def bookAuthorId1 = book.@id 
def bookAuthorId2 = book['@id'] 

assert bookAuthorId1 == '1'

// .'*' could be replaced by .children()
def catcherInTheRye = response.value.books.'*'.find { node->
    // node.@id == 2 could be expressed as node['@id'] == 2
    node.name() == 'book' && node.@id == '2'
}

assert catcherInTheRye.title.text() == 'Catcher in the Rye'

// .'**' could be replaced by .depthFirst()
def bookId = response.'**'.find { book->
    book.author.text() == 'Lewis Carroll'
}.@id

assert bookId == 3

// find(Closure cl) finds just the first occurrence. To find all titles:
def titles = response.'**'.findAll{ node-> node.name() == 'title' }*.text()

assert titles.size() == 4
```

As you can see there are two types of notations to get attributes, the

* direct notation with `@nameoftheattribute`
* map notation using `['@nameoftheattribute']`

### Writing XML

``` groovy Standard usage of MarkupBuilder
def writer = new StringWriter()
def xml = new MarkupBuilder(writer) 

xml.records() { 
    car(name:'HSV Maloo', make:'Holden', year:2006) {
        country('Australia')
        record(type:'speed', 'Production Pickup Truck with speed of 271kph')
    }
    car(name:'Royale', make:'Bugatti', year:1931) {
        country('France')
        record(type:'price', 'Most Valuable Car at $15 million')
    }
}

def records = new XmlSlurper().parseText(writer.toString()) 

assert records.car.first().name.text() == 'HSV Maloo'
assert records.car.last().name.text() == 'Royale'
```

See documentation for the following use cases:

* Namespace aware
* Generate elements inside XML document.

Using `StreamingMarkupBuilder` is very similar to using `MarkupBuilder`. 
The `bind` method returns a `Writable` instance that can be used to stream the markup.

``` groovy Standard usage of StreamingMarkupBuilder
def xml = new StreamingMarkupBuilder().bind { 
    records {
        car(name:'HSV Maloo', make:'Holden', year:2006) { 
            country('Australia')
            record(type:'speed', 'Production Pickup Truck with speed of 271kph')
        }
        car(name:'P50', make:'Peel', year:1962) {
            country('Isle of Man')
            record(type:'size', 'Smallest Street-Legal Car at 99cm wide and 59 kg in weight')
        }
        car(name:'Royale', make:'Bugatti', year:1931) {
            country('France')
            record(type:'price', 'Most Valuable Car at $15 million')
        }
    }
}

def records = new XmlSlurper().parseText(xml.toString()) 

assert records.car.size() == 3
assert records.car.find { it.@name == 'P50' }.country.text() == 'Isle of Man'
```

#### MarkupBuilderHelper

This helper normally can be accessed from within an instance of class groovy.xml.MarkupBuilder or an instance of groovy.xml.StreamingMarkupBuilder.
This helper can be accessed as `mkp` property.

``` groovy Using mkp for comments and escape in XML
def xmlWriter = new StringWriter()
def xmlMarkup = new MarkupBuilder(xmlWriter).rules {
    mkp.comment('THIS IS THE MAIN RULE') 
    rule(sentence: mkp.yield('3 > n')) 
}

assert xmlWriter.toString().contains('3 &gt; n')
assert xmlWriter.toString().contains('<!-- THIS IS THE MAIN RULE -->')
```

### Code recipes

The input XML file is based on typical Maven "settings.xml" file.

**Recipe 1**: Read, transform, and write to file. Based on [this](https://stackoverflow.com/questions/2245641/load-modify-and-write-an-xml-document-in-groovy).

``` groovy XML transform to file
import groovy.xml.StreamingMarkupBuilder
import groovy.xml.XmlUtil

def transformXml(String filePath, String username, String password) {
  String TMP_PATH = '/tmp/temp.xml'
  
  def inFile = new File( filePath )
  def outFile = new File( TMP_PATH )
  def xml = new XmlSlurper(false, false).parse(inFile)
  
  xml.servers.server.each { node ->
    node.username = username
    node.password = password
  }
  
  def outBuilder = new StreamingMarkupBuilder()
  def outWriter = outFile.newWriter()

  // Option 1: Write XML to one line
  outWriter << outBuilder.bind{ mkp.yield xml }
  // Option 2: Pretty print XML
  XmlUtil.serialize( xml, outWriter )
  
  return TMP_PATH
}
```

For overwriting the original file (based on [this](https://stackoverflow.com/questions/18385062/writing-updated-xml-to-originally-parsed-file)):

``` groovy Write the transformed XML to original file
inFile.withWriter { outWriter ->
    XmlUtil.serialize( new StreamingMarkupBuilder().bind{ mkp.yield xml }, outWriter )
}
```

**Recipe 2**: Read, transform, and write to string.

``` groovy XML transform to String
import groovy.xml.XmlUtil

@NonCPS
def transformXml(String xmlContent, String username, String password) {
  def xml = new XmlSlurper(false, false).parseText(xmlContent)
  
  echo 'Start tranforming XML'
  xml.servers.server.each { node ->
    node.username = username
    node.password = password
  }
  
  def outWriter = new StringWriter()
  XmlUtil.serialize( xml, outWriter )
  return outWriter.toString()
}
```

### Tips

* `XmlSlurper(false, false)` is used due to [this](https://stackoverflow.com/questions/9197588/tag0-namespace-added-for-elements-in-default-namespace)

### References

* [Processing XML](http://groovy-lang.org/processing-xml.html)
