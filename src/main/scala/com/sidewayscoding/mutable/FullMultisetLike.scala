package com.sidewayscoding.mutable

trait FullMultisetLike[A, +This <: FullMultiset[A] with FullMultisetLike[A, This]]
  extends MultisetLike[A, This] {

  def remove(elem: A, eq: Equiv[A]): Boolean

  def removeAll(elem: A, eq: Equiv[A]): Boolean

  override def -=(elem: A): this.type = {
    remove(elem, implicitly[Equiv[A]])
    this
  }

  override def remove(elem: A): Boolean = remove(elem, implicitly[Equiv[A]])

  override def removeAll(elem: A): Boolean = removeAll(elem, implicitly[Equiv[A]])

  def removed(a: A, eq: Equiv[A]): This = {
    val c = this.clone()
    c.remove(a, eq)
    c
  }

  def removedAll(a: A, eq: Equiv[A]): This = {
    val c = this.clone()
    c.removeAll(a, eq)
    c
  }

}