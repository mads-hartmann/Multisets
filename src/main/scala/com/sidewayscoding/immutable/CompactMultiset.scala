package com.sidewayscoding.immutable

import scala.collection.generic.GenericCompanion
import scala.collection.generic.CanBuildFrom
import com.sidewayscoding.GenericMultisetTemplate
import com.sidewayscoding.CompactMultisetLike
import com.sidewayscoding.MultisetFactory

trait CompactMultiset[A] extends Iterable[A]
                            with com.sidewayscoding.CompactMultiset[A]
                            with GenericMultisetTemplate[A, CompactMultiset]
                            with CompactMultisetLike[A, CompactMultiset[A]] {

  override def companion: GenericCompanion[CompactMultiset] = CompactMultiset
  override def seq: CompactMultiset[A] = this

}

object CompactMultiset extends ImmutableCompactMultisetFactory[CompactMultiset] {

  override def empty[A]: CompactMultiset[A] =
    CompactListMultiset.empty[A]

  implicit def canBuildFrom[A]: CanBuildFrom[Coll, A, CompactMultiset[A]] =
    multisetCanBuildFrom[A]

}