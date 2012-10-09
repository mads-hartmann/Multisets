package com.sidewayscoding.immutable

import org.specs2.mutable._

class MultisetSpec extends Specification {

  // Normal case class where equals is defined over all of the properties
  case class Person(name: String, age: Int)
  val p1 = Person("Mads", 23)
  val p2 = Person("Mikkel", 23)
  val p3 = Person("Mads", 25)

  // case class where equals is only defined over a subset of the properties
  case class CPRPerson(cpr: String, name: String) {
    override def equals(o: Any) = o match {
      case p: CPRPerson => p.cpr == cpr
      case _ => false
    }
  }
  val cprP1 = CPRPerson("190989", "Mads")
  val cprP2 = CPRPerson("190989", "Mikkel")
  val cprP3 = CPRPerson("020190", "Eva")
  val cprP4 = CPRPerson("010190", "Jon")
  val cprP5 = CPRPerson("020190", "Doe")
  val cprP6 = CPRPerson("030190", "Bazz")

  "An empty Multiset" should {

   "Have size 0" in {
     Multiset().size must_== 0
   }

   "Grow in size when items are added" in {
     val empty = Multiset[Person]()
     val notEmpty = empty + p1 + p2
     notEmpty.size must_== 2
     notEmpty.multiplicity(p1) must_== 1
     notEmpty.multiplicity(p2) must_== 1
   }

  }

  "A non-empty multiset" should {

    "should grow in size when added" in {
      val notEmpty = Multiset(p1,p2)
      val bigger = notEmpty + p3
      notEmpty.size must_== 2
      bigger.size must_== 3
    }
  }

  "Any Multiset" should {

    "keep track of the multiplicity" in {
      val mset = Multiset(p1,p1)
      mset.multiplicity(p1) must_== 2
    }

    "be able to intersect with another Multiset" in {
      val mset = Multiset(p1,p2,p3)
      val mset2 = Multiset(p1)
      val intersection = mset.intersect(mset2)
      intersection.size must_== 2
    }

    "contain the correct elements after intersection" in {
      val mset = Multiset(cprP1)
      val mset2 = Multiset(cprP2,cprP3,cprP4, cprP5,cprP6)
      val intersection = mset intersect mset2
      intersection.size must_== 2
      intersection.toList must_== List(cprP1, cprP2)
    }

  }

  "union" should {

    "return the same multiset when invoked with an empty multiset" in {
      val mset1 = Multiset(p1)
      val mset2 = Multiset[Person]()
      (mset1 union mset2).toList must_== Multiset(p1).toList // TODO: need to implement equals. 
      (mset2 union mset1).toList must_== Multiset(p1).toList
    }

    "find the actual union" in {
      val mset1 = Multiset(p1)
      val mset2 = Multiset(p2)
      (mset2 union mset1).toList must_== Multiset(p2,p1).toList
      (mset1 union mset2).toList must_== Multiset(p1,p2).toList
    }
  }

}