package com.sidewayscoding

/**
 * A Compact Multiset is a Multiset that will only store one of each equal element and use a
 * number to keep track of the multiplicity of each element. This is useful to safe memory if 
 * the equals method is defined over all of the properties of the elements (as is the default 
 * for case classes) 
 */
trait CompactMultisetLike[A, +This <: CompactMultiset[A] with CompactMultisetLike[A, This]] extends MultisetLike[A, This] {

}