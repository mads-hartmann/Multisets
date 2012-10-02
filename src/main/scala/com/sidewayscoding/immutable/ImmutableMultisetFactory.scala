package com.sidewayscoding.immutable

import com.sidewayscoding.Multiset
import com.sidewayscoding.MultisetLike
import com.sidewayscoding.MultisetBuilder
import scala.collection.mutable.Builder
import scala.collection.generic.CanBuildFrom
import scala.collection.generic.GenericCompanion

abstract class ImmutableMultisetFactory[CC[X] <: Multiset[X] with MultisetLike[X, CC[X]]] 
       extends GenericCompanion[CC] {

  override def newBuilder[A]: Builder[A, CC[A]] = new MultisetBuilder[A, CC[A]](empty[A])

  def multisetCanBuildFrom[A] = new CanBuildFrom[CC[_], A, CC[A]] {
    def apply(from: CC[_]) = newBuilder[A]
    def apply() = newBuilder[A]
  }

 }