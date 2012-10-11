# Multisets

## Project Description

The collection classes in the standard library of the [Scala programming
language](http://www.scala-lang.org) has sequences, sets, maps, and several
other collection types, but it does not currently have Multisets. The goal of
the project is to write, document and test a complete set of APIs and
implementations of Multisets that fit seamlessly into the Scala collection
library. Both immutable and mutable versions of Multisets will be developed and
various implementations will be compared using empirical data of their
performance. The goal is to submit a SIP document adding Multisets to Scala at
the end of the project and as such comprehensive testing is required.

*Semester Project by Mads Hartmann Jensen, fall 2012.*

## Dependencies 

- [ScalaCheck](https://github.com/rickynils/scalacheck)

## Building

The project is build using [SBT](http://www.scala-sbt.org/release/docs/home.html). 

### Code Coverage

In an SBT session, run:

    > clean 
    > scct:test 

And then in your shell

    > open target/scala-2.9.2/coverage-report/index.html