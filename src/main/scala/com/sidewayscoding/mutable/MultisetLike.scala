package com.sidewayscoding.mutable

trait MultisetLike[A, +This <: Multiset[A] with MultisetLike[A, This]]
  extends com.sidewayscoding.MultisetLike[A, This] {

  def add(elem: A): Boolean = {
    val r = this.multiplicity(elem) > 0
    this += elem
    !r
  }

  def remove(elem: A): Boolean = {
    val r = this.multiplicity(elem) > 0
    this -= elem
    r
  }

  def removeAll(elem: A): Boolean

  def +=(elem: A): this.type

  def -=(elem: A): this.type

  def clear() { foreach(-=) }

}