package com.sidewayscoding

import scala.collection.{ IterableLike }
import scala.collection.GenIterableLike

/**
 * Interface for all Multiset implementations.
 */
trait MultisetLike[A, +This <: Multiset[A] with MultisetLike[A, This]] extends IterableLike[A, This] 
                                                                          with GenIterableLike[A, This]{

  def multiplicity(a: A): Int

  def +(a: A): This

  def -(a: A): This

  /**
   * Computes the intersection between this multiset and another multiset.
   *
   *  @param   that  the multiset to intersect with.
   *  @return  a new multiset consisting of all elements that are both in this
   *  multiset and in the given multiset `that`.
   */
  def intersect(that: Multiset[A]): This =
    this filter (itm => that.multiplicity(itm) > 0)

  /**
   * Computes the union of this multiset and another multiset.
   *
   *  @param   that  the multiset to form the union with.
   *  @return  a new multiset consisting of all elements that are in this
   *  multisetset or in the given multiset `that`.
   */
  def union(that: Multiset[A]): This = throw new Exception("Not Implemented")

  /**
   * Computes the difference of this multiset and another multiset.
   *
   *  @param that the multiset of elements to exclude.
   *  @return     a multiset containing those elements of this
   *              multiset that are not also contained in the given multiset `that`.
   */
  def diff(that: Multiset[A]): This = throw new Exception("Not Implemented")

}
