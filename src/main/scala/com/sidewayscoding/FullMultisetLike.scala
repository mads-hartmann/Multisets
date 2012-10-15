package com.sidewayscoding

trait FullMultisetLike[A, +This <: FullMultiset[A] with FullMultisetLike[A, This]] extends MultisetLike[A, This] {
  def copies: Map[A, Seq[A]]
  def get(a: A): Seq[A]
}