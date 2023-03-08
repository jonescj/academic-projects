package com.cscie88a.week7

import org.scalatest.{ WordSpec, BeforeAndAfterAll, Matchers }
import cats.effect.{Blocker, ExitCode, IO, IOApp, Resource}
import cats.implicits._
import fs2.{io, text, Stream}

class SimpleStreamTest
  extends WordSpec
  with Matchers
  with BeforeAndAfterAll {
  "SimpleStream" should {

    // example of stream unit test
    "generate stream of constant values when using constant operator" in {
      Stream.constant(5).take(4).toList.forall(_ == 5) should be(true)
    }
  }
}
