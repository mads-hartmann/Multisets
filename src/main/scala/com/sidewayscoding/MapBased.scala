package com.sidewayscoding

trait MapBasedMultiset[A, +This <: Multiset[A] with MultisetLike[A, This], MapRep <: Map[A, Seq[A]]] { this: This =>
  
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
  def multiplicities = delegate.mapValues(_.size)

  def iterator: Iterator[A] = new com.sidewayscoding.immutable.ListMultisetIterator(delegate)
  
  def removed(a: A): This = {
    if (delegate.contains(a)) {
      val seq = delegate.get(a).get
      if (seq.size <= 1) {
        fromMap((delegate - a).asInstanceOf[MapRep])
      } else {
        val toBeRemoved = seq.filter(_ == a)
        val newSeq = seq.filterNot(_ == a) ++ toBeRemoved.tail
        fromMap(delegate.updated(a, newSeq).asInstanceOf[MapRep])
      }
    } else {
      this
    }
  }
  
  def +(a: A): This = {
    val newSeq    = delegate.getOrElse(a, emptySeq) :+ a
    val newDelegate = delegate.updated(a, newSeq).asInstanceOf[MapRep]
    fromMap(newDelegate)
  }

  def removedAll(a: A): This = {
    fromMap((delegate - a).asInstanceOf[MapRep])
  }
}

trait MapBasedFullMultiset[A, +This <: Multiset[A] with MultisetLike[A, This], MapRep <: Map[A, Seq[A]]]
  extends MapBasedMultiset[A, This, MapRep] { this: This =>

  def removed(a: A, eq: A => Boolean): This = {
    if (delegate.contains(a)) {
      val list = delegate.get(a).get
      if (list.size <= 1) {
        fromMap((delegate - a).asInstanceOf[MapRep])
      } else {
        val toBeRemoved = list.filter(eq)
        val newList = list.filterNot(eq) ++ toBeRemoved.tail
        fromMap(delegate.updated(a, newList).asInstanceOf[MapRep])
      }
    } else {
      this
    }
  }

  def removedAll(a: A, eq: A => Boolean): This = {
    if (delegate.contains(a)) {
      val list = delegate.get(a).get
      val staying = list.filterNot(eq)
      if (staying.isEmpty) {
        fromMap((delegate - a).asInstanceOf[MapRep])
      } else {
        fromMap(delegate.updated(a, staying).asInstanceOf[MapRep])
      }
    } else {
      this
    }
  }

  def copies: Map[A, Seq[A]] = delegate

  def get(a: A): Seq[A] = delegate.getOrElse(a, Nil)

}