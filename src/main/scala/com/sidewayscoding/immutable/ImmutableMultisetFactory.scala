package com.sidewayscoding.immutable

import com.sidewayscoding.MultisetFactory
import com.sidewayscoding.MultisetLike

abstract class ImmutableMultisetFactory[CC[X] <: com.sidewayscoding.immutable.Multiset[X] with MultisetLike[X, CC[X]]] 
       extends MultisetFactory[CC]