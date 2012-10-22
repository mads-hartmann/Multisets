package com.sidewayscoding.immutable

import scala.collection.generic.CanBuildFrom
import scala.collection.generic.GenericCompanion
import com.sidewayscoding.MultisetLike
import com.sidewayscoding.GenericMultisetTemplate

trait Multiset[A] extends Iterable[A]
                     with com.sidewayscoding.Multiset[A]
                     with GenericMultisetTemplate[A, Multiset]
                     with MultisetLike[A, Multiset[A]] {

  override def companion: GenericCompanion[Multiset] = Multiset
  override def seq: Multiset[A] = this

}

object Multiset extends ImmutableMultisetFactory[Multiset] {

  override def empty[A]: Multiset[A] =
    com.sidewayscoding.immutable.FullHashMultiset.empty[A]

  implicit def canBuildFrom[A]: CanBuildFrom[Coll, A, Multiset[A]] =
    multisetCanBuildFrom[A]

}