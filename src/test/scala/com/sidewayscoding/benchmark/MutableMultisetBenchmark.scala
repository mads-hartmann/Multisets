package com.sidewayscoding.benchmark

import com.sidewayscoding.mutable.FullMultiset
import scala.collection.parallel.ForkJoinTaskSupport

// runbench com.sidewayscoding.benchmark.MutableMultisetBench 100
object MutableMultisetBenchmark extends testing.Benchmark {

  val length = 2000
  val ms = FullMultiset((0 until length): _*)

  def run = {
    ms.size
  }
}