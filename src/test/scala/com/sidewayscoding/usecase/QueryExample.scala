package com.sidewayscoding.usecase

import com.sidewayscoding.immutable.ListMultiset
import com.sidewayscoding.immutable.Multiset
/**
 * TODO: Currently I don't see any advantage of Multiset here. You might as well use any other 
 * Iterable data structure.
 */
object QueryExample {
  def main(args: Array[String]) {

    case class Person(cpr: String, name: String, age: Int)
    case class Book(title: String, rentedByCpr: String)

    val mads   = Person("001", "Mads Hartmann Jensen", 23)
    val eva    = Person("002", "Eva Paus Regnar", 22)
    val mikkel = Person("003", "Mikkel Hartmann Jensen", 23)

    val people = Multiset(mads, eva, mikkel)

    val rentedBooks = Multiset(
      Book("Lift in Action", mads.cpr),
      Book("Programming Scala", mads.cpr),
      Book("Beginning Scala", mads.cpr),
      Book("A Game of Thrones", mikkel.cpr)
    )

    // SELECT Book.name, Person.Name
    // FROM Book, Person
    // WHERE Person.CPR = Book.rentedBy

    val rslt1: Multiset[(String, String)] = for {
      book   <- rentedBooks
      person <- people
      if book.rentedByCpr == person.cpr
    } yield (book.title, person.name)


  }
}