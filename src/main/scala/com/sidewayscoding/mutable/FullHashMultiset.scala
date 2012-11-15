package com.sidewayscoding.mutable

import scala.collection.mutable.FlatHashTable
import com.sidewayscoding.GenericMultisetTemplate
import scala.collection.mutable.FlatHashTable
import scala.collection.generic.CanBuildFrom
import scala.collection.generic.GenericCompanion
import sun.reflect.generics.reflectiveObjects.NotImplementedException
import scala.collection.mutable.Builder
import scala.collection.mutable.HashMap

class FullHashMultiset[A] private (protected val delegate: HashMap[A, List[A]]) 
  extends FullMultiset[A]
     with FullMultisetLike[A, FullHashMultiset[A]]
     with GenericMultisetTemplate[A, FullHashMultiset]
     with MutableMapBasedFullMultiset[A, FullHashMultiset[A], List, HashMap[A, List[A]]]{

  override def companion: GenericCompanion[FullHashMultiset] = FullHashMultiset
  override def seq: FullHashMultiset[A] = this
  
  def fromMap(a: HashMap[A, List[A]]) = new FullHashMultiset(a)
  def emptySeq: Seq[A] = List[A]() 

}

object FullHashMultiset extends MutableMultisetFactory[FullHashMultiset] {

  override def empty[A] = new FullHashMultiset[A](HashMap[A, List[A]]())
  implicit def canBuildFrom[A]: CanBuildFrom[Coll, A, FullHashMultiset[A]] = multisetCanBuildFrom[A]

}