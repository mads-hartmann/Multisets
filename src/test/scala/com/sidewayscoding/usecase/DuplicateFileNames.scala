package com.sidewayscoding.usecase

import com.sidewayscoding.immutable.Multiset

object DuplicateFileNames {
  def main(args: Array[String]) {
    // Our custom representation of a filename.
    case class FileName(name: String, path: List[String]) {
      override def equals(o: Any) = {
        o match {
          case x: FileName => x.name == name
          case _           => false
        }
      }
      override def hashCode: Int = name.hashCode
    }

    val fakeDirPath = List("~", "fake", "dir")
    val tmpDirPath = List("~", "tmp", "dir")

    val folderOne = Multiset(
      FileName("log1.txt", fakeDirPath),
      FileName("log2.txt", fakeDirPath),
      FileName("log3.txt", fakeDirPath),
      FileName("log4.txt", fakeDirPath))

    val folderTwo = Multiset(
      FileName("log1.txt", tmpDirPath),
      FileName("cat-image1.jpg", tmpDirPath),
      FileName("cat-image2.jpg", tmpDirPath),
      FileName("cat-image3.jpg", tmpDirPath),
      FileName("cat-image4.jpg", tmpDirPath))

    val duplicates = folderOne intersect folderTwo

    println(duplicates.size)
    println(duplicates)
  }
}