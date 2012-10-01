package com.sidewayscoding

import com.sidewayscoding.immutable.ListMultiset
import scala.collection.GenIterable
import scala.collection.generic.GenericCompanion
import scala.collection.generic.GenericTraversableTemplate
import com.sidewayscoding.immutable.ImmutableMultisetFactory

trait Multiset[A] extends Iterable[A]
                     with MultisetLike[A, Multiset[A]]
                     with GenIterable[A]
                     with GenericMultisetTemplate[A, Multiset] {

  override def companion: GenericCompanion[Multiset] = Multiset
  override def seq: this.type = this

}

object Multiset extends ImmutableMultisetFactory[Multiset] {

  /*TODO: We probably don't want to use this as the default implementation. */
  override def empty[A] = ListMultiset[A]()

}