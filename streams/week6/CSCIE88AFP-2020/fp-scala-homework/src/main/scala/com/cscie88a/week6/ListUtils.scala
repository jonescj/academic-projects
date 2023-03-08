package com.cscie88a.week6

case class UserPreferences(
  id: String,
  name: String,
  color: String,
  rating: String)

object UserPreferences {
  val userPrefsMap: Map[String, UserPreferences] = Map(
    "1" -> UserPreferences("1", "Joe Small", "BLUE", "B"),
    "2" -> UserPreferences("2", "Mike Rez", "RED", "A"),
    "3" -> UserPreferences("3", "John Dall", "GREEN", "C"),
    "4" -> UserPreferences("4", "Mary Kate", "BLUE", "B"),
    "5" -> UserPreferences("5", "Chloe Mazie", "BLUE", "D"),
    "6" -> UserPreferences("6", "Jane Trist", "GREEN", "A")
  )

  // user at id 4, user at id 10
  // map filter
  def colorFans(color: String): Map[String, UserPreferences] =
    userPrefsMap.filter(t => t._2.color == color)

  def keyByName: Map[String, UserPreferences] =
    userPrefsMap.map(t => (t._2.name, t._2))

}

object ListUtils {

  def add2: Int => Int = _ + 2

  def uptoN: Int => List[Int] = n => (0 to n).toList

  def allPairs_a: Seq[(Int, Int)] =
    (1 to 5).flatMap { a =>
      (10 to 50 by 10).map {b =>
        (a,b)
      }
    }

  def allPairs_b: Seq[(Int, Int)] =
    for {
      a <- (1 to 5)
      b <- (10 to 50 by 10)
    } yield (a,b)


  def rightTriangleTriples: Seq[(Int, Int, Int)] =
    for {
      a <- 1 to 10
      b <- 1 to 10
      c <- 1 to 10
      if (a * a + b * b == c * c)
    } yield (a,b,c)


  val ratingPoints: Map[String, Int] = Map(
    "A" -> 90,
    "B" -> 80,
    "C" -> 70,
    "D" -> 60
  )

  def add10ToMapValues(inMap: Map[String, Int]): Map[String, Int] =
    inMap.transform((_, v) => v + 10)

  // scan
  def fibN(n: Int): List[Int] = {
    (1 to n)
      .toList
      .scanLeft((0,1))((t, i) => (t._2, t._1+t._2))
      .map(_._2)
  }

  // flatMap for iterating over lists
  def allXYPairs_a: List[(Int, Int)] =
    for {
      x <- (1 to 5).toList
      y <- (1 to 5).toList
    } yield (x,y)

  def allXYPairs_b: List[(Int, Int)] =
    (1 to 5).toList.flatMap { x =>
      (1 to 5).toList.map { y =>
        (x,y)
      }
    }

  // flatMap for nested sequences
  def lettersInSentence(sentence: String): List[String] =
    for {
      word <- sentence.split(" ").toList.filter(_ != "")
      letter <- word.split("").toList
    } yield letter.toLowerCase()

  // tuples

}
