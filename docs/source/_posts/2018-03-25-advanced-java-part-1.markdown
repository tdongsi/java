---
layout: post
title: "Advanced Java part 1"
date: 2018-03-25 14:35:11 -0700
comments: true
categories: 
- Java
- Book
---

Based on Part 1: "Interfaces, Inheritances, and Objects" of [SafariBooks's Advanced Java Development](https://www.safaribooksonline.com/library/view/advanced-java-development/9781491960400/video247566.html)
by Ken Kousen.

<!--more-->

### Default and static methods

#### Why static method in interface?

Before Java 8, it is hard to add static methods that work on objects of the same type. 
For example, `sort` method should work on all Collection objects. 
However, we don’t have that in Collection interface. 
We end up working around by adding all static methods into Collections (plural) class. 
That is how we end up with `Collections.sort()`.

After Java 8, the above problem is sorted out with static method in interfaces. 
For example, Stream is an interface but it has utility methods such as "Stream.of(a, b, c)" which is static method.

#### Default methods in interface

What if default methods in two interfaces have the same name and a class implements both interfaces? 
You have to override the method in the class to provide the specific implementation. 
Otherwise, you will get the compilation error.
Note that, it is different from two abstract classes since it is usually required (before Java 8) for the class to provide concrete implementation for an interface method.
You can still refer to the default methods in the interfaces as follows:

``` java Default methods with same name in interfaces
public interface Company {
    default String getName() { return "defaults.Company"; }
}

public interface Contractor {
    String getFirst();
    String getLast();
    default String getName() { return String.format("%s %s", getFirst(), getLast()); }
}

public class CompanyContractor implements Company, Contractor {

    ...

    @Override
    public String getName() {
        return Contractor.super.getName() + " at " + Company.super.getName();
    }
}
```

### Exceptions

The following exmample demonstrates how "try-with-resources" clauses can simplify codes that would be otherwise verbose before Java 7.
In this example, let's open a file and read it.
Although it uses Java 7's NIO classes, all exception-related tasks apply.

``` java 
    Path dir = Paths.get("src", "main", "java", "my", "learning", "advanced", "one");
    try {
        BufferedReader br = Files.newBufferedReader(dir.resolve("ExceptionDemo.java"));
        System.out.println(br.readLine());
    } catch (IOException e) {
        e.printStackTrace();
    }
```

The missing piece in the above code is that you need to properly close the file should any exception happens.
Now, the problem is that simply adding the following would not work since `br` is a local variable in `try` block.

``` java
finally {
    br.close();
}
```

In the end, you would end up with so much additional works just to make sure the file is closed should any exception happens, as shown in the following code.

* Declare a null `br` outside of try-catch block to make it visible in `finally` block.
* Add `finally` block to close the file.
* Additional null-check for `br` before closing the file.
* Additional try-catch block for closing the file in `finally` block.

``` java
    Path dir = Paths.get("src", "main", "java", "my", "learning", "advanced", "one");
    BufferedReader br = null;
    try {
        br = Files.newBufferedReader(dir.resolve("ExceptionDemo.java"));
        System.out.println(br.readLine());
    } catch (IOException e) {
        e.printStackTrace();
    } finally {
        if (br != null) {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
```

Such code is verbose and obfuscating the simple purpose of closing the file.
It is also distracting developers and reviewers from the main code of reading and processing the file.

With Java 7's "try-with-resources", the above problem is effectively solved:

``` java
    Path dir = Paths.get("src", "main", "java", "my", "learning", "advanced", "one");
    try( BufferedReader br = Files.newBufferedReader(dir.resolve("ExceptionDemo.java")) ) {
        System.out.println(br.readLine());
    } catch (IOException e) {
        e.printStackTrace();
    }
```

We only need to move the BufferedReader instatiation into the `try()` statement.
No additional line is required at all.
The only requirement is that the BufferedReader class has to implement AutoCloseable interface, which it does.

