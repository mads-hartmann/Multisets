package com.sidewayscoding.usecase

import com.sidewayscoding.immutable.FullHashMultiset
import com.sidewayscoding.immutable.CompactHashMultiset

object Examples {

  def main(args: Array[String]) {

    val ms: FullHashMultiset[Int] = FullHashMultiset(1,2,3,4,5,4,3,2)

    val ms2: FullHashMultiset[String] = ms.map(_.toString)

    val l = CompactHashMultiset(1,2,3,4,5,6)

    println(l)
    println(ms)

    val l2: CompactHashMultiset[String] = l.map( _.toString )

    val l3 = CompactHashMultiset(("hey",10), ("you", 2))

    println(l3)

  }

}