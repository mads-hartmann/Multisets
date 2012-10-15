package com.sidewayscoding.immutable

import org.scalacheck.Properties
import org.scalacheck.Prop.forAll
import org.scalacheck._
import org.scalacheck.Gen._
import Arbitrary.arbitrary
import org.scalacheck.util.Buildable
import com.sidewayscoding.MultisetBuilder

object MultisetCheck extends Properties("Multiset"){

  implicit def buildableMultiset[A] = new Buildable[A, Multiset] {
    def builder = new MultisetBuilder(Multiset.empty[A])
  }

  val multisetGen = Gen.containerOf[Multiset, String](Gen.alphaStr)

  property("size") = forAll((elems: List[String]) => Multiset(elems:_*).size == elems.size)

  property("add") = forAll((ms: Multiset[String], s: String) => (ms + s).size == ms.size + 1)

  property("intersection") = forAll((ms: Multiset[String]) => ms.intersect(Multiset[String]()).size == 0)

  property("union") = forAll((ms: Multiset[String]) => ms.union(Multiset[String]()).size == ms.size)

}