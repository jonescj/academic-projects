package com.cscie88a.week7

import cats.implicits._
import fs2.{INothing, Pure, Stream, io, text}
import cats.effect.IO
import scala.concurrent.ExecutionContext
import scala.concurrent.duration._
import scala.util.Random

object EffectfulStreams {

  implicit val timer = IO.timer(ExecutionContext.global)

  // synchronous effects
  // describe the effect
  def puts = IO(println("hello"))

  val p1 = puts

  // run the effect
  p1.unsafeRunSync


  def putsNumber = IO {
    println("running IO effect ...")
    Random.nextInt
  }

  // describe effectful stream
  val effStr1 = Stream.eval(puts)

  val compiledEff1 = effStr1.compile.toList

  // run stream reified
  val result1 = compiledEff1.unsafeRunSync

  // can now use combinators since effectful streams are pure
  val effStr2 = effStr1.repeat

  val compiledEff2 = effStr2.take(5).compile.toList

  val result2 = compiledEff2.unsafeRunSync

  // example
  def putNum(i: Int) = IO(println(i))

  val printRange = Stream.emits(1 to 10).evalMap(putNum)

  val rate = Stream.awakeEvery[IO](2 seconds)

  val printWithRate = rate.zip(printRange)

  printWithRate.compile.drain.unsafeRunSync

}
