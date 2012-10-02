package com.sidewayscoding

import scala.collection.generic.GenericTraversableTemplate
import com.sidewayscoding.immutable.Multiset

trait GenericMultisetTemplate[A, +CC[X] <: Multiset[X]] extends GenericTraversableTemplate[A, CC] {
  def empty: CC[A] = companion.empty[A]
}