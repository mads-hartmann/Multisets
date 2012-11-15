package com.sidewayscoding.mutable

import scala.collection.mutable.HashMap
import com.sidewayscoding.GenericMultisetTemplate
import com.sidewayscoding.ElementSeq
import scala.collection.generic.CanBuildFrom
import scala.collection.generic.GenericCompanion

class CompactHashMultiset[A] private (protected val delegate: HashMap[A, ElementSeq[A]])
  extends CompactMultiset[A]
     with CompactMultisetLike[A, CompactHashMultiset[A]]
     with GenericMultisetTemplate[A, CompactHashMultiset]
     with MutableMapBasedMultiset[A, CompactHashMultiset[A], ElementSeq, HashMap[A, ElementSeq[A]]]{

  override def companion: GenericCompanion[CompactHashMultiset] = CompactHashMultiset
  override def seq: CompactHashMultiset[A] = this
  
  def fromMap(a: HashMap[A, ElementSeq[A]]) = new CompactHashMultiset(a)
  def emptySeq: Seq[A] = ElementSeq[A]()
  
}

object CompactHashMultiset extends MutableMultisetFactory[CompactHashMultiset] {

  override def empty[A] = new CompactHashMultiset[A](HashMap[A, ElementSeq[A]]())
  implicit def canBuildFrom[A]: CanBuildFrom[Coll, A, CompactHashMultiset[A]] = multisetCanBuildFrom[A]

}