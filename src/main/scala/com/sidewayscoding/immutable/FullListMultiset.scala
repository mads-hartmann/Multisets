package com.sidewayscoding.immutable

import com.sidewayscoding.MultisetLike
import scala.collection.mutable.Builder
import scala.collection.generic.CanBuildFrom
import scala.collection.immutable.ListMap
import com.sidewayscoding.FullMultisetLike
import com.sidewayscoding.GenericMultisetTemplate
import scala.collection.generic.GenericCompanion

/**
 * @author mads379
 */
object FullListMultiset extends ImmutableMultisetFactory[FullListMultiset] {

  override def empty[A] = new FullListMultiset[A](ListMap[A, List[A]]())
  implicit def canBuildFrom[A]: CanBuildFrom[Coll, A, FullListMultiset[A]] = multisetCanBuildFrom[A]

}

class FullListMultiset[A] private[immutable] (val delegate: ListMap[A, List[A]]) extends Multiset[A]
                                                                                with FullMultiset[A]
                                                                                with GenericMultisetTemplate[A, FullListMultiset]
                                                                                with FullMultisetLike[A, FullListMultiset[A]] {
  override def companion: GenericCompanion[FullListMultiset] = FullListMultiset
  override def seq = FullListMultiset.this

  def multiplicities = delegate.mapValues(_.size)

  def copies: Map[A, Seq[A]] = delegate

  def get(a: A): Seq[A] = delegate.getOrElse(a, Nil)

  def iterator: Iterator[A] = new ListMultisetIterator(delegate)

  override def newBuilder: Builder[A, FullListMultiset[A]] = FullListMultiset.newBuilder

  override def size: Int = {
    delegate.map( _._2.size ).sum
  }

  def multiplicity(a: A): Int = {
    delegate.get(a).map( _.size ).getOrElse(0)
  }

  def +(a: A) = {
    val newList = a :: delegate.getOrElse(a, Nil)
    new FullListMultiset(delegate.updated(a, newList))
  }

  def removed(a: A) = removed(a: A, implicitly[Equiv[A]])

  def removedAll(a: A) = removedAll(a, implicitly[Equiv[A]])

  def removed(a: A, eq: Equiv[A]) = {
    if (delegate.contains(a)) {
        val list = delegate.get(a).get
        if (list.size == 1) {
          new FullListMultiset(delegate - a)
        } else {
          val toBeRemoved = list.filter( eq.equiv(_, a))
          val newList = list.filterNot(_ == a) ++ toBeRemoved.tail
          new FullListMultiset(delegate.updated(a, newList))
        }
      } else {
      FullListMultiset.this
    }
  }

  def removedAll(a: A, eq: Equiv[A]) = {
    if (delegate.contains(a)) {
        val list = delegate.get(a).get
        if (list.size == 1) {
          new FullListMultiset(delegate - a)
        } else {
          val newList = list.filterNot( eq.equiv(_, a))
          new FullListMultiset(delegate.updated(a, newList))
        }
      } else {
      FullListMultiset.this
    }
  }

}

private class ListMultisetIterator[A](private val tm: ListMap[A, List[A]]) extends Iterator[A] {

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