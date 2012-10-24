package com.sidewayscoding.immutable

import org.scalacheck.Properties
import org.scalacheck.Gen._
import com.sidewayscoding.MultisetCheckHelper
import com.sidewayscoding.MultisetProperties

object ImmutableCompactHashMultiset extends Properties("immutable.CompactHashMultiset")
                                       with MultisetCheckHelper
                                       with MultisetProperties {

  type T[A] = com.sidewayscoding.immutable.CompactListMultiset[A]
  def empty[A] = com.sidewayscoding.immutable.CompactListMultiset.empty[A]
  def create[A](as: A*) = com.sidewayscoding.immutable.CompactListMultiset.apply(as:_*)

}