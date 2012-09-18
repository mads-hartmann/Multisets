package com.sidewayscoding.immutable

import com.sidewayscoding.MergeableMultiset
import com.sidewayscoding.MergeableMultisetLike
import com.sidewayscoding.Mergeable
import scala.collection.mutable.Builder
import scala.collection.generic.CanBuildFrom
import com.sidewayscoding.MultisetBuilder

abstract class ImmutableMergeableMultisetFactory[CC[A] <: MergeableMultiset[A] with MergeableMultisetLike[A, CC[A]]] {

  type Coll = CC[_]

  def empty[A](implicit mergeable: Mergeable[A]): CC[A]

  def apply[A](elems: A*)(implicit mergeable: Mergeable[A]): CC[A] = (newBuilder[A](mergeable) ++= elems).result

  def newBuilder[A](implicit mergeable: Mergeable[A]): Builder[A, CC[A]] = new MultisetBuilder[A, CC[A]](empty)

  implicit def newCanBuildFrom[A](implicit mergeable: Mergeable[A]) : CanBuildFrom[Coll, A, CC[A]] = new SortedSetCanBuildFrom()(mergeable);

  class SortedSetCanBuildFrom[A](implicit mergeable: Mergeable[A]) extends CanBuildFrom[Coll, A, CC[A]] {
    def apply(from: Coll) = newBuilder[A](mergeable)
    def apply() = newBuilder[A](mergeable)
  }
}