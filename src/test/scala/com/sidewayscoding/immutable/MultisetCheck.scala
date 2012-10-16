package com.sidewayscoding.immutable

import org.scalacheck.Properties
import org.scalacheck.Prop._
import org.scalacheck._
import org.scalacheck.Gen._
import Arbitrary.arbitrary
import org.scalacheck.util.Buildable
import com.sidewayscoding.MultisetBuilder

object MultisetCheck extends Properties("Multiset") {

  import com.sidewayscoding._

  property("size") = forAll { (elems: List[String]) =>
    Multiset(elems:_*).size == elems.size
  }

  property("adding an item should increase the size of the multiset") = forAll { 
    (ms: Multiset[String], s: String) =>
      (ms + s).size == ms.size + 1
  }

  property("adding an item should increase it's multiplicity") = forAll { 
    (ms: Multiset[String], s: String) =>
      (ms + s).multiplicity(s) == ms.multiplicity(s) + 1
  }

  property("removing existing item should decrease size") = forAll(genMultisetWithString) {
    ((ms: Multiset[String], s: String) =>
      ((ms.removed(s)).size) == (ms.size - 1) ).tupled
  }

  property("removing existing item should decrease multiplicity") = forAll(genMultisetWithString) {
    ((ms: Multiset[String], s: String) =>
      (ms.removed(s)).multiplicity(s) == ms.multiplicity(s) - 1 ).tupled
  }

  property("intersection with empty multiset") = forAll {
    (ms: Multiset[String]) =>
      ms.intersect(Multiset[String]()).size == 0
  }

  property("union with empty multiset") = forAll {
    (ms: Multiset[String]) =>
      ms.union(Multiset[String]()).size == ms.size
  }

  // TODO: find a nice way to test the following: 
  // def multiplicities = delegate.mapValues(_.size)
  // def copies: Map[A, Seq[A]] = delegate
  // def get(a: A): Seq[A] = delegate.getOrElse(a, Nil)

  // TODO: De Morgan's Law
  
}