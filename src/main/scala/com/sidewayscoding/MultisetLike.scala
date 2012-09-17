package com.sidewayscoding

import scala.collection.{ IterableLike }

/** 
 * Interface for all Multiset implementations.
 */
// TODO: Should probably be +This <: Multiset[A] with MultisetLike[A, This] 
//       at some point
trait MultisetLike[A, +This <: Multiset[A] with MultisetLike[A, This]] extends IterableLike[A, This] {
  
  def empty: This
  
  def multiplicity(a: A): Int
  
  def insert(a: A): This
  
  def remove(a: A): This
  
}
