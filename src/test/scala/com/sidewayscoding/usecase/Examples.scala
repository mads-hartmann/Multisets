package com.sidewayscoding.usecase

import com.sidewayscoding.immutable.ListMultiset
import com.sidewayscoding.immutable.MergeableListMultiset

object Examples {

  def main(args: Array[String]) {

    val ms: ListMultiset[Int] = ListMultiset(1,2,3,4,5,4,3,2)

    val ms2: ListMultiset[String] = ms.map(_.toString)

    val l = MergeableListMultiset(1,2,3,4,5,6)

    println(l)
    println(ms)

    val l2: MergeableListMultiset[String] = l.map( _.toString )

    val l3 = MergeableListMultiset(("hey",10), ("you", 2))

    println(l3)

  }

}