package com.cscie88a.week7

import cats.implicits._
import fs2.{INothing, Pure, Stream, io, text}

object MySimpleStreams {

  // Problem 1
  val numTo10 = Stream.emits(1 to 10)

  val numFrom10To20 = Stream.emits(10 to 20)

  val numTo20 = numTo10 ++ numFrom10To20


  // Problem 2
  val evens = Stream.iterate(0)(_ + 2)

  val odds = Stream.iterate(1)(_ + 2)

  val naturalNumbers = evens.interleave(odds)

  // Problem 3
  def multN(n: Int): Stream[Pure, Int] = (Stream.iterate(1)(_ + 1)).map(_ * n);

  val mult3 = multN(3)

  val mult4 = multN(4)

  val mult34Pairs = mult3.zipWith(mult4) { (m3, m4) => s"[${m3},${m4}]"}

  val mult12 = mult3.interleave(mult4).filter(_ % 12 == 0)

  // Problem 4
  def pythTest(t: Tuple3[Int, Int, Int]): Boolean = (math.pow(t._1,2) + math.pow(t._2,2) == math.pow(t._3,2))

  val upto100: Stream[Pure, Int] = Stream.emits(1 to 100)

  val pythTriples: Stream[Pure, (Int, Int, Int)] = {
    val fullStream: Stream[Pure, (Int,Int,Int)] = for {
      a <- upto100
      b <- upto100
      c <- upto100
    } yield (a,b,c)
    val filteredStream = fullStream.filter(pythTest(_));
    filteredStream
  }

}
