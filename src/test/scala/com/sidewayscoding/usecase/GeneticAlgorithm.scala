package com.sidewayscoding.usecase

import scala.util.Random
import scala.annotation.tailrec
import com.sidewayscoding.immutable.Multiset

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

  var population: Multiset[Solution] =
    Multiset((1 to initialPopulation).map(_ => randomSolution(citites)): _*)

  for (gen <- 1 until N) {
    val picks = pickThreeRandom(population)
    val best = picks.sortBy(_.fitness).take(2)
    val offspring = {
      val off = Solution(crossover(best.head, best.last), gen)
      if ((Random.nextInt(100) + 1) < 3) mutation(off) else off
    }

    // add the offspring
    population = population + offspring

    // remove the oldest solution from the generation, resolving ties by fitness
    val smallest = picks.map(_.generation).sorted.head
    val candidatesForRemoval = picks.filter(_.generation == smallest)
    if (candidatesForRemoval.size > 1) {
      population = {
        val toBeRemoved = candidatesForRemoval.sortBy( s => s.fitness).last
        removeFromSolutions(population, toBeRemoved)
      }
    } else {
      population = removeFromSolutions(population, candidatesForRemoval.head)
    }
  }

  // Printing the results in a pretty way

  val best = population.minBy(p => p.fitness)

  println("Did %s generations with an initial population of %s".format(N, initialPopulation))
  println("")
  println("The best solutions have a round-trip cost of: %s and can be acheived using".format(best.fitness))

  population.filter( s => s.fitness == best.fitness).foreach { s =>
    println("\t%s".format(s.seq.map( p => "(%s,%S)".format(p.x,p.y)).mkString(";")))
  }

  println("")

  population.withMultiplicity.toList.sortBy( tup => tup._1.head.fitness).foreach {
    case (elems, mult) => println("Fitness: %f\tcount: %s\tsolution:%s\tgenerations: %s".format(
        elems.head.fitness,
        mult,
        elems.head.seq.map( p => "(%s,%S)".format(p.x,p.y)).mkString(";"),
        elems.map(_.generation).mkString(",")
       ))
  }

  /**
   * Mutation is the generic name given to those variation operators that use
   * only one parent and create one child by applying some kind of randomized
   * change to the representation. In our case we're using Inversion Mutation.
   *
   * Inversion mutation works by randomly selecting two positions in the string
   * and reversing the order in which the values appear between those positions.
   */
  def mutation(s1: Solution): Solution = {

    val half = Math.ceil(s1.seq.size / 2).toInt
    val startIndex = Random.nextInt(half) // it's not inclusive so no reason to -1
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
   */
  def crossover(s1: Solution, s2: Solution): Seq[Point] = {

    // I simply pick some point in the left half of the list as the
    // first crossover-point and then select the second crossover-point
    // (size/2) elements to the right of that.

    val half = Math.ceil(s1.seq.size / 2).toInt
    val startIndex = Random.nextInt(half) // it's not inclusive so no reason to -1
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

  // Generate «x« unique random numbers with the maximum value defiend by `roof`
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

  // Because Multisets aren't indexed there isn't a nice way to pick three random
  // elements.
  def pickThreeRandom(options: Multiset[Solution]): Seq[Solution] = {
    val randoms = uniqueRandom(2, options.size-2)
    val (as, bs) = options.splitAt(randoms.head)
    val (xs, ys) = options.splitAt(randoms.last)
    List(as.headOption,
      bs.headOption,
      xs.headOption,
      ys.headOption).flatten.take(3)
  }

  // Remove one of many possible duplicates.
  def removeFromSolutions(solutions: Multiset[Solution], x: Solution): Multiset[Solution] = {
    val many = solutions.filter(_.hashCode == x.hashCode)
    solutions.filterNot(_.hashCode == x.hashCode) ++ many.tail
  }
}