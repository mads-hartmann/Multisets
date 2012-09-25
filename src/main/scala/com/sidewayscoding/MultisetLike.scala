package com.sidewayscoding

import scala.collection.{ IterableLike }

/**
 * Interface for all Multiset implementations.
 */
trait MultisetLike[A, +This <: Multiset[A] with MultisetLike[A, This]] extends IterableLike[A, This] {

  def empty: This

  def multiplicity(a: A): Int

  def +(a: A): This

  def -(a: A): This

}
