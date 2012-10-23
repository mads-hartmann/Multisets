package com.sidewayscoding.mutable

import com.sidewayscoding.MultisetFactory

abstract class MutableMultisetFactory[CC[X] <: com.sidewayscoding.mutable.Multiset[X] with MultisetLike[X, CC[X]]] 
         extends MultisetFactory[CC]