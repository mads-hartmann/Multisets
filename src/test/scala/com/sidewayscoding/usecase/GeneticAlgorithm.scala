package com.sidewayscoding.usecase

import scala.util.Random
import scala.annotation.tailrec
import com.sidewayscoding.immutable.Multiset
import com.sidewayscoding.FullMultiset
import com.sidewayscoding.immutable.FullHashMultiset

/**
 * Finding a solution to TPS using a Genetic Algorithm.
 *
 * Representation: permutation of the indices of cities
 * Fitness:        Round Trip Cost
 * Selection:      3-tournament
 * crossover:      Order crossover
 * mutation:       Inversion Mutation
 */
object GeneticAlgorithm extends App {

  import Data._

  val N = 200
  val MutationProbability = 3
  val initialPopulation = 100

  val citites = List(
    Point(0,0),
    Point(0,1),
    Point(0,2),
    Point(1,2),
    Point(2,2),
    Point(2,1),
    Point(2,0),
    Point(1,0))

  var initialSolutions: List[Solution] =
    List((1 to initialPopulation).map(_ => randomSolution(citites)): _*)

  /*
   * Presort the initial population into buckets so it's faster to pick
   * solutions for the tournament.
   */

  val buckets = Array(
    FullMultiset[Solution](),
    FullMultiset[Solution](),
    FullMultiset[Solution]())

  val randoms = (1 to initialPopulation).map( _ => Random.nextInt(3) )

  initialSolutions.zip(randoms).foreach { case (p,i) =>
    buckets(i % 3) = buckets(i % 3) + p
  }

  val START = System.currentTimeMillis()

  /*
   * Proceed for N generations
   */

  for (gen <- 1 until N) {
    val picks = pickThreeRandom(buckets)
    val best = picks.sortBy(_._2.fitness).take(2)
    val offspring = {
      val off = Solution(crossover(best.head._2, best.last._2), gen)
      if ((Random.nextInt(100)) < MutationProbability) mutation(off) else off
    }

    // add the offspring and remove the oldest.
    buckets(gen % 3) = buckets(gen % 3) + offspring

    // remove the oldest solution from the generation, resolving ties by fitness
    val smallest = picks.sortBy( p => p._2.generation).head
    val candidatesForRemoval = picks.filter(_._2.generation == smallest._2.generation)
    val (bucketCount, solution) = if (candidatesForRemoval.size > 1) {
      candidatesForRemoval.sortBy( s => s._2.fitness).last
    } else candidatesForRemoval.head
    buckets(bucketCount) = buckets(bucketCount).removed(solution, x => x.seq == solution.seq && x.generation == solution.generation)
  }

  val END = System.currentTimeMillis()

  // Printing the results in a pretty way
  val allSolutions: FullMultiset[Solution] = buckets.fold(FullMultiset())(_ ++ _)
  val best = allSolutions.minBy(p => p.fitness)

  println("Did %s generations with an initial population of %s\n".format(N, initialPopulation))
  println("The best solutions have a round-trip cost of: %s and can be acheived using".format(best.fitness))

  allSolutions.filter( s => s.fitness == best.fitness).foreach { s =>
    println("\t%s".format(s.seq.map( p => "(%s,%S)".format(p.x,p.y)).mkString(";")))
  }

  println("")

  allSolutions.copies.toList.sortBy( tup => tup._1.fitness).foreach {
    case (elem, elems) => println("Fitness: %f\tcount: %s\tsolution:%s\tgenerations: %s".format(
        elems.head.fitness,
        elems.size,
        elems.head.seq.map( p => "(%s,%s)".format(p.x,p.y)).mkString(";"),
        elems.map(_.generation).mkString(",")
       ))
  }

  println("It took: " + (END - START) )

  /**
   * Mutation is the generic name given to those variation operators that use
   * only one parent and create one child by applying some kind of randomized
   * change to the representation. In our case we're using Inversion Mutation.
   *
   * Inversion mutation works by randomly selecting two positions in the string
   * and reversing the order in which the values appear between those positions.
   *
   * - Introduction to Evolutionary Computing
   */
  def mutation(s1: Solution): Solution = {

    val half = Math.ceil(s1.seq.size / 2).toInt
    val startIndex = Random.nextInt(half)
    val endIndex = (startIndex + half)

    val (before, segment, after) = {
      val (b, rest) = s1.seq.splitAt(startIndex)
      val (seg2, a) = rest.splitAt(endIndex - startIndex)
      (b, seg2, a)
    }

    def shuffle(p: List[Point], left: List[Point], right: List[Point]): List[Point] = p match {
      case Nil => left.reverse ::: right
      case x :: Nil => left.reverse ::: List(x) ::: right
      case x :: xs => {
        val y = xs.last
        val rest = xs.slice(0, xs.size - 1)
        shuffle(rest, y :: left, x :: right)
      }
    }

    s1.copy(seq = before.toList ::: shuffle(segment.toList, Nil, Nil) ::: after.toList)
  }

  /**
   * Crossover is also known as recombination. It's the process whereby a new
   * solution is created by information contained within two parent solutions.
   * In our case we use order-crossover which goes like this
   *
   * 1. Choose two crossover points at random, and copy the segment between
   *    them from the first parent (s1) into the first offspring.
   * 2. Starting from the second crossover point in the second parent, copy
   *    the remaining unused numbers into the first child in the order that
   *    they appear in the second parent, wrapping around at the end of the
   *    list
   *
   * - Introduction to Evolutionary Computing (modified a bit)
   */
  def crossover(s1: Solution, s2: Solution): Seq[Point] = {

    // I simply pick some point in the left half of the list as the
    // first crossover-point and then select the second crossover-point
    // (size/2) elements to the right of that.

    val half = Math.ceil(s1.seq.size / 2).toInt
    val startIndex = Random.nextInt(half)
    val endIndex = (startIndex + half)

    def createOffspring(parent1: Seq[Point], parent2: Seq[Point]) = {
      val segment = parent1.seq.slice(startIndex, endIndex)
      val numberOfElemsRight = parent2.seq.size - endIndex
      val rest = {
        val (b, a) = parent2.seq.splitAt(parent2.seq.size - numberOfElemsRight)
        (a.toList ::: b.toList).filterNot(segment.contains)
      }
      val (after, before) = rest.splitAt(numberOfElemsRight)
      before ::: segment.toList ::: after
    }

    createOffspring(s1.seq, s2.seq)
  }
}


/**
 * Data definitions and functions.
 */
object Data {

  case class Point(x: Int, y: Int)

  case class Area(width: Int, height: Int)

  case class Solution(seq: Seq[Point], generation: Int) {

    override def equals(o: Any) = o match {
      case x: Solution => x.seq == this.seq
      case _ => false
    }

    override def hashCode = this.seq.hashCode

    // Calculates the fitness of the solution. I.e. the total distance for the round-trip.
    def fitness: Double = {
      val start = seq.head
      val initial = (start, 0d)
      val (last, dist) = seq.tail.foldLeft(initial) {
        case ((prev, dist), next) =>
          (next, dist + distance(prev, next))
      }
      dist + distance(last, start)
    }
  }

  // Euclidean distance between two points.
  def distance(p1: Point, p2: Point) = {
    Math.sqrt(Math.pow(p2.x - p1.x, 2) + Math.pow(p2.y - p1.y, 2))
  }

  // Given a sequence of points to visit generate a random solution.
  def randomSolution(places: Seq[Point]): Solution = {
    @tailrec def rec(acc: List[Point], remaining: Seq[Point]): List[Point] = {
      if (remaining.isEmpty) {
        acc
      } else {
        val x = remaining(Random.nextInt(remaining.size))
        rec(x :: acc, remaining.filterNot(_ == x))
      }
    }
    Solution(rec(Nil, places), 0)
  }

  // Generate `x` unique random numbers with the maximum value defined by `roof`
  def uniqueRandom(x: Int, roof: Int): Seq[Int] = {
    assert(x < roof, "The number of unique numbers should be smaller than the max allowed value")
    @tailrec def rec(current: List[Int], remaining: Int): Seq[Int] = {
      if (remaining <= 0) {
        current
      } else {
        val x = Random.nextInt(roof)
        if (!current.contains(x)) {
          rec(x :: current, remaining - 1)
        } else {
          rec(current, remaining)
        }
      }
    }
    rec(Nil, x)
  }

  def pickThreeRandom(buckets: Array[FullMultiset[Solution]]): Seq[(Int, Solution)] = {
    for ( i <- 0 until 3) yield {
      (i, buckets(i).head)
    }
  }
}