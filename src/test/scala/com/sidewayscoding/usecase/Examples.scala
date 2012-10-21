package com.sidewayscoding.usecase

import com.sidewayscoding.immutable.FullListMultiset
import com.sidewayscoding.immutable.CompactListMultiset

object Examples {

  def main(args: Array[String]) {

    val ms: FullListMultiset[Int] = FullListMultiset(1,2,3,4,5,4,3,2)

    val ms2: FullListMultiset[String] = ms.map(_.toString)

    val l = CompactListMultiset(1,2,3,4,5,6)

    println(l)
    println(ms)

    val l2: CompactListMultiset[String] = l.map( _.toString )

    val l3 = CompactListMultiset(("hey",10), ("you", 2))

    println(l3)

  }

}