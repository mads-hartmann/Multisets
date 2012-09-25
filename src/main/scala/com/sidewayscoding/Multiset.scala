package com.sidewayscoding

trait Multiset[A] extends Iterable[A] {

  override def seq: this.type = this
  
}