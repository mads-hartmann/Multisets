package com.sidewayscoding.immutable

import scala.collection.mutable.Builder
import com.sidewayscoding.MultisetFactory
import com.sidewayscoding.CompactMultisetLike

abstract class ImmutableCompactMultisetFactory[CC[A] <: CompactMultiset[A] with CompactMultisetLike[A, CC[A]]]
       extends MultisetFactory[CC] {

  def apply[X: ClassManifest, A](elems: (A, Int)*): CC[A] = (newTupleBuilder[A] ++= elems).result

  private def newTupleBuilder[A]: Builder[(A, Int), CC[A]] = new CompactMultisetBuilder[A, CC[A]](empty)

  private class CompactMultisetBuilder[A, Coll <: CompactMultiset[A] with CompactMultisetLike[A, Coll]](empty: Coll)
        extends Builder[(A, Int), Coll] {

    protected var elems: Coll = empty

    def += (t: (A, Int)): this.type = {
      val (x, c) = t
      (0 until c).foreach { _ =>
        elems = elems + x
      }
      this
    }

    def clear() { elems = empty }
    def result: Coll = elems
  }
}