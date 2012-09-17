package com.sidewayscoding.immutable

import com.sidewayscoding.{ MultisetLike }
import scala.collection.mutable.Builder

abstract class ImmutableMultisetFactory[CC[X] <: Multiset[X] with MultisetLike[X, CC[X]]] {
 
	// TODO: Add the code that MergeableListMultiset and ListMultiset have in common here. See the 
	// 		 how the Scala team has done it. We might have a problem because we want to enfore a 
	//       constraint (again, see how TreeMap enforces Ordering)   
}