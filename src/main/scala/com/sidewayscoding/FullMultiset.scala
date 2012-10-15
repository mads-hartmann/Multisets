package com.sidewayscoding

import scala.collection.generic.GenericCompanion
import scala.collection.generic.CanBuildFrom

trait FullMultiset[A] extends Iterable[A]
                         with com.sidewayscoding.Multiset[A]
                         with GenericMultisetTemplate[A, FullMultiset]
                         with FullMultisetLike[A, FullMultiset[A]] {

  override def companion: GenericCompanion[FullMultiset] = FullMultiset
  override def seq: Multiset[A] = this

}

object FullMultiset extends MultisetFactory[FullMultiset] {

  override def empty[A]: FullMultiset[A] =
    com.sidewayscoding.immutable.FullMultiset.empty[A]

  implicit def canBuildFrom[A]: CanBuildFrom[Coll, A, FullMultiset[A]] =
    multisetCanBuildFrom[A]

}