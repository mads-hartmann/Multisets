package com.sidewayscoding

class ElementIterator[A] private (val element: A, count: Int) extends Iterator[A] {

  var index = 0;

  def hasNext: Boolean = index < count

  def next(): A = {
    if (index < count) {
      index += 1
      element
    } else throw new NoSuchElementException("next on empty iterator")

  }
}

object ElementIterator {

  def apply[A](a: A, count: Int): ElementIterator[A] = {
    ElementIterator(a, count)
  }

}