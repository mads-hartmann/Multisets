package com.sidewayscoding

/**
 * A FullMultiset is a Multiset that keeps all occurrences of the elements in memory. This is useful
 * if the equals mehtod isn't defined over all of the properties of the elements as you won't loose
 * any information.
 */
trait FullMultisetLike[A, +This <: FullMultiset[A] with FullMultisetLike[A, This]] extends MultisetLike[A, This] {

  /**
   * Returns a Map with each distinct element mapping to the sequences of elements that are
   * equal to it.
   */
  def copies: Map[A, Seq[A]]

  /**
   * Returns all the occurrences of `a`.
   */
  def get(a: A): Seq[A]

  override def removed(a: A): This = removed(a, b => implicitly[Equiv[A]].equiv(a,b))

  override def removedAll(a: A): This = removedAll(a, b => implicitly[Equiv[A]].equiv(a,b))

  /**
   * Removes the first copy of `a` where `eq` returns true.
   */
  def removed(a: A, eq: A => Boolean): This

  /**
   * Removes all copies of `a` where `eq` returns true. 
   */
  def removedAll(a: A, eq: A => Boolean): This
}