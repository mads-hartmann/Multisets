package com.sidewayscoding.mutable

trait FullMultisetLike[A, +This <: FullMultiset[A] with FullMultisetLike[A, This]]
  extends MultisetLike[A, This] {

  def remove(elem: A, eq: A => Boolean): Boolean

  def removeAll(elem: A, eq: A => Boolean): Boolean

  override def -=(elem: A): this.type = {
    remove(elem, implicitly[Equiv[A]].equiv(elem,_))
    this
  }

  override def remove(elem: A): Boolean = remove(elem, implicitly[Equiv[A]].equiv(elem,_))

  override def removeAll(elem: A): Boolean = removeAll(elem, implicitly[Equiv[A]].equiv(elem,_))

  def removed(a: A, eq: A => Boolean): This = {
    val c = this.clone()
    c.remove(a, eq)
    c
  }

  def removedAll(a: A, eq: A => Boolean): This = {
    val c = this.clone()
    c.removeAll(a, eq)
    c
  }

}