package com.sidewayscoding.mutable

import com.sidewayscoding.MapBasedMultiset
import scala.collection.mutable.Map
import com.sidewayscoding.ElementSeq

trait MutableMapBasedMultiset[A, +This <: Multiset[A] with MultisetLike[A, This], S[A] <: Seq[A], MapRep <: Map[A, S[A]]] { this: This =>

  protected val delegate: MapRep
  
  def fromMap(a: MapRep): This
  def emptySeq: Seq[A]
  
  /**
   * Return the multiplicity of the element `a` in the multiset.
   */
  def multiplicity(a: A): Int = {
    delegate.get(a).map( _.size ).getOrElse(0)
  }

  /**
   * Returns the total number of elements in the multiset, i.e. the 
   * sum of the multiplicity of each element
   */
  override def size: Int = delegate.values.map(_.size).sum

  /**
   * return a map from each distinct distinct element to the multiplicity of
   * that element in the multiset
   */
  def multiplicities = delegate.mapValues(_.size).toMap

  def iterator: Iterator[A] = new com.sidewayscoding.immutable.ListMultisetIterator(delegate.toMap)
  
  def +=(elem: A): this.type = {
    val seq = delegate.getOrElse(elem, emptySeq) :+ elem
    delegate.update(elem, seq.asInstanceOf[S[A]])
    this
  }
  
  def -= (a: A): this.type = {
    if (delegate.contains(a)) {
      val seq = delegate.get(a).get
      if (seq.size <= 1) {
        delegate - a
      } else {
        val toBeRemoved = seq.filter(_ == a)
        val newSeq = seq.filterNot(_ == a) ++ toBeRemoved.tail
        delegate.update(a, newSeq.asInstanceOf[S[A]])
      }
    }
    this
  }


  def removeAll(a: A): Boolean = {
    if (delegate.contains(a)) {
      val list = delegate.get(a).get
      val staying = list.filterNot(_ == a)
      if (staying.isEmpty) {
        delegate - a
      } else {
        delegate.updated(a, staying)
      }
      true
    } else {
      false
    }
  }

}

trait MutableMapBasedFullMultiset[A, +This <: Multiset[A] with MultisetLike[A, This], S[A] <: Seq[A], MapRep <: Map[A, S[A]]]
  extends MutableMapBasedMultiset[A, This, S, MapRep] { this: This =>

  def remove(a: A, eq: A => Boolean): Boolean = {
    if (delegate.contains(a)) {
      val seq = delegate.get(a).get
      if (seq.size <= 1) {
        delegate - a
        true
      } else {
        val toBeRemoved = seq.filter(eq)
        val newSeq = seq.filterNot(eq) ++ toBeRemoved.tail
        delegate.update(a, newSeq.asInstanceOf[S[A]])
        true
      }
    } else {
      false
    }
  }

  def removeAll(a: A, eq: A => Boolean): Boolean = {
    if (delegate.contains(a)) {
      val list = delegate.get(a).get
      val staying = list.filterNot(eq)
      if (staying.isEmpty) {
        delegate - a
      } else {
        delegate.updated(a, staying)
      }
      true
    } else {
      false
    }
  }

}