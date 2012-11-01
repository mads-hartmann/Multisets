package com.sidewayscoding.benchmark

import com.sidewayscoding.mutable.FullMultiset
import scala.collection.parallel.ForkJoinTaskSupport

// runbench com.sidewayscoding.benchmark.MutableMultisetBench 100
object MutableMultisetBench extends testing.Benchmark {

  val length = 300
  val partrie = FullMultiset((0 until length) zip (0 until length): _*)

  def run = {
    partrie map {
      kv => kv
    }
  }
}