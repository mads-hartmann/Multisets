package com.sidewayscoding

trait MergeableMultiset[A] extends Multiset[A] {
  
  def withMultiplicity: Iterable[(A, Int)]
  
}