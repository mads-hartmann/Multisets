package com.sidewayscoding.immutable

import scala.collection.generic.CanBuildFrom
import scala.collection.mutable.Builder
import com.sidewayscoding.MergeableMultiset
import com.sidewayscoding.MergeableMultisetLike
import scala.collection.generic.GenericCompanion
import com.sidewayscoding.GenericMultisetTemplate
import scala.collection.immutable.ListMap

object MergeableListMultiset extends ImmutableMergeableMultisetFactory[MergeableListMultiset] {

  override def empty[A] = apply()
  implicit def canBuildFrom[A]: CanBuildFrom[Coll, A, MergeableListMultiset[A]] = multisetCanBuildFrom[A]

  def apply[A](): MergeableListMultiset[A] =
    new MergeableListMultiset[A](ListMap[A,Int]())

}

/**
 *  Don't use. This is a reference implementation with horrible performance.
 *
 *  The aim is to create an implementation that obviously has no bugs (rather
 *  than one that has no obvious bugs) so it can be used to test more advanced
 *  implementations.
 */
class MergeableListMultiset[A] private[immutable] (val delegate: ListMap[A, Int]) extends MergeableMultiset[A]
                                                                                     with MergeableMultisetLike[A, MergeableListMultiset[A]]
                                                                                     with GenericMultisetTemplate[A, MergeableListMultiset]{

  def withMultiplicity = delegate.toIterable.map{ case (itm, count) => ((0 until count).map(_ =>itm), count)}

  override def companion: GenericCompanion[MergeableListMultiset] = MergeableListMultiset

  def iterator: Iterator[A] = new MergeableListMultisetIterator(delegate)

  override def newBuilder: Builder[A, MergeableListMultiset[A]] = MergeableListMultiset.newBuilder

  override def size: Int = delegate.values.sum

  def multiplicity(a: A): Int = delegate.get(a).getOrElse(0)

  def +(a: A) = {
    val newCount    = delegate.get(a).map(_ + 1).getOrElse(1)
    val newDelegate = delegate.updated(a, newCount)
    new MergeableListMultiset(newDelegate)
  }

  def -(a: A) = {
    if (delegate.contains(a)) {
      val count = delegate.get(a).get
      if (count <= 1) {
        new MergeableListMultiset(delegate - a)
      } else {
        new MergeableListMultiset(delegate.updated(a, count - 1))
      }
    } else {
      this
    }
  }

}

class MergeableListMultisetIterator[A](private val tm: ListMap[A, Int]) extends Iterator[A] {

  private[this] val mapIterator = tm.iterator
  private[this] var indexInElement = 1
  private[this] var currentElementCount: Int = 0
  private[this] var currentElement: Option[A] = None

  override def hasNext: Boolean = {
    indexInElement < currentElementCount || mapIterator.hasNext
  }

  override def next(): A = {
    if (indexInElement < currentElementCount) {
        indexInElement += 1
        return currentElement.get
      } else {
      if (mapIterator.hasNext) {
          val (e,c) = mapIterator.next()
          currentElement = Some(e)
          currentElementCount = c
          indexInElement = 1
          return e
        } else {
        throw new NoSuchElementException("next on empty iterator")
      }
    }
  }
}

