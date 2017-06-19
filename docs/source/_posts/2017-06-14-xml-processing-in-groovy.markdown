---
layout: post
title: "XML processing in Groovy"
date: 2017-06-14 09:57:51 -0700
comments: true
categories: 
- Groovy
- TODO
---

This post discusses XML processing in Groovy.

<!--more-->

### XmlParser vs XmlSlurper

TODO

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
