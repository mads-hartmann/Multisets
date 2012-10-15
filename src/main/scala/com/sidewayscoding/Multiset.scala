package com.sidewayscoding

import scala.collection.GenIterable
import scala.collection.generic.GenericCompanion
import scala.collection.generic.CanBuildFrom

trait Multiset[A] extends Iterable[A]
                     with MultisetLike[A, Multiset[A]]
                     with GenIterable[A]
                     with GenericMultisetTemplate[A, Multiset]{

  override def companion: GenericCompanion[Multiset] = Multiset
  override def seq: Multiset[A] = this

}

object Multiset extends MultisetFactory[Multiset] {

  override def empty[A]: Multiset[A] =
    com.sidewayscoding.immutable.Multiset.empty[A]

  implicit def canBuildFrom[A]: CanBuildFrom[Coll, A, Multiset[A]] =
    multisetCanBuildFrom

}