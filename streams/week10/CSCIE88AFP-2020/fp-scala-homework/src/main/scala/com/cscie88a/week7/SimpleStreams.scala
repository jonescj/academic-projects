package com.cscie88a.week7

import cats.implicits._
import fs2.{INothing, Pure, Stream, io, text}

object SimpleStreams {

  // empty Stream
  val emptyIntStream: Stream[Pure, Int] = Stream.empty

  // from values
  val stream1 = Stream.emit(1)

  val stream2 = Stream(1)

  val stream3 = Stream(1,2,3,4,5)

  // from sequences
  val stream4 = Stream.emits(List(1))

  val stream5 = Stream.emits(List(1,2,3,4,5))

  val stream6 = Stream.emits(1 to 1000)


  // stream reification
  val list5 = stream5.toList

  val vec6 = stream6.toVector

  // sampling stream values
  val subset1 = stream5.take(2).toList

  val subset2 = stream5.drop(1).take(3).toList

  // creating infinite streams
  val constantStream = Stream.constant("a")

  val rep1 = Stream(1,2,3).repeat

  val wholeNumbers = Stream.iterate(1)(_ + 1)

  // stream combinators 1
  val seqStreams = Stream(1,2,3) ++ Stream(3,4)

  val tens = wholeNumbers.map(_ * 10)

  val byFives = wholeNumbers.filter(_ % 5 == 0)

  val sumStream = Stream.emits(1 to 100).fold(0)(_ + _).toList

  val onesAndTens = wholeNumbers.take(3) ++ wholeNumbers.map(_ * 10).take(3)

  // stream combinators 2
  val csvItems = Stream(1,2,3,4,5).map(_.toString).intersperse(",")

  val mixedItems = Stream(1,2,3,4,5).interleave(Stream(10,20,30,40,50))

  val charInts = Stream.emits('a' to 'e').zip(Stream.emits(1 to 5))

  val charInts2 =
    Stream.emits('a' to 'e')
      .zipWith(Stream.emits(1 to 5)) { (c, i) =>
        s"[${c}, ${i}]"
      }

  val charInts3 = for {
    c <- Stream.emits('a' to 'e')
    i <- Stream.emits(1 to 5)
  } yield s"[${c},${i}]"


  val fibs: Stream[Pure, Int] =
    Stream(0,1) ++ fibs.zipWith(fibs.tail)(_ + _)


}
