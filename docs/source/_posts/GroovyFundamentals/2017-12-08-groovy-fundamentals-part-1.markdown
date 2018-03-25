---
layout: post
title: "Groovy Fundamentals: Part 1: Basics"
date: 2017-12-08 17:16:47 -0800
comments: true
categories: 
- groovy
---

This post corresponds to "Installing Groovy" section of [this course](https://www.safaribooksonline.com/library/view/groovy-programming-fundamentals/9781491926253/).

<!--more-->

### Anatomy of Groovy bytecode

There is no requirement of filename matching class name like in Java (e.g., Foo.java for Foo class).
However, the convention is that filesnames with lower cases and underscore are used for script (e.g., hello_world.groovy) and filesnames with camel cases are used for Groovy class (e.g., Foo.groovy for Foo class).

``` plain
tdongsi-ltm4:docs tdongsi$ groovy hello_world.groovy
Hello World


tdongsi-ltm4:docs tdongsi$ groovyc hello_world.groovy
tdongsi-ltm4:docs tdongsi$ ls
hello_world.class
hello_world.groovy
tdongsi-ltm4:docs tdongsi$ groovy hello_world
Hello World
```

The above code snippets show that you can run command `groovy` with source code directly, giving the impression of interpreting language.
However, what actually happens is that it compiles and runs the bytecode but does not save the byte code.
On the other hand, you can compile with `groovyc` command to obtain the bytecode and run like Java code.

``` plain Anatomy of bytecode from Groovy code
tdongsi-ltm4:docs tdongsi$ java hello_world
Error: Could not find or load main class hello_world

tdongsi-ltm4:docs tdongsi$ javap hello_world
Compiled from "hello_world.groovy"
public class hello_world extends groovy.lang.Script {
  public static transient boolean __$stMC;
  public hello_world();
  public hello_world(groovy.lang.Binding);
  public static void main(java.lang.String...);
  public java.lang.Object run();
  protected groovy.lang.MetaClass $getStaticMetaClass();
}
```

The above code snippets show that `hello_world.class` is also JVM bytecode.
Although, we cannot execute `java hello_world` directly, the `javap` command confirms that it is Java bytecode.
With the right class dependencies, provided in `groovy-all-2.4.8.jar` in `GROOVY_HOME`, we can execute the bytecode compiled from Groovy code using `java` code, as shown below.

``` plain Exceute Groovy code with Java
tdongsi-ltm4:docs tdongsi$ which groovyc
/usr/local/bin/groovyc
tdongsi-ltm4:docs tdongsi$ ls -l /usr/local/bin/groovyc
lrwxr-xr-x  1 tdongsi  staff  34 Feb 10  2017 /usr/local/bin/groovyc -> ../Cellar/groovy/2.4.8/bin/groovyc

tdongsi-ltm4:docs tdongsi$ java -cp /usr/local/Cellar/groovy/2.4.8/libexec/embeddable/groovy-all-2.4.8.jar:. hello_world
Hello World
```

The implication is that, in any production system or development environment, you can add/execute any Groovy code by simply adding a single jar file.
For example, in Maven projects, all you need to do is to add this into the pom.xml file:

``` xml Groovy dependencies
    <dependency>
      <groupId>org.codehaus.groovy</groupId>
      <artifactId>groovy-all</artifactId>
      <version>2.4.13</version>
    </dependency>
```

After that, you can start adding package and Groovy file, e.g., HelloWorld.groovy, into "src/main/groovy" folder. 
See [example commit](https://github.com/tdongsi/java/commit/3ce5202f9c575f735f14f095ea26759224316576).

### Default imports

In Groovy, if you don't add any `import` statements, you get the following package by default:

* java.lang.*
* java.util.*
* java.net.*
* java.io.*
* java.math.BigInteger
* java.math.BigDecimal
* groovy.lang.*
* groovy.util.*

### `def` keyword

In Groovy, a variable can be dynamic type, using `def` keyword, or static type (Java-style).

``` groovy Dynamic and weak typing
def x = 1
println x.class.name
x = 'abc'
println x.class.name
```

The best practice is to use static typing when you know what type a variable is at the time of writing.
