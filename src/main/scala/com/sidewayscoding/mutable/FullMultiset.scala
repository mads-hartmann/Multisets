package com.sidewayscoding.mutable

import scala.collection.generic.GenericCompanion
import scala.collection.generic.CanBuildFrom
import com.sidewayscoding.GenericMultisetTemplate

// TODO: Seems broken that I have to extend both com.sidewayscoding.* and com.sidewayscoding.mutable.*. The 
// mutable version should extend com.sidewayscoding.* copies so it shouldn't really be a problem..
trait FullMultiset[A] extends Iterable[A]
                         with Multiset[A]
                         with com.sidewayscoding.FullMultiset[A]
                         with GenericMultisetTemplate[A, FullMultiset]
                         with FullMultisetLike[A, FullMultiset[A]]
                         with com.sidewayscoding.FullMultisetLike[A, FullMultiset[A]] {

  override def companion: GenericCompanion[FullMultiset] = FullMultiset
  override def seq: FullMultiset[A] = this

}

object FullMultiset extends MutableMultisetFactory[FullMultiset] {

  override def empty[A]: FullMultiset[A] =
    FullHashMultiset.empty[A]

  implicit def canBuildFrom[A]: CanBuildFrom[Coll, A, FullMultiset[A]] =
    multisetCanBuildFrom[A]

}