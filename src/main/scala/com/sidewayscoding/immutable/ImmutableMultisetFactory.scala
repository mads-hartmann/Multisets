package com.sidewayscoding.immutable

import com.sidewayscoding.Multiset
import com.sidewayscoding.MultisetLike
import com.sidewayscoding.MultisetBuilder
import scala.collection.mutable.Builder
import scala.collection.generic.CanBuildFrom

abstract class ImmutableMultisetFactory[CC[X] <: Multiset[X] with MultisetLike[X, CC[X]]] {

  type Coll = CC[_]

  def empty[A]: CC[A]

  def apply[A](elems: A*): CC[A] = (newBuilder[A] ++= elems).result

  def newBuilder[A]: Builder[A, CC[A]] = new MultisetBuilder[A, CC[A]](empty)

  implicit def newCanBuildFrom[A]: CanBuildFrom[Coll, A, CC[A]] = new MultisetCanBuildFrom();

  class MultisetCanBuildFrom[A] extends CanBuildFrom[Coll, A, CC[A]] {
    def apply(from: Coll) = newBuilder[A]
    def apply() = newBuilder[A]
  }

 }