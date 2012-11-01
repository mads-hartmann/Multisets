package com.sidewayscoding.immutable

import com.sidewayscoding.MultisetLike
import scala.collection.mutable.Builder
import scala.collection.generic.CanBuildFrom
import scala.collection.immutable.HashMap
import com.sidewayscoding.FullMultisetLike
import com.sidewayscoding.GenericMultisetTemplate
import scala.collection.generic.GenericCompanion


object FullHashMultiset extends ImmutableMultisetFactory[FullHashMultiset] {

  override def empty[A] = new FullHashMultiset[A](HashMap[A, List[A]]())
  implicit def canBuildFrom[A]: CanBuildFrom[Coll, A, FullHashMultiset[A]] = multisetCanBuildFrom[A]

}

/**
 * A FullMultiset implementation based on a HashMap. It is very important that
 * the equals method of the elements you add obey the hashCode specification 
 * defined here http://docs.oracle.com/javase/1.5.0/docs/api/java/lang/Object.html#hashCode()
 */
class FullHashMultiset[A] private[immutable] (private val delegate: HashMap[A, List[A]]) extends Multiset[A]
                                                                                with FullMultiset[A]
                                                                                with GenericMultisetTemplate[A, FullHashMultiset]
                                                                                with FullMultisetLike[A, FullHashMultiset[A]] {
  override def companion: GenericCompanion[FullHashMultiset] = FullHashMultiset
  override def seq = FullHashMultiset.this

  def multiplicities = delegate.mapValues(_.size)

  def copies: Map[A, Seq[A]] = delegate

  def get(a: A): Seq[A] = delegate.getOrElse(a, Nil)

  def iterator: Iterator[A] = new ListMultisetIterator(delegate)

  override def newBuilder: Builder[A, FullHashMultiset[A]] = FullHashMultiset.newBuilder

  override def size: Int = {
    delegate.map( _._2.size ).sum
  }

  def multiplicity(a: A): Int = {
    delegate.get(a).map( _.size ).getOrElse(0)
  }

  def +(a: A) = {
    val newList = a :: delegate.getOrElse(a, Nil)
    new FullHashMultiset(delegate.updated(a, newList))
  }

  def removed(a: A, eq: A => Boolean) = {
    if (delegate.contains(a)) {
        val list = delegate.get(a).get
        if (list.size <= 1) {
          new FullHashMultiset(delegate - a)
        } else {
          val toBeRemoved = list.filter(eq)
          val newList = list.filterNot(eq) ++ toBeRemoved.tail
          new FullHashMultiset(delegate.updated(a, newList))
        }
      } else {
      FullHashMultiset.this
    }
  }

  def removedAll(a: A, eq: A => Boolean) = {
    if (delegate.contains(a)) {
        val list = delegate.get(a).get
        val staying = list.filterNot(eq)
        if (staying.isEmpty) {
          new FullHashMultiset(delegate - a)
        } else { 
          new FullHashMultiset(delegate.updated(a, staying))
        }
      } else {
      FullHashMultiset.this
    }
  }

}

private class ListMultisetIterator[A](private val tm: HashMap[A, List[A]]) extends Iterator[A] {

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