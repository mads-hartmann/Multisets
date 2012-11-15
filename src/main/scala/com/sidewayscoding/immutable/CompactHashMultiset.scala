package com.sidewayscoding.immutable

import scala.collection.generic.CanBuildFrom
import scala.collection.generic.GenericCompanion
import scala.collection.immutable.HashMap
import scala.collection.mutable.Builder

import com.sidewayscoding.CompactMultisetLike
import com.sidewayscoding.GenericMultisetTemplate

object CompactHashMultiset extends ImmutableCompactMultisetFactory[CompactHashMultiset] {

  override def empty[A] = apply()

  implicit def canBuildFrom[A]: CanBuildFrom[Coll, A, CompactHashMultiset[A]] =
    multisetCanBuildFrom[A]

  def apply[A](): CompactHashMultiset[A] =
    new CompactHashMultiset[A](HashMap[A,Int]())

}

/**
 *  Don't use. This is a reference implementation with horrible performance.
 *
 *  The aim is to create an implementation that obviously has no bugs (rather
 *  than one that has no obvious bugs) so it can be used to test more advanced
 *  implementations.
 */
class CompactHashMultiset[A] private[immutable] (val delegate: HashMap[A, Int]) extends CompactMultiset[A]
                                                                                   with CompactMultisetLike[A, CompactHashMultiset[A]]
                                                                                   with GenericMultisetTemplate[A, CompactHashMultiset] {

  override def companion: GenericCompanion[CompactHashMultiset] = CompactHashMultiset

  def multiplicities = delegate

  def iterator: Iterator[A] = new MergeableListMultisetIterator(delegate)

  override def newBuilder: Builder[A, CompactHashMultiset[A]] = CompactHashMultiset.newBuilder

  override def size: Int = delegate.values.sum

  def multiplicity(a: A): Int = delegate.get(a).getOrElse(0)

  def +(a: A) = {
    val newCount    = delegate.get(a).map(_ + 1).getOrElse(1)
    val newDelegate = delegate.updated(a, newCount)
    new CompactHashMultiset(newDelegate)
  }

  def removed(a: A) = {
    if (delegate.contains(a)) {
      val count = delegate.get(a).get
      if (count <= 1) {
        new CompactHashMultiset(delegate - a)
      } else {
        new CompactHashMultiset(delegate.updated(a, count - 1))
      }
    } else {
      CompactHashMultiset.this
    }
  }

  def removedAll(a: A) = {
    new CompactHashMultiset(delegate - a)
  }

}

private class MergeableListMultisetIterator[A](private val tm: HashMap[A, Int]) extends Iterator[A] {

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

