package com.sidewayscoding.mutable

import scala.collection.generic.Growable
import scala.collection.generic.Shrinkable
import scala.collection.mutable.Cloneable
import scala.collection.mutable.Builder

trait MultisetLike[A, +This <: Multiset[A] with MultisetLike[A, This]]
  extends com.sidewayscoding.MultisetLike[A, This]
  with Builder[A, This]
  with Growable[A]
  with Shrinkable[A]
  with Cloneable[com.sidewayscoding.mutable.Multiset[A]] { self =>

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
  
  def result: This = repr

  override def clone(): This = empty ++= repr.seq

  override def + (elem: A): This = clone() += elem

}