package com.sidewayscoding

import scala.collection.mutable.Builder

/** TODO: This is an exact copy of SetBuilder. Maybe they can be united? */ 
class MultisetBuilder[A, Coll <: Multiset[A] with MultisetLike[A, Coll]](empty: Coll) extends Builder[A, Coll] {
  protected var elems: Coll = empty
  def += (x: A): this.type = { elems = elems + x ; this}
  def clear() { elems = empty }
  def result: Coll = elems
}