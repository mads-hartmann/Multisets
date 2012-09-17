package com.sidewayscoding

/**
 * This type-class indicates that it's acceptable to 
 * merge two instances if they're equal. 
 * 
 * This is needed so we can throw away instances in a
 * Multiset if they're equal and just keep a counter 
 * instead to save memory usage.   
 */
trait Mergeable[A] {
  
}