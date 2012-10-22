package com

import org.scalacheck.util.Buildable
import org.scalacheck.Gen
import org.scalacheck.Arbitrary
import org.scalacheck.Arbitrary$

package object sidewayscoding {

  case class TestPerson(name: String, age: Int)

  implicit def buildableMultiset[A] = new Buildable[A, FullMultiset] {
    def builder = new MultisetBuilder(FullMultiset.empty[A])
  }

  val arbitraryPerson = for {
    name <- Gen.alphaStr
    age <- Arbitrary.arbitrary[Int]
  } yield TestPerson(name, age)

  val genStringMultiset = Gen.containerOf[FullMultiset,String](Gen.alphaStr)
  val genPeopleMultiset = Gen.containerOf[FullMultiset,TestPerson](arbitraryPerson)

  /**
   * Generates a tuple with a Multiset with arbitrary strings and a string that is
   * guaranteed to be the in multiset.
   */
  val genMultisetWithString = for {
    mset <- genStringMultiset
       s <- Arbitrary.arbitrary[String]
  } yield (mset + s, s)

  /**
   * Generates a tuple with a Multiset with arbitrary strings and a Map representation
   * of the same multiset.
   */
  val genMultisetWithMap = for {
    strs <- Gen.listOfN(100, Gen.alphaStr)
    map = strs.groupBy(id => id)
    mset = FullMultiset(strs:_*)
  } yield (mset, map)

  /**
   * Generates a tuple with a Multiset with arbitrary strings and a Map representation
   * of the same multiset and a string that is guaranteed to be in the multiset/map
   */
  val genMultisetWithMapContainingStr = for {
    str <- Arbitrary.arbitrary[String]
    strs <- Gen.listOfN(100, Gen.alphaStr)
    map = (str :: strs).groupBy(id => id)
    mset = FullMultiset((str :: strs):_*)
  } yield (mset, map, str)

  /**
   * Generates a tuple with a Multiset with arbitrary strings and a Map representation of
   * the multiplicities.
   */
  val genMultisetWithIntMap = for {
    strs <- Gen.listOfN(100, Gen.alphaStr)
    map = strs.groupBy(id => id).mapValues(_.size)
    mset = FullMultiset(strs:_*)
  } yield (mset,map)

  val peopleMultisetContainingPerson: Gen[(FullMultiset[TestPerson], TestPerson)] = for {
       p <- arbitraryPerson
    mset <- genPeopleMultiset
  } yield (mset + p, p)
}