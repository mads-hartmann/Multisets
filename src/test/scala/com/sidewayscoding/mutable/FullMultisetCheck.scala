package com.sidewayscoding.mutable

import com.sidewayscoding.MultisetCheckHelper
import org.scalacheck.Properties
import org.scalacheck.Prop._
import com.sidewayscoding.FullMultisetProperties

object MutableFullHashMultisetCheck extends Properties("mutable.FullHashMultiset")
                                       with MultisetCheckHelper
                                       with MutableFullMultisetProperties
                                       with FullMultisetProperties {

  override type T[A] = U[A]
  type U[A] = com.sidewayscoding.mutable.FullHashMultiset[A]
  def empty[A] = com.sidewayscoding.mutable.FullHashMultiset.empty[A]
  def create[A](as: A*) = com.sidewayscoding.mutable.FullHashMultiset.apply(as:_*)

}