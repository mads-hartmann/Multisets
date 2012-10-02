package com.sidewayscoding.usecase

import com.sidewayscoding.immutable.ListMultiset
import com.sidewayscoding.Multiset

object QueryExample {
  def main(args: Array[String]) {

    case class Person(cpr: String, name: String, age: Int)
    case class Book(title: String, rentedByCpr: String)

    val mads = Person("001", "Mads Hartmann Jensen", 23)
    val eva = Person("002", "Eva Paus Regnar", 22)
    val mikkel = Person("003", "Mikkel Hartmann Jensen", 23)

    val people = Multiset(mads, eva, mikkel)
    
    val rentedBooks = Multiset(
      Book("Lift in Action", mads.cpr),
      Book("Programming Scala", mads.cpr),
      Book("Beginning Scala", mads.cpr),
      Book("A Game of Thrones", mikkel.cpr)
    )

    // SELECT Book.name
    // FROM Book, Person
    // WHERE Person.CPR = Book.rentedBy

    // Cartesian product
    def x[A,B](m1: Multiset[A], m2: Multiset[B]): Multiset[(A,B)] = for { 
      a <- m1
      b <- m2
    } yield (a,b)

    val rslt: Multiset[String] = 
      x(rentedBooks,people).filter { case (b,p) => b.rentedByCpr == p.cpr }.map( _._2.name )

  }
}