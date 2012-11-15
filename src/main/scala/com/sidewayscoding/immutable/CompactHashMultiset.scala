package com.sidewayscoding.immutable

import scala.collection.generic.CanBuildFrom
import scala.collection.generic.GenericCompanion
import scala.collection.immutable.HashMap
import scala.collection.mutable.Builder
import com.sidewayscoding.CompactMultisetLike
import com.sidewayscoding.GenericMultisetTemplate
import com.sidewayscoding.MapBasedMultiset
import com.sidewayscoding.ElementSeq

object CompactHashMultiset extends ImmutableCompactMultisetFactory[CompactHashMultiset] {

  override def empty[A] = apply()

  implicit def canBuildFrom[A]: CanBuildFrom[Coll, A, CompactHashMultiset[A]] =
    multisetCanBuildFrom[A]

  def apply[A](): CompactHashMultiset[A] =
    new CompactHashMultiset[A](HashMap[A,ElementSeq[A]]())

}

/**
 *  Don't use. This is a reference implementation with horrible performance.
 *
 *  The aim is to create an implementation that obviously has no bugs (rather
 *  than one that has no obvious bugs) so it can be used to test more advanced
 *  implementations.
 */
class CompactHashMultiset[A] private[immutable] (protected val delegate: HashMap[A, ElementSeq[A]]) extends CompactMultiset[A]
                                                                                   with CompactMultisetLike[A, CompactHashMultiset[A]]
                                                                                   with GenericMultisetTemplate[A, CompactHashMultiset]
                                                                                   with MapBasedMultiset[A, CompactHashMultiset[A], HashMap[A, ElementSeq[A]]] {

  override def companion: GenericCompanion[CompactHashMultiset] = CompactHashMultiset
  override def newBuilder: Builder[A, CompactHashMultiset[A]] = CompactHashMultiset.newBuilder
  def fromMap(m: HashMap[A, ElementSeq[A]]) = new CompactHashMultiset(m)
  def emptySeq: ElementSeq[A] = ElementSeq()

}