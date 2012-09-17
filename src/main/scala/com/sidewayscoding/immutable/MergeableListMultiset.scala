package com.sidewayscoding.immutable

import com.sidewayscoding.{ MultisetLike }
import scala.collection.generic.{ CanBuildFrom }
import scala.collection.mutable.{ Builder }
import com.sidewayscoding.Mergeable

/**
 *  Don't use. This is a reference implementation with horrible performance.
 *
 *  The aim is to create an implementation that obviously has no bugs (rather
 *  than one that has no obvious bugs) so it can be used to test more advanced
 *  implementations.
 */
class MergeableListMultiset[A : Mergeable] private[immutable] (val delegate: Map[A, Int]) extends Multiset[A] with MultisetLike[A, MergeableListMultiset[A]] {

  import MergeableListMultiset._

  def empty = MergeableListMultiset[A]()
  
  def iterator: Iterator[A] = new MergeableListMultisetIterator(delegate)

  override def newBuilder: Builder[A, MergeableListMultiset[A]] = MergeableListMultiset.newBuilder[A]

  def seq: MergeableListMultiset[A] = this

  override def size: Int = delegate.values.sum

  def multiplicity(a: A): Int = delegate.get(a).getOrElse(0)

  def insert(a: A) = {
    val newCount    = delegate.get(a).map(_ + 1).getOrElse(1)
    val newDelegate = delegate.updated(a, newCount)
    new MergeableListMultiset(newDelegate)
  }

  def remove(a: A) = {
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

object MergeableListMultiset extends ImmutableMultisetFactory[MergeableListMultiset] {

  implicit def canBuildFrom[A : Mergeable]: CanBuildFrom[MergeableListMultiset[_], A, MergeableListMultiset[A]] = 
    new CanBuildFrom[MergeableListMultiset[_], A, MergeableListMultiset[A]] {
      def apply(): Builder[A, MergeableListMultiset[A]] = newBuilder
      def apply(from: MergeableListMultiset[_]): Builder[A, MergeableListMultiset[A]] = newBuilder
    }

  def newBuilder[A : Mergeable]: Builder[A, MergeableListMultiset[A]] = new MergeableListMultisetBuilder

  def empty[A: Mergeable] = apply()

  def apply[A : Mergeable](): MergeableListMultiset[A] =
    new MergeableListMultiset[A](Map[A,Int]())
   
}
    
class MergeableListMultisetBuilder[A : Mergeable ] extends Builder[A, MergeableListMultiset[A]] {
 
  private var delegate: Map[A, Int] = Map[A,Int]()

  def +=(a: A): this.type = {
    val newCount = delegate.get(a).map(_ + 1).getOrElse(1)
    delegate = delegate.updated(a, newCount)
    this
  }
  
  def clear() = { delegate = Map[A,Int]() }
  
  def result = { 
    new MergeableListMultiset[A](delegate) 
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

