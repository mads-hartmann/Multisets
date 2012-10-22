package com.sidewayscoding.immutable

import org.scalacheck.Properties
import org.scalacheck.Prop._
import org.scalacheck._
import org.scalacheck.Gen._
import Arbitrary.arbitrary
import org.scalacheck.util.Buildable
import com.sidewayscoding.MultisetBuilder

object FullMultisetCheck extends Properties("FullMultiset") {

  import com.sidewayscoding._

  property("size") = forAll { (elems: List[String]) =>
    FullMultiset(elems: _*).size == elems.size
  }

  property("adding an item should increase the size of the multiset") = forAll {
    (ms: FullMultiset[String], s: String) =>
      (ms + s).size == ms.size + 1
  }

  property("adding an item should increase it's multiplicity") = forAll {
    (ms: FullMultiset[String], s: String) =>
      (ms + s).multiplicity(s) == ms.multiplicity(s) + 1
  }

  property("removing existing item should decrease size") = forAll(genMultisetWithString) {
    ((ms: FullMultiset[String], s: String) =>
      ((ms.removed(s)).size) == (ms.size - 1) ).tupled
  }

  property("removing existing item should decrease multiplicity") = forAll(genMultisetWithString) {
    ((ms: FullMultiset[String], s: String) =>
      (ms.removed(s)).multiplicity(s) == ms.multiplicity(s) - 1 ).tupled
  }

  property("intersection with empty multiset") = forAll {
    (ms: FullMultiset[String]) =>
      ms.intersect(FullMultiset[String]()).size == 0
  }

  property("union with empty FullMultiset ") = forAll {
    (ms: FullMultiset[String]) =>
      ms.union(FullMultiset [String]()).size == ms.size
  }

  property("copies") = forAll(genMultisetWithMap) {
    ((ms: FullMultiset[String], map: Map[String, List[String]]) =>
      ms.copies == map).tupled
  }

  property("get") = forAll(genMultisetWithMapContainingStr) {
    ((ms: FullMultiset[String], map: Map[String, List[String]], s: String) =>
      ms.get(s) == map.getOrElse(s, Nil)).tupled
  }

  property("multiplicities") = forAll(genMultisetWithIntMap) {
    ((ms: FullMultiset[String], map: Map[String, Int]) =>
      ms.multiplicities == map).tupled
  }

  property("removed") = forAll(peopleMultisetContainingPerson) {
    ((ms: FullMultiset[TestPerson], p: TestPerson) =>
      ms.removed(p).multiplicity(p) == (ms.multiplicity(p) - 1) ).tupled
  }

  // TODO: use special Equiv[TestPerson]
  property("removed with equiv") = forAll(peopleMultisetContainingPerson) {
    ((ms: FullMultiset[TestPerson], p: TestPerson) =>
      ms.removed(p).multiplicity(p) == (ms.multiplicity(p) - 1) ).tupled
  }

  property("removedAll") = forAll(peopleMultisetContainingPerson) {
    ((ms: FullMultiset[TestPerson], p: TestPerson) =>
      (ms.multiplicity(p) > 0)                :| "prop #1" &&
      (ms.removedAll(p).multiplicity(p) == 0) :| "prop #2" ).tupled
  }

  // TODO: use special Equiv[TestPerson]
  property("removedAll with equiv") = forAll(peopleMultisetContainingPerson) {
    ((ms: FullMultiset[TestPerson], p: TestPerson) => {
      (ms.multiplicity(p) > 0)                :| "prop #1") &&
      (ms.removedAll(p).multiplicity(p) == 0) :| "prop #2" }).tupled
  }

  // TODO: De Morgan's Law

}