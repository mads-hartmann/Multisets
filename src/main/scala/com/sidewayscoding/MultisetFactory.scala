package com.sidewayscoding

import scala.collection.generic.CanBuildFrom
import scala.collection.generic.GenericCompanion
import scala.collection.mutable.Builder

abstract class MultisetFactory[CC[X] <: com.sidewayscoding.Multiset[X] with MultisetLike[X, CC[X]]] 
       extends GenericCompanion[CC] {

  override def newBuilder[A]: Builder[A, CC[A]] = new MultisetBuilder[A, CC[A]](empty[A])

  def multisetCanBuildFrom[A] = new CanBuildFrom[CC[_], A, CC[A]] {
    def apply(from: CC[_]) = newBuilder[A]
    def apply() = newBuilder[A]
  }

 }