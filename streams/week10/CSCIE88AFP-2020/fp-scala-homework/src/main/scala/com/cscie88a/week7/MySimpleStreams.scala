package com.cscie88a.week7

import cats.implicits._
import fs2.{INothing, Pure, Stream, io, text}

object MySimpleStreams {

  // Problem 1
  val numTo10 = ???

  val numFrom10To20 = ???

  val numTo20 = ???


  // Problem 2
  val evens = ???

  val odds = ???

  val naturalNumbers = ???

  // Problem 3
  def multN(n: Int): Stream[Pure, Int] = ???

  val mult3 = ???

  val mult4 = ???

  val mult34Pairs = ???

  val mult12 = ???

  // Problem 4
  def pythTest(t: Tuple3[Int, Int, Int]): Boolean = ???

  val upto100: Stream[Pure, Int] = ???

  val pythTriples: Stream[Pure, (Int, Int, Int)] = ???

}
