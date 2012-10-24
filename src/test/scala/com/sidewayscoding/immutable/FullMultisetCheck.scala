package com.sidewayscoding.immutable

import org.scalacheck.Properties
import org.scalacheck.Prop._
import org.scalacheck._
import org.scalacheck.Gen._
import Arbitrary.arbitrary
import org.scalacheck.util.Buildable
import com.sidewayscoding.MultisetBuilder
import com.sidewayscoding.MultisetLike
import com.sidewayscoding.FullMultisetCheckHelper

object MutableFullHashMultisetAsImmutable extends Properties("mutable.FullHashMultiset")
                                             with FullMultisetCheckHelper 
                                             with ImmutableFullMultisetProperties {

  type T[A] = com.sidewayscoding.mutable.FullHashMultiset[A]
  def empty[A] = com.sidewayscoding.mutable.FullHashMultiset.empty[A]
  def create[A](as: A*) = com.sidewayscoding.mutable.FullHashMultiset.apply(as:_*)

}

object ImmutableFullHashMultiset extends Properties("immutable.FullHashMultiset")
                                    with FullMultisetCheckHelper
                                    with ImmutableFullMultisetProperties {

  type T[A] = com.sidewayscoding.immutable.FullHashMultiset[A]
  def empty[A] = com.sidewayscoding.immutable.FullHashMultiset.empty[A]
  def create[A](as: A*) = com.sidewayscoding.immutable.FullHashMultiset.apply(as:_*)

}

trait ImmutableFullMultisetProperties {

  this: FullMultisetCheckHelper with Properties =>

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

  property("copies") = forAll(genMultisetWithMap) {
    ((ms: this.T[String], map: Map[String, List[String]]) =>
      ms.copies == map).tupled
  }

  property("get") = forAll(genMultisetWithMapContainingStr) {
    ((ms: this.T[String], map: Map[String, List[String]], s: String) =>
      ms.get(s) == map.getOrElse(s, Nil)).tupled
  }

  property("multiplicities") = forAll(genMultisetWithIntMap) {
    ((ms: this.T[String], map: Map[String, Int]) =>
      ms.multiplicities == map).tupled
  }

  property("removed") = forAll(peopleMultisetContainingPerson) {
    ((ms: this.T[TestPerson], p: TestPerson) =>
      ms.removed(p).multiplicity(p) == (ms.multiplicity(p) - 1) ).tupled
  }

  // TODO: use special Equiv[TestPerson]
  property("removed with equiv") = forAll(peopleMultisetContainingPerson) {
    ((ms: this.T[TestPerson], p: TestPerson) =>
      ms.removed(p).multiplicity(p) == (ms.multiplicity(p) - 1) ).tupled
  }

  property("removedAll") = forAll(peopleMultisetContainingPeople)(t => {
      val (ms: this.T[TestPerson], p: TestPerson) = t
      (ms.multiplicity(p) > 0)                :| "prop #1" &&
      (ms.removedAll(p).multiplicity(p) == 0) :| "prop #2"
    })

  // TODO: use special Equiv[TestPerson]
  property("removedAll with equiv") = forAll(peopleMultisetContainingPeople) {
    ((ms: this.T[TestPerson], p: TestPerson) => {
      (ms.multiplicity(p) > 1)                :| "prop #1" &&
      (ms.removedAll(p).multiplicity(p) == 0) :| "prop #2" }).tupled
  }

  // TODO: De Morgan's Law
}