package com.sidewayscoding.mutable

import scala.collection.generic.GenericCompanion
import com.sidewayscoding.GenericMultisetTemplate
import scala.collection.generic.CanBuildFrom

trait Multiset[A] extends Iterable[A]
                     with com.sidewayscoding.Multiset[A]
                     with GenericMultisetTemplate[A, Multiset]
                     with MultisetLike[A, Multiset[A]] {

  override def companion: GenericCompanion[Multiset] = Multiset
  override def seq: Multiset[A] = this

}

object Multiset extends MutableMultisetFactory[Multiset] {

  override def empty[A]: Multiset[A] =
    com.sidewayscoding.mutable.FullHashMultiset.empty[A]

  implicit def canBuildFrom[A]: CanBuildFrom[Coll, A, Multiset[A]] =
    multisetCanBuildFrom[A]

}