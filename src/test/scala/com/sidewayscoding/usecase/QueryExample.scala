package com.sidewayscoding.usecase

import com.sidewayscoding.immutable.ListMultiset
import com.sidewayscoding.Multiset

object QueryExample {
  def main(args: Array[String]) {

    sealed abstract class WithId(val id: String) {
      override def equals(other: Any) = other match {
        case x: WithId => this.id == x.id
        case _ => false
      }
    }

    case class Person(cpr: String, name: String, age: Int) extends WithId(cpr)
    case class Book(title: String, rentedBy: Person) extends WithId(rentedBy.id)

    val mads = Person("001", "Mads Hartmann Jensen", 23)
    val eva = Person("002", "Eva Paus Regnar", 22)
    val mikkel = Person("003", "Mikkel Hartmann Jensen", 23)

    val rentedBooks: Multiset[WithId] = ListMultiset(
      Book("Lift in Action", mads),
      Book("Programming Scala", mads),
      Book("Beginning Scala", mads),
      Book("A Game of Thrones", mikkel)
    )

    // Find all the books rented by Mads Hartmann Jensen
    val booksMadsRented = rentedBooks intersect ListMultiset[WithId](mads)

    println(booksMadsRented)

  }
}