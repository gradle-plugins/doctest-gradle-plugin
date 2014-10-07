#Doctest gradle plugin
Plugin to use doctest within a gradle build project.

##Getting Started

```gradle

buildscript {
  repositories {
    mavenLocal()
    jcenter()
  }
  
  dependencies {
    classpath "com.devbliss.doctest:doctest-gradle-plugin:0.1"
  }
}

doctest {
  docHtmlDir = "/doctest-api-doc/" //optional - default is build/reports/doctest
}

```

To run the doctests you need to start the "doctest" task

``` gradle doctest ```

##Tested with

JDK 8 and Gradle 2.x
