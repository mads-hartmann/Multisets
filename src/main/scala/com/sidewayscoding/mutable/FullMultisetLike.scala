package com.sidewayscoding.mutable

trait FullMultisetLike[A, +This <: FullMultiset[A] with FullMultisetLike[A, This]]
  extends MultisetLike[A, This]
     with com.sidewayscoding.FullMultisetLike[A, This] {

  def remove(elem: A, eq: A => Boolean): Boolean

  def removeAll(elem: A, eq: A => Boolean): Boolean

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