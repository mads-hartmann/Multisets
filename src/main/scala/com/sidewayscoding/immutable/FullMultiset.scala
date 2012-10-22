package com.sidewayscoding.immutable

import scala.collection.generic.GenericCompanion
import scala.collection.generic.CanBuildFrom
import com.sidewayscoding.GenericMultisetTemplate
import com.sidewayscoding.FullMultisetLike
import com.sidewayscoding.MultisetFactory

trait FullMultiset[A] extends Iterable[A]
                         with com.sidewayscoding.FullMultiset[A]
                         with GenericMultisetTemplate[A, FullMultiset]
                         with FullMultisetLike[A, FullMultiset[A]] {

  override def companion: GenericCompanion[FullMultiset] = FullMultiset
  override def seq: FullMultiset[A] = this

}

object FullMultiset extends MultisetFactory[FullMultiset] {

  override def empty[A]: FullMultiset[A] =
    FullHashMultiset.empty[A]

  implicit def canBuildFrom[A]: CanBuildFrom[Coll, A, FullMultiset[A]] =
    multisetCanBuildFrom[A]

}