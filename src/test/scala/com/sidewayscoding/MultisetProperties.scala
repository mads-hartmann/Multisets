package com.sidewayscoding

import org.scalacheck.Properties
import org.scalacheck.Prop._

trait MultisetProperties {

  this: MultisetCheckHelper with Properties =>

  property("size") = forAll { (elems: List[String]) =>
    FullMultiset(elems: _*).size == elems.size
  }

  property("adding an item should increase the size of the multiset") = forAll(genMultisetAndString) {
    ((ms: this.T[String], s: String) =>
      (ms + s).size == ms.size + 1).tupled
  }

  property("adding an item should increase it's multiplicity") = forAll(genMultisetAndString) {
    ((ms: this.T[String], s: String) =>
      (ms + s).multiplicity(s) == ms.multiplicity(s) + 1).tupled
  }

  property("removing existing item should decrease size") = forAll(genMultisetWithString) {
    ((ms: this.T[String], s: String) =>
      ((ms.removed(s)).size) == (ms.size - 1) ).tupled
  }

  property("removing existing item should decrease multiplicity") = forAll(genMultisetWithString) {
    ((ms: this.T[String], s: String) =>
      (ms.removed(s)).multiplicity(s) == ms.multiplicity(s) - 1 ).tupled
  }

  property("intersection with empty multiset") = forAll(genStringMultiset) {
    (ms: this.T[String]) =>
      ms.intersect(FullMultiset[String]()).size == 0
  }

  property("union with empty FullMultiset") = forAll(genStringMultiset) {
    (ms: this.T[String]) =>
      ms.union(FullMultiset [String]()).size == ms.size
  }

  property("multiplicities") = forAll(genMultisetWithIntMap) {
    ((ms: this.T[String], map: Map[String, Int]) =>
      ms.multiplicities == map).tupled
  }

  property("removed") = forAll(peopleMultisetContainingPerson) {
    ((ms: this.T[TestPerson], p: TestPerson) =>
      ms.removed(p).multiplicity(p) == (ms.multiplicity(p) - 1) ).tupled
  }

  property("removedAll") = forAll(peopleMultisetContainingPeople)(t => {
      val (ms: this.T[TestPerson], p: TestPerson) = t
      (ms.multiplicity(p) > 0)                :| "prop #1" &&
      (ms.removedAll(p).multiplicity(p) == 0) :| "prop #2"
  })

  // TODO: De Morgan's Law
}