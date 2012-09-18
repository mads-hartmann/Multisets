package com.sidewayscoding.immutable

import scala.collection.generic.CanBuildFrom
import scala.collection.mutable.Builder

import com.sidewayscoding.Mergeable
import com.sidewayscoding.MergeableMultiset
import com.sidewayscoding.MergeableMultisetLike

object MergeableListMultiset extends ImmutableMergeableMultisetFactory[MergeableListMultiset] {

  def empty[A: Mergeable] = apply()

  def apply[A : Mergeable](): MergeableListMultiset[A] =
    new MergeableListMultiset[A](Map[A,Int]())

}

/**
 *  Don't use. This is a reference implementation with horrible performance.
 *
 *  The aim is to create an implementation that obviously has no bugs (rather
 *  than one that has no obvious bugs) so it can be used to test more advanced
 *  implementations.
 */
class MergeableListMultiset[A] private[immutable] (val delegate: Map[A, Int])(implicit val mergeable: Mergeable[A]) extends MergeableMultiset[A] 
                                                                                                                       with MergeableMultisetLike[A, MergeableListMultiset[A]] {

  def empty = MergeableListMultiset.empty

  def iterator: Iterator[A] = new MergeableListMultisetIterator(delegate)

  override def newBuilder: Builder[A, MergeableListMultiset[A]] = MergeableListMultiset.newBuilder

  def seq: MergeableListMultiset[A] = this

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

class MergeableListMultisetIterator[A : Mergeable](private val tm: Map[A, Int]) extends Iterator[A] {

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

