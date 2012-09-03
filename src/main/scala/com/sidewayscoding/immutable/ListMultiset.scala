package com.sidewayscoding.immutable

import com.sidewayscoding.{ Multiset }
import scala.collection.immutable.{ TreeMap }

/**
 *  Don't use. This is a reference implementation with horrible performance.
 *
 *  The aim is to create an implementation that obviously has no bugs (rather
 *  than one that has no obvious bugs) so it can be used to test more advanced
 *  implementations.
 */
class ListMultiset[A : Ordering] private (val delegate: TreeMap[A, Int]) extends Multiset[A] {

  def size: Int =
    delegate.values.sum

  def multiplicity(a: A): Int =
    delegate.get(a).getOrElse(0)

  def insert(a: A): Multiset[A] = {
    val newCount    = delegate.get(a).map(_ + 1).getOrElse(1)
    val newDelegate = delegate.updated(a, newCount)
    new ListMultiset(newDelegate)
  }

  def remove(a: A): Multiset[A] = {
    val newDelegate = delegate.get(a).map { c: Int =>
      delegate.updated(a, c + 1)
    }.getOrElse(delegate)

    new ListMultiset(newDelegate)
  }

}

object ListMultiset {

  def apply[A : Ordering](): ListMultiset[A] =
    new ListMultiset[A](TreeMap[A,Int]())

}
