package com.sidewayscoding

import scala.collection.SeqLike
import scala.collection.mutable.Builder
import scala.collection.mutable.ArrayBuffer
import scala.collection.generic.CanBuildFrom

class ElementSeq[A] private (element: Option[A], count: Int) extends Seq[A] with SeqLike[A, ElementSeq[A]] {

  import ElementSeq._

  def apply(idx: Int): A = if (idx > 0 && idx < count) element.get
                           else throw new IndexOutOfBoundsException

  def length = count

  def iterator: Iterator[A] = new ElementIterator[A](element, count)

  override def newBuilder: Builder[A, ElementSeq[A]] = {
    new ArrayBuffer[A] mapResult fromSeq
  }
}

object ElementSeq {

  def apply[A]() = new ElementSeq[A](None, 0)

  def apply[A](elem: A, count: Int) = new ElementSeq(Some(elem), count)

  def fromSeq[A](buf: Seq[A]): ElementSeq[A] = {
    new ElementSeq(buf.headOption, buf.size) 
  }

  implicit def canBuildFrom[A]: CanBuildFrom[ElementSeq[_], A, ElementSeq[A]] = 
    new CanBuildFrom[ElementSeq[_], A, ElementSeq[A]] {
      def apply(): Builder[A, ElementSeq[A]] = new ArrayBuffer[A] mapResult fromSeq
      def apply(from: ElementSeq[_]): Builder[A, ElementSeq[A]] = new ArrayBuffer[A] mapResult fromSeq
    }

  private class ElementIterator[A] (element: Option[A], count: Int) extends Iterator[A] {

    var index = 0;

    def hasNext: Boolean = !element.isEmpty && index < count

    def next(): A = {
      println("wtf!")
      if (index <= count) {
        index += 1
        element.get
      } else throw new NoSuchElementException("next on empty iterator (lol)")

    }
  }
}

