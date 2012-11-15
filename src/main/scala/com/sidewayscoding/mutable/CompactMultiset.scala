package com.sidewayscoding.mutable

import com.sidewayscoding.GenericMultisetTemplate
import scala.collection.generic.GenericCompanion
import scala.collection.generic.CanBuildFrom

trait CompactMultiset[A] extends Multiset[A]
                         with com.sidewayscoding.CompactMultiset[A]
                         with CompactMultisetLike[A, CompactMultiset[A]]
                         with GenericMultisetTemplate[A, CompactMultiset] {

  override def companion: GenericCompanion[CompactMultiset] = CompactMultiset
  override def seq: CompactMultiset[A] = this

}

object CompactMultiset extends MutableMultisetFactory[CompactMultiset] {

  override def empty[A]: CompactMultiset[A] =
    CompactMultiset.empty[A]

  implicit def canBuildFrom[A]: CanBuildFrom[Coll, A, CompactMultiset[A]] =
    multisetCanBuildFrom[A]

}