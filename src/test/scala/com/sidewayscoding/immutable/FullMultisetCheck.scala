package com.sidewayscoding.immutable

import org.scalacheck.Properties
import org.scalacheck.Prop._
import org.scalacheck._
import org.scalacheck.Gen._
import Arbitrary.arbitrary
import org.scalacheck.util.Buildable
import com.sidewayscoding.MultisetBuilder
import com.sidewayscoding.MultisetLike
import com.sidewayscoding.MultisetCheckHelper
import com.sidewayscoding.FullMultisetProperties

object ImmutableFullHashMultisetCheck extends Properties("immutable.FullHashMultiset")
                                         with MultisetCheckHelper
                                         with FullMultisetProperties {

  type U[A] = com.sidewayscoding.immutable.FullHashMultiset[A]
  def empty[A] = com.sidewayscoding.immutable.FullHashMultiset.empty[A]
  def create[A](as: A*) = com.sidewayscoding.immutable.FullHashMultiset.apply(as:_*)

}

object ImmutableFullMultisetCheck extends Properties("immutable.FullMultiset")
                                     with MultisetCheckHelper
                                     with FullMultisetProperties {

  type U[A] = com.sidewayscoding.immutable.FullMultiset[A]
  def empty[A] = com.sidewayscoding.immutable.FullMultiset.empty[A]
  def create[A](as: A*) = com.sidewayscoding.immutable.FullMultiset.apply(as:_*)

}

object FullMultisetCheck extends Properties("FullMultiset")
                            with MultisetCheckHelper
                            with FullMultisetProperties {

  type U[A] = com.sidewayscoding.FullMultiset[A]
  def empty[A] = com.sidewayscoding.FullMultiset.empty[A]
  def create[A](as: A*) = com.sidewayscoding.FullMultiset.apply(as:_*)

}