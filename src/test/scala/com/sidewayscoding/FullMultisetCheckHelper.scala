package com.sidewayscoding

import org.scalacheck.util.Buildable
import org.scalacheck.Gen
import org.scalacheck.Arbitrary

trait FullMultisetCheckHelper {

  type T[A] <: FullMultiset[A] with MultisetLike[A, T[A]]

  def empty[A]: T[A]

  def create[A](as: A*): T[A]

  case class TestPerson(name: String, age: Int)

  implicit def buildableMultiset[A] = new Buildable[A, T] {
    def builder = new MultisetBuilder(empty[A])
  }

  val arbitraryPerson = for {
    name <- Gen.alphaStr
    age <- Arbitrary.arbitrary[Int]
  } yield TestPerson(name, age)

  val genStringMultiset = Gen.containerOf[T, String](Gen.alphaStr)
  val genPeopleMultiset = Gen.containerOf[T, TestPerson](arbitraryPerson)

  val genMultisetAndString = for {
    mset <- genStringMultiset
    s <- Arbitrary.arbitrary[String]
  } yield (mset, s)

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
    mset = create(strs: _*)
  } yield (mset, map)

  /**
   * Generates a tuple with a Multiset with arbitrary strings and a Map representation
   * of the same multiset and a string that is guaranteed to be in the multiset/map
   */
  val genMultisetWithMapContainingStr = for {
    str <- Arbitrary.arbitrary[String]
    strs <- Gen.listOfN(100, Gen.alphaStr)
    map = (str :: strs).groupBy(id => id)
    mset = create((str :: strs): _*)
  } yield (mset, map, str)

  /**
   * Generates a tuple with a Multiset with arbitrary strings and a Map representation of
   * the multiplicities.
   */
  val genMultisetWithIntMap = for {
    strs <- Gen.listOfN(100, Gen.alphaStr)
    map = strs.groupBy(id => id).mapValues(_.size)
    mset = create(strs: _*)
  } yield (mset, map)

  val peopleMultisetContainingPerson: Gen[(T[TestPerson], TestPerson)] = for {
    p <- arbitraryPerson
    mset <- genPeopleMultiset
  } yield (mset + p, p)

  val peopleMultisetContainingPeople: Gen[(T[TestPerson], TestPerson)] = for {
    mset <- genPeopleMultiset
    p <- arbitraryPerson
    val all = mset + p + p
  } yield (all, p)

}