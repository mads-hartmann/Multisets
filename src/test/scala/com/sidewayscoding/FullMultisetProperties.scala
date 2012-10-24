package com.sidewayscoding

import org.scalacheck.Properties
import org.scalacheck.Prop._

trait FullMultisetProperties extends MultisetProperties {

  this: MultisetCheckHelper with Properties =>

  type U[A] <: com.sidewayscoding.FullMultiset[A] with com.sidewayscoding.FullMultisetLike[A, U[A]]
  type T[A] = U[A]

  property("copies") = forAll(genMultisetWithMap) {
    ((ms: this.T[String], map: Map[String, List[String]]) =>
      ms.copies == map).tupled
  }

  property("get") = forAll(genMultisetWithMapContainingStr) {
    ((ms: this.T[String], map: Map[String, List[String]], s: String) =>
      ms.get(s) == map.getOrElse(s, Nil)).tupled
  }

  // TODO: use special Equiv[TestPerson]
  property("removed with equiv") = forAll(peopleMultisetContainingPerson) {
    ((ms: this.T[TestPerson], p: TestPerson) =>
      ms.removed(p).multiplicity(p) == (ms.multiplicity(p) - 1) ).tupled
  }

  // TODO: use special Equiv[TestPerson]
  property("removedAll with equiv") = forAll(peopleMultisetContainingPeople) {
    ((ms: this.T[TestPerson], p: TestPerson) => {
      (ms.multiplicity(p) > 1)                :| "prop #1" &&
      (ms.removedAll(p).multiplicity(p) == 0) :| "prop #2" }).tupled
  }

}