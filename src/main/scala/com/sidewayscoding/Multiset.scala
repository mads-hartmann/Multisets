package com.sidewayscoding

import scala.collection.GenIterable
import scala.collection.generic.GenericCompanion
import scala.collection.generic.CanBuildFrom

/**
 * Multisets are sets that allow multiple occurrences of the same element
 *
 * {{{
 * val ms = Multiset(1,2,3,3,2)
 * ms.multiplicity(3) // 2
 * ms.size() // 5
 * }}}
 *
 * A distinction is made bewtween Multisets that keep all elements in memory (`FullMultiset`) and
 * those that store only the first of equal elements and use a counter to keep
 * track of the multiplicity (`CompactMultiset`). This distinction is important if you have defined an
 * equals method that does not consider all properties of the elements as you would
 * then loose imformation if you only stored the first of equal elements.
 */
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