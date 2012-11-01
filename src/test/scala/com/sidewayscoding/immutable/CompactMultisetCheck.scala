package com.sidewayscoding.immutable

import org.scalacheck.Properties
import org.scalacheck.Gen._
import com.sidewayscoding.MultisetCheckHelper
import com.sidewayscoding.MultisetProperties

object ImmutableCompactHashMultisetCheck extends Properties("immutable.CompactHashMultiset")
                                       with MultisetCheckHelper
                                       with MultisetProperties {

  type T[A] = com.sidewayscoding.immutable.CompactListMultiset[A]
  def empty[A] = com.sidewayscoding.immutable.CompactListMultiset.empty[A]
  def create[A](as: A*) = com.sidewayscoding.immutable.CompactListMultiset.apply(as:_*)

}

object ImmutableCompactMultisetCheck extends Properties("immutable.CompactMultiset")
                                       with MultisetCheckHelper
                                       with MultisetProperties {

  type T[A] = com.sidewayscoding.immutable.CompactMultiset[A]
  def empty[A] = com.sidewayscoding.immutable.CompactMultiset.empty[A]
  def create[A](as: A*) = com.sidewayscoding.immutable.CompactMultiset.apply(as:_*)

}

object CompactMultisetCheck extends Properties("CompactMultiset")
                          with MultisetCheckHelper
                          with MultisetProperties {

  type T[A] = com.sidewayscoding.CompactMultiset[A]
  def empty[A] = com.sidewayscoding.CompactMultiset.empty[A]
  def create[A](as: A*) = com.sidewayscoding.CompactMultiset.apply(as:_*)

}