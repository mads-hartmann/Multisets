package com.sidewayscoding.immutable

import com.sidewayscoding.MergeableMultiset
import com.sidewayscoding.MergeableMultisetLike

abstract class ImmutableMergeableMultisetFactory[CC[X] <: MergeableMultiset[X] with MergeableMultisetLike[X, CC[X]]] {

}