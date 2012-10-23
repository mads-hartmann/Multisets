package com.sidewayscoding

import scala.collection.{ IterableLike }
import scala.collection.GenIterableLike
import scala.collection.generic.CanBuildFrom

/**
 * Interface for all Multiset implementations.
 */
trait MultisetLike[A, +This <: Multiset[A] with MultisetLike[A, This]] extends IterableLike[A, This]
                                                                          with GenIterableLike[A, This]{

  self =>

  def empty: This

  /**
   * The number of occurrences of `a` in this Multiset.
   */
  def multiplicity(a: A): Int

  /**
   * A copy of this Multiset with one more occurrence of `a`.
   */
  def +(a: A): This

  /**
   * A copy of this Multiset with one less occurrence of `a`.
   */
  def removed(a: A): This

  /**
   * A copy of this Multiset with all occurrences of `a` removed.
   */
  def removedAll(a: A): This

  /**
   * All the items in the Multiset with their associated multiplicity.
   *
   * @return A Map associating each unique item with its multiplicity.
   */
  def multiplicities: Map[A, Int]

  /**
   * Computes the intersection between this multiset and another multiset.
   *
   *  @param   that  the multiset to intersect with.
   *  @return  a new multiset consisting of all elements that are both in this
   *  multiset and in the given multiset `that`.
   */
  def intersect(that: Multiset[A]): This = {
    val thisInThat = this.filter( itm => that.multiplicity(itm) > 0)
    val thatInThis = that.filter( itm => this.multiplicity(itm) > 0)
    val b = self.newBuilder
    b ++= thisInThat
    b ++= thatInThis
    b.result
  }

  /**
   * Computes the union of this multiset and another multiset.
   *
   *  @param   that  the multiset to form the union with.
   *  @return  a new multiset consisting of all elements that are in this
   *  multisetset or in the given multiset `that`.
   */
  def union(that: Multiset[A]): This = {
    val b = self.newBuilder
    b ++= this
    b ++= that
    b.result
  }

  /**
   * Computes the difference of this multiset and another multiset.
   *
   *  @param that the multiset of elements to exclude.
   *  @return     a multiset containing those elements of this
   *              multiset that are not also contained in the given multiset `that`.
   */
  def diff(that: Multiset[A]): This = {
    this filter ( itm => that.multiplicity(itm) == 0)
  }

}
