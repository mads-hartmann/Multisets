package com.sidewayscoding.mutable

trait FullMultisetLike[A, +This <: FullMultiset[A] with FullMultisetLike[A, This]]
  extends MultisetLike[A, This] {

  def remove(elem: A, eq: Equiv[A]): Boolean

  def removeAll(elem: A, eq: Equiv[A]): Boolean
  
  override def remove(elem: A): Boolean = remove(elem, implicitly[Equiv[A]])
  
  override def removeAll(elem: A): Boolean = removeAll(elem, implicitly[Equiv[A]])

}