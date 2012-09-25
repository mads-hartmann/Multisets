package com.sidewayscoding.immutable

import com.sidewayscoding.MergeableMultiset
import com.sidewayscoding.MergeableMultisetLike
import scala.collection.mutable.Builder
import scala.collection.generic.CanBuildFrom
import com.sidewayscoding.MultisetBuilder

abstract class ImmutableMergeableMultisetFactory[CC[A] <: MergeableMultiset[A] with MergeableMultisetLike[A, CC[A]]]
       extends ImmutableMultisetFactory[CC] {

  def apply[X: ClassManifest, A](elems: (A, Int)*): CC[A] = (newTupleBuilder[A] ++= elems).result

  private def newTupleBuilder[A]: Builder[(A, Int), CC[A]] = new MergeabeMultisetBuilder[A, CC[A]](empty)

  private class MergeabeMultisetBuilder[A, Coll <: MergeableMultiset[A] with MergeableMultisetLike[A, Coll]](empty: Coll)
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