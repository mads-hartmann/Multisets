package com.sidewayscoding

import scala.collection.{ IterableLike }

/** 
 * Interface for all Multiset implementations.
 */
trait MultisetLike[A, +This <: MultisetLike[A, This]] extends IterableLike[A, This] {
  
  def empty: This
  
  def multiplicity(a: A): Int
  
  def insert(a: A): This
  
  def remove(a: A): This
  
}
