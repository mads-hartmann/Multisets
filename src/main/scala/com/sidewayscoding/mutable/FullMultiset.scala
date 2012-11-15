package com.sidewayscoding.mutable

import scala.collection.generic.GenericCompanion
import scala.collection.generic.CanBuildFrom
import com.sidewayscoding.GenericMultisetTemplate

trait FullMultiset[A] extends Multiset[A]
                         with GenericMultisetTemplate[A, FullMultiset]
                         with FullMultisetLike[A, FullMultiset[A]] {

  override def companion: GenericCompanion[FullMultiset] = FullMultiset
  override def seq: FullMultiset[A] = this

}

object FullMultiset extends MutableMultisetFactory[FullMultiset] {

  override def empty[A]: FullMultiset[A] =
    FullHashMultiset.empty[A]

  implicit def canBuildFrom[A]: CanBuildFrom[Coll, A, FullMultiset[A]] =
    multisetCanBuildFrom[A]

}