package com

import org.scalacheck.util.Buildable
import org.scalacheck.Gen
import org.scalacheck.Arbitrary

package object sidewayscoding {

  implicit def buildableMultiset[A] = new Buildable[A, Multiset] {
    def builder = new MultisetBuilder(Multiset.empty[A])
  }

  val genStringMultiset = Gen.containerOf[Multiset,String](Gen.alphaStr)

  val genMultisetWithString = for {
    mset <- genStringMultiset
       s <- Arbitrary.arbitrary[String]
  } yield (mset + s, s)

}