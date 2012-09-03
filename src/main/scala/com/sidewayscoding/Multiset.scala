package com.sidewayscoding

/** 
 * Interface for all Multiset implementations.
 */
trait Multiset[A] {
  
  def size: Int
  def multiplicity(a: A): Int
  def insert(a: A): Multiset[A]
  def remove(a: A): Multiset[A]
  
}
