package com.sidewayscoding.immutable

import com.sidewayscoding.MultisetLike
import scala.collection.mutable.Builder
import scala.collection.generic.CanBuildFrom
import scala.collection.immutable.HashMap
import com.sidewayscoding.FullMultisetLike
import com.sidewayscoding.GenericMultisetTemplate
import scala.collection.generic.GenericCompanion
import com.sidewayscoding.MapBasedFullMultiset


object FullHashMultiset extends ImmutableMultisetFactory[FullHashMultiset] {

  override def empty[A] = new FullHashMultiset[A](HashMap[A, List[A]]())
  implicit def canBuildFrom[A]: CanBuildFrom[Coll, A, FullHashMultiset[A]] = multisetCanBuildFrom[A]

}

/**
 * A FullMultiset implementation based on a HashMap. It is very important that
 * the equals method of the elements you add obey the hashCode specification 
 * defined here http://docs.oracle.com/javase/1.5.0/docs/api/java/lang/Object.html#hashCode()
 */
class FullHashMultiset[A] private[immutable] (protected val delegate: HashMap[A, List[A]]) 
  extends FullMultiset[A]
     with GenericMultisetTemplate[A, FullHashMultiset]
     with FullMultisetLike[A, FullHashMultiset[A]] 
     with MapBasedFullMultiset[A, FullHashMultiset[A], HashMap[A, List[A]]]{

  override def companion: GenericCompanion[FullHashMultiset] = FullHashMultiset
  override def seq = FullHashMultiset.this
  
  def fromMap(a: HashMap[A, List[A]]) = new FullHashMultiset(a)
  def emptySeq: Seq[A] = Nil
}

class ListMultisetIterator[A](private val tm: Map[A, Seq[A]]) extends Iterator[A] {

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