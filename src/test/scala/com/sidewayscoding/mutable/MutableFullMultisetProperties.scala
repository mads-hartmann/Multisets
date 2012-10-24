package com.sidewayscoding.mutable

import org.scalacheck.Properties
import org.scalacheck.Prop._
import com.sidewayscoding.MultisetCheckHelper

trait MutableFullMultisetProperties {

  this: MultisetCheckHelper with Properties =>

  // TODO: Is there are better way to refine this restriction?
  type U[A] <: com.sidewayscoding.mutable.FullMultiset[A] with com.sidewayscoding.mutable.MultisetLike[A, U[A]]
  type T[A] = U[A]

  property("remove") = forAll(peopleMultisetContainingPerson) {
    ((ms: T[TestPerson], p: TestPerson) => {
      val x = ms.multiplicity(p)
      ms.remove(p)
      val y = ms.multiplicity(p)
      x >= 1 && y == (x-1)}).tupled
  }

}