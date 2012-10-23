package com.sidewayscoding.mutable

import scala.collection.mutable.FlatHashTable
import com.sidewayscoding.GenericMultisetTemplate
import scala.collection.mutable.FlatHashTable
import scala.collection.generic.CanBuildFrom
import scala.collection.generic.GenericCompanion
import sun.reflect.generics.reflectiveObjects.NotImplementedException
import scala.collection.mutable.Builder

// TODO: Something is wrong with the inheritance. See mutable.FullMultiset for a more descriptive note.
class FullHashMultiset[A] private (val delegate: FlatHashTable[A]) extends Multiset[A]
                                                                      with FullMultiset[A]
                                                                      with com.sidewayscoding.FullMultiset[A]
                                                                      with GenericMultisetTemplate[A, FullHashMultiset]
                                                                      with FullMultisetLike[A, FullHashMultiset[A]] 
                                                                      with com.sidewayscoding.FullMultisetLike[A, FullHashMultiset[A]]{

  override def companion: GenericCompanion[FullHashMultiset] = FullHashMultiset
  override def seq = FullHashMultiset.this
  override def newBuilder: Builder[A, FullHashMultiset[A]] = FullHashMultiset.newBuilder
  
  def multiplicities = throw new NotImplementedException
  def copies = throw new NotImplementedException
  def get(a: A) = throw new NotImplementedException
  def iterator = throw new NotImplementedException
  override def size: Int = throw new NotImplementedException
  def multiplicity(a: A) = throw new NotImplementedException
  def remove(a: A, eq: Equiv[A]): Boolean = throw new NotImplementedException
  def removeAll(a: A, eq: Equiv[A]): Boolean = throw new NotImplementedException
  def -=(a: A): this.type = throw new NotImplementedException
  def +=(a: A): this.type = throw new NotImplementedException
  
  def +(a: A): this.type = throw new NotImplementedException
  def removed(a: A, eq: Equiv[A]): this.type = throw new NotImplementedException
  def removedAll(a: A, eq: Equiv[A]): this.type = throw new NotImplementedException
  
}

object FullHashMultiset extends MutableMultisetFactory[FullHashMultiset] {
  
  override def empty[A] = new FullHashMultiset[A](new Object with FlatHashTable[A])
  implicit def canBuildFrom[A]: CanBuildFrom[Coll, A, FullHashMultiset[A]] = multisetCanBuildFrom[A]
  
}

