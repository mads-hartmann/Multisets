package com.sidewayscoding.immutable

import com.sidewayscoding.MergeableMultiset
import com.sidewayscoding.MergeableMultisetLike
import scala.collection.mutable.Builder
import scala.collection.generic.CanBuildFrom
import com.sidewayscoding.MultisetBuilder

abstract class ImmutableMergeableMultisetFactory[CC[A] <: MergeableMultiset[A] with MergeableMultisetLike[A, CC[A]]] {

  type Coll = CC[_]

  def empty[A]: CC[A]

  def apply[A](elems: A*): CC[A] = (newBuilder[A] ++= elems).result

  def newBuilder[A]: Builder[A, CC[A]] = new MultisetBuilder[A, CC[A]](empty)

  implicit def newCanBuildFrom[A]: CanBuildFrom[Coll, A, CC[A]] = new MergeableMultisetCanBuildFrom();

  class MergeableMultisetCanBuildFrom[A] extends CanBuildFrom[Coll, A, CC[A]] {
    def apply(from: Coll) = newBuilder[A]
    def apply() = newBuilder[A]
  }
}