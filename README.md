[![Build Status](https://travis-ci.com/mP1/java-util-TimeZone-j2cl.svg?branch=master)](https://travis-ci.com/mP1/java-util-TimeZone-j2cl.svg?branch=master)
[![Coverage Status](https://coveralls.io/repos/github/mP1/java-util-TimeZone-j2cl/badge.svg?branch=master)](https://coveralls.io/github/mP1/java-util-TimeZone-j2cl?branch=master)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
[![Language grade: Java](https://img.shields.io/lgtm/grade/java/g/mP1/java-util-TimeZone-j2cl.svg?logo=lgtm&logoWidth=18)](https://lgtm.com/projects/g/mP1/java-util-TimeZone-j2cl/context:java)
[![Total alerts](https://img.shields.io/lgtm/alerts/g/mP1/java-util-TimeZone-j2cl.svg?logo=lgtm&logoWidth=18)](https://lgtm.com/projects/g/mP1/java-util-TimeZone-j2cl/alerts/)



# java.util.TimeZone j2cl

This project aims to provide a mostly complete `java.util.Time` and `java.util.SimpleTimeZone` and possibly a few other
support classes.

Some portions of Apache Harmony have been used such as the definition of all TimeZones.



## Transpiling

The `j2cl-maven-plugin` will repackage the source during the transpile phase, so `walkingkooka.javautiltimezonej2cl.java.util.TimeZone`
is available to the runtime as `java.util.TimeZone` and something similar for `walkingkooka.javautiltimezonej2cl.java.util.SimpleTimeZone`. 



## Removed functionality

- Sub classing of `TimeZone` not supported.
- `SimpleTimeZone` ctors package private preventing creation with custom values.



### Serialization

Serialization is not supported, and all support classes and forms including magic methods such as `writeReplace` are removed.



## Getting the source

You can either download the source using the "ZIP" button at the top
of the github page, or you can make a clone using git:

```
git clone git://github.com/mP1/java-util-TimeZone-j2cl.git
```
