package com.sidewayscoding.mutable

trait CompactMultisetLike[A, +This <: CompactMultiset[A] with CompactMultisetLike[A, This]]
  extends MultisetLike[A, This]
     with com.sidewayscoding.CompactMultisetLike[A, This]