[![Build Status](https://travis-ci.com/mP1/j2cl-java-util-TimeZone.svg?branch=master)](https://travis-ci.com/mP1/j2cl-java-util-TimeZone.svg?branch=master)
[![Coverage Status](https://coveralls.io/repos/github/mP1/j2cl-java-util-TimeZone/badge.svg?branch=master)](https://coveralls.io/github/mP1/j2cl-java-util-TimeZone?branch=master)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
[![Language grade: Java](https://img.shields.io/lgtm/grade/java/g/mP1/j2cl-java-util-TimeZone.svg?logo=lgtm&logoWidth=18)](https://lgtm.com/projects/g/mP1/j2cl-java-util-TimeZone/context:java)
[![Total alerts](https://img.shields.io/lgtm/alerts/g/mP1/j2cl-java-util-TimeZone.svg?logo=lgtm&logoWidth=18)](https://lgtm.com/projects/g/mP1/j2cl-java-util-TimeZone/alerts/)



# java.util.TimeZone

This project aims to provide a mostly complete `java.util.Time` and `java.util.SimpleTimeZone` and possibly a few other
support classes, but some features have been removed.

Some portions of Apache Harmony have been used such as the definition of all TimeZones.

- Sub classing of `java.util.TimeZone` is not supported.
- `java.util.SimpleTimeZone` is not available.
- Serialization is not supported, and all support classes and forms including magic methods such as `writeReplace` are removed.
- `java.util.TimeZone#getDisplay` is not yet supported see [TimeZone#getDisplay](https://github.com/mP1/j2cl-java-util-TimeZone/issues/30)
- `java.util.TimeZone#getOffset` is not yet supported see [TimeZone#getOffset](https://github.com/mP1/j2cl-java-util-TimeZone/issues/26)
- `java.util.TimeZone#insDaylightTime` is not yet supported see [TimeZone#inDaylightTime](https://github.com/mP1/j2cl-java-util-TimeZone/issues/25)

See issues for a current list.


## Supported/Unsupported APIS

TODO



## Transpiling

The `j2cl-maven-plugin` will shade the source during the transpile phase, so `TimeZone`
is available to the runtime as `java.util.TimeZone` and something similar for `SimpleTimeZone`. 



## Getting the source

You can either download the source using the "ZIP" button at the top
of the github page, or you can make a clone using git:

```
git clone git://github.com/mP1/j2cl-java-util-TimeZone.git
```



### IDE build setup

Annotation processors from class path must be enabled as some tests test the output of generated classes.

The star/wildcard may need to be escaped with a backslash.

![Intellij -> System Preferences -> Annotation Processors](intellij-enable-annotation-processors.png)