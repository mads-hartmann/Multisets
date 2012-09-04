package com.sidewayscoding.immutable

import com.sidewayscoding.{ MultisetLike }
import scala.collection.generic.{ CanBuildFrom }
import scala.collection.mutable.{ Builder }
import scala.collection.immutable.{ TreeMap }

/**
 *  Don't use. This is a reference implementation with horrible performance.
 *
 *  The aim is to create an implementation that obviously has no bugs (rather
 *  than one that has no obvious bugs) so it can be used to test more advanced
 *  implementations.
 */
class ListMultiset[A : Ordering] private[immutable] (val delegate: TreeMap[A, Int]) extends MultisetLike[A, ListMultiset[A]] {

  import ListMultiset._

  def empty = ListMultiset[A]()
  
  def iterator: Iterator[A] = new ListMultisetIterator(delegate)

  override def newBuilder: Builder[A, ListMultiset[A]] = ListMultiset.newBuilder[A]

  def seq: ListMultiset[A] = this

  override def size: Int =
    delegate.values.sum

  def multiplicity(a: A): Int =
    delegate.get(a).getOrElse(0)

  def insert(a: A) = {
    val newCount    = delegate.get(a).map(_ + 1).getOrElse(1)
    val newDelegate = delegate.updated(a, newCount)
    new ListMultiset(newDelegate)
  }

  def remove(a: A) = {
    val newDelegate = delegate.get(a).map { c: Int =>
      delegate.updated(a, c + 1)
    }.getOrElse(delegate)

    new ListMultiset(newDelegate)
  }

}

object ListMultiset {

  implicit def canBuildFrom[A : Ordering]: CanBuildFrom[ListMultiset[_], A, ListMultiset[A]] = 
    new CanBuildFrom[ListMultiset[_], A, ListMultiset[A]] {
      def apply(): Builder[A, ListMultiset[A]] = newBuilder
      def apply(from: ListMultiset[_]): Builder[A, ListMultiset[A]] = newBuilder
    }

  def newBuilder[A : Ordering]: Builder[A, ListMultiset[A]] = new ListMultisetBuilder

  def empty[A: Ordering] = apply()

  def apply[A : Ordering](): ListMultiset[A] =
    new ListMultiset[A](TreeMap[A,Int]())

}

class ListMultisetBuilder[A : Ordering] extends Builder[A, ListMultiset[A]] {
  private var delegate: TreeMap[A, Int] = new TreeMap[A,Int]()

  def +=(a: A): this.type = {
    val newCount = delegate.get(a).map(_ + 1).getOrElse(1)
    delegate = delegate.updated(a, newCount)
    this
  } 
  
  def clear() = { delegate = new TreeMap[A,Int]() }
  
  def result = { new ListMultiset[A](delegate) }
}

class ListMultisetIterator[A : Ordering](private val tm: TreeMap[A, Int]) extends Iterator[A] {

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

