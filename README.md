#Doctest gradle plugin
Plugin to use [doctest](https://github.com/devbliss/doctest) within a gradle build project.

[ ![Download](https://api.bintray.com/packages/devbliss/gradle-plugins/doctest-gradle-plugin/images/download.svg) ](https://bintray.com/devbliss/gradle-plugins/doctest-gradle-plugin/_latestVersion)


##Getting Started

There are two ways to integrate doctests into your build. Both are shown on the [Doctest Plugin Page](http://plugins.gradle.org/plugin/com.devbliss.doctest).

To configure the output directory of the  html doc you need to set the docHtmlDir path as a string. If it's not set you find the html output in build/reports/doctest.

```
doctest {
  docHtmlDir = "/doctest-api-doc/" //optional - default is build/reports/doctest
}
```

To run the doctests you need to start the "doctest" task

``` gradle doctest ```

##Tested with

JDK 8 and Gradle 2.x
