package com.sidewayscoding.mutable

import scala.collection.mutable.FlatHashTable
import com.sidewayscoding.GenericMultisetTemplate
import scala.collection.mutable.FlatHashTable
import scala.collection.generic.CanBuildFrom
import scala.collection.generic.GenericCompanion
import sun.reflect.generics.reflectiveObjects.NotImplementedException
import scala.collection.mutable.Builder

// TODO: Something is wrong with the inheritance. See mutable.FullMultiset for a more descriptive note.
class FullHashMultiset[A] private (private val delegate: FlatHashTable[Bucket[A]]) 
  extends Multiset[A]
  with FullMultiset[A]
  with com.sidewayscoding.FullMultiset[A]
  with GenericMultisetTemplate[A, FullHashMultiset]
  with FullMultisetLike[A, FullHashMultiset[A]]
  with com.sidewayscoding.FullMultisetLike[A, FullHashMultiset[A]]{

  override def companion: GenericCompanion[FullHashMultiset] = FullHashMultiset
  override def seq = FullHashMultiset.this
  override def newBuilder: Builder[A, FullHashMultiset[A]] = FullHashMultiset.newBuilder

  def multiplicities = throw new NotImplementedException
  def copies = throw new NotImplementedException
  def get(a: A) = throw new NotImplementedException
  
  def iterator = delegate.iterator.map( (b: Bucket[A]) => b.list ).flatten
  
  override def size: Int = throw new NotImplementedException
  def multiplicity(a: A) = throw new NotImplementedException
  def remove(a: A, eq: Equiv[A]): Boolean = throw new NotImplementedException
  def removeAll(a: A, eq: Equiv[A]): Boolean = throw new NotImplementedException

  def -=(a: A): this.type = {
    if (delegate.containsEntry(Bucket(a))) {
      val old = delegate.findEntry(Bucket(a)).get
      delegate.removeEntry(Bucket(a))
      if (old.size > 1) {
        delegate.addEntry(old - a)
      }
    }
    this
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

  def removed(a: A, eq: Equiv[A]): this.type = throw new NotImplementedException
  def removedAll(a: A, eq: Equiv[A]): this.type = throw new NotImplementedException

}

object FullHashMultiset extends MutableMultisetFactory[FullHashMultiset] {

  override def empty[A] = new FullHashMultiset[A](new Object with FlatHashTable[Bucket[A]])
  implicit def canBuildFrom[A]: CanBuildFrom[Coll, A, FullHashMultiset[A]] = multisetCanBuildFrom[A]

}

private case class Bucket[A](val list: List[A]) {
  override def equals(o: Any) = list.head.equals(o)
  override def hashCode = list.head.hashCode
  def +(a: A): Bucket[A] = new Bucket(a :: list)
  def -(a: A): Bucket[A] = new Bucket(list.tail)
  def size: Int = list.size
}

private object Bucket {
  def apply[A](a: A): Bucket[A] = new Bucket(List(a))
}
