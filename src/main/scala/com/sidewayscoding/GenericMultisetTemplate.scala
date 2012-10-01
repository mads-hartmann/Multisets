package com.sidewayscoding

import scala.collection.generic.GenericTraversableTemplate

trait GenericMultisetTemplate[A, +CC[X] <: Multiset[X]] extends GenericTraversableTemplate[A, CC] {
  def empty: CC[A] = companion.empty[A]
}