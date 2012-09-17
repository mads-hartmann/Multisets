package com.sidewayscoding

trait MergeableMultiset[A] extends Multiset[A] {
  
  implicit def mergeable: Mergeable[A]
  
}