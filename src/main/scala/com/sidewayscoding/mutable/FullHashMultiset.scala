package com.sidewayscoding.mutable

import scala.collection.mutable.FlatHashTable
import com.sidewayscoding.GenericMultisetTemplate
import scala.collection.mutable.FlatHashTable
import scala.collection.generic.CanBuildFrom
import scala.collection.generic.GenericCompanion
import sun.reflect.generics.reflectiveObjects.NotImplementedException
import scala.collection.mutable.Builder

class FullHashMultiset[A] private (private val delegate: FlatHashTable[Bucket[A]]) 
  extends FullMultiset[A]
     with GenericMultisetTemplate[A, FullHashMultiset]
     with FullMultisetLike[A, FullHashMultiset[A]] {

  override def companion: GenericCompanion[FullHashMultiset] =
    FullHashMultiset

  override def seq =
    FullHashMultiset.this

  override def newBuilder: Builder[A, FullHashMultiset[A]] =
    FullHashMultiset.newBuilder

  def multiplicity(a: A): Int =
    delegate.findEntry(Bucket(a)).map(_.list.size).getOrElse(0)

  def multiplicities: Map[A, Int] =
    delegate.iterator.map( b => (b.list.head, b.list.size)).toMap

  def copies: Map[A, Seq[A]] =
    delegate.iterator.map( b => (b.list.head, b.list)).toMap

  def get(a: A): Seq[A] =
    delegate.findEntry(Bucket(a)).map(_.list).getOrElse(Nil)

  def iterator =
    delegate.iterator.map(_.list).flatten

  override def size: Int =
    delegate.iterator.map(_.list.size).sum

  def remove(a: A, eq: A => Boolean): Boolean = {
    if (delegate.containsEntry(Bucket(a))) {
      val old      = delegate.findEntry(Bucket(a)).get
      val toStay   = old.list.filterNot(eq)
      val toRemove = old.list.filter(eq)
      if (toRemove.size > 0) {
        delegate.removeEntry(Bucket(a))
        val removed = if (toRemove.size == 1) Nil else toRemove.tail
        val staying = toStay ::: removed
        if (staying.size > 0) {
          delegate.addEntry(Bucket(staying))
        }
      }
      true
    } else {
      false
    }
  }

  def removeAll(a: A, eq: A => Boolean): Boolean = {
    if (delegate.containsEntry(Bucket(a))) {
      val old      = delegate.findEntry(Bucket(a)).get
      val toStay   = old.list.filterNot(eq)
      delegate.removeEntry(Bucket(a))
      if (toStay.size > 0) {
        delegate.addEntry(Bucket(toStay))
      }
      true
    } else {
      false
    }
  }

  def +=(a: A): this.type = {
    if (delegate.containsEntry(Bucket(a))) {
      val old = delegate.findEntry(Bucket(a)).get
      delegate.removeEntry(Bucket(a))
      delegate.addEntry(old + a)
    } else {
      delegate.addEntry(Bucket(a))
    }
    this
  }

}

object FullHashMultiset extends MutableMultisetFactory[FullHashMultiset] {

  override def empty[A] = new FullHashMultiset[A](new Object with FlatHashTable[Bucket[A]])
  implicit def canBuildFrom[A]: CanBuildFrom[Coll, A, FullHashMultiset[A]] = multisetCanBuildFrom[A]

}

private case class Bucket[A](val list: List[A]) {
  override def equals(o: Any) = o match {
    case Bucket(x) => list.head == x.head
    case _ => false
  }
  override def hashCode = list.head.hashCode()
  def +(a: A): Bucket[A] = new Bucket(a :: list)
  def -(a: A): Bucket[A] = new Bucket(list.tail)
  def size: Int = list.size
}

private object Bucket {
  def apply[A](a: A): Bucket[A] = new Bucket(List(a))
}
