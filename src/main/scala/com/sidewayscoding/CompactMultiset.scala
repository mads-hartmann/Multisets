package com.sidewayscoding

import scala.collection.generic.GenericCompanion
import scala.collection.generic.CanBuildFrom

trait CompactMultiset[A] extends Iterable[A]
                            with com.sidewayscoding.Multiset[A]
                            with GenericMultisetTemplate[A, CompactMultiset]
                            with CompactMultisetLike[A, CompactMultiset[A]] {

  override def companion: GenericCompanion[CompactMultiset] = CompactMultiset
  override def seq: Multiset[A] = this

}

object CompactMultiset extends MultisetFactory[CompactMultiset] {

  override def empty[A]: CompactMultiset[A] =
    com.sidewayscoding.immutable.CompactMultiset.empty[A]

  implicit def canBuildFrom[A]: CanBuildFrom[Coll, A, Multiset[A]] =
    multisetCanBuildFrom[A]

}