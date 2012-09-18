package com.sidewayscoding.immutable

import com.sidewayscoding.Mergeable
import com.sidewayscoding.MultisetLike
import scala.collection.mutable.Builder
import scala.collection.generic.CanBuildFrom
import com.sidewayscoding.Multiset

/**
 * @author mads379
 */
object ListMultiset extends ImmutableMultisetFactory[ListMultiset] {
  
  def empty[A] = apply()
  
  def apply[A](): ListMultiset[A] = 
    new ListMultiset[A](Map[A,List[A]]())
  
}

class ListMultiset[A] private[immutable] (val delegate: Map[A, List[A]]) extends Multiset[A]
                                                                            with MultisetLike[A, ListMultiset[A]] {
  
  def empty = new ListMultiset[A](Map[A, List[A]]())
  
  def iterator: Iterator[A] = new ListMultisetIterator(delegate)

  override def newBuilder: Builder[A, ListMultiset[A]] = ListMultiset.newBuilder

  def seq: ListMultiset[A] = this

  override def size: Int = {
    delegate.map( _._2.size ).sum
  }

  def multiplicity(a: A): Int = {
    delegate.get(a).map( _.size ).getOrElse(0)
  }

  def +(a: A) = {
    val newList = a :: delegate.getOrElse(a, Nil)
    new ListMultiset(delegate.updated(a, newList))
  }

  def -(a: A) = {
    if (delegate.contains(a)) {
      val list = delegate.get(a).get
      val newList = list - a
      if (newList.isEmpty) {
        new ListMultiset(delegate - a)
      } else {
        new ListMultiset(delegate.updated(a, newList))
      }
    } else {
      this
    }
  }
  
}

private class ListMultisetIterator[A](private val tm: Map[A, List[A]]) extends Iterator[A] {
  
  private val mapIterator = tm.iterator
  private var listIterator: Option[Iterator[A]] = None
  
  def hasNext: Boolean = {
    listIterator.map(_.hasNext).getOrElse(false) || mapIterator.hasNext
  }
  
  override def next(): A = {
    if (listIterator.map(_.hasNext).getOrElse(false)) {
      listIterator.get.next
    } else if(mapIterator.hasNext) {
      val newListIterator = mapIterator.next._2.iterator
      listIterator = Some(newListIterator)
      newListIterator.next
    } else {
      throw new NoSuchElementException("next on empty iterator")
    }
  }
  
}