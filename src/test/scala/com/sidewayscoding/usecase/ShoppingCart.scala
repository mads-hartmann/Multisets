package com.sidewayscoding.usecase

import com.sidewayscoding.immutable.CompactHashMultiset

object ShoppingCart {

  /*
   * Data has been taken from http://www.freebase.com/view/book/book_edition
   */

  case class Book(name: String,
                  creditedTo: Option[String],
                  publisher: Option[String],
                  publicationYear: Option[Int],
                  binding: Option[String])

  def main(args: Array[String]) {

    var shoppingCart = CompactHashMultiset[Book](
       Book("Brave New Words: The Oxford Dictionary of Science Fiction", Some("Jeff Prucher"), Some("Oxford University Press"), Some(2007), Some("Hardcover"))
      ,Book("On Lisp", None, Some("Prentice Hall"), Some(1993), Some("Paperback"))
      ,Book("The Logic of Failure: Why Things Go Wrong and What We Can Do to Make Them Right", None, Some("Perseus Books Group"), Some(1996), Some("Paperback"))
      ,Book("The Immaculate Conception", None, Some("House of Anansi Press"), Some(2006), None)
      ,Book("Bluebeard", Some("Kurt Vonnegut, Jr."), Some("Delacorte Press"), Some(1987), Some("Hardcover"))
      ,Book("On Lisp", None, Some("Prentice Hall"), Some(1993), Some("Paperback"))
    )

    println("Shopping cart contents")
    println((shoppingCart.multiplicities.map { case (book, count) =>
      "item %s, count: %s".format(book.name, count)
    }).mkString("\n"))

  }

}