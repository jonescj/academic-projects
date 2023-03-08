package com.cscie88a.week10

import akka.stream._
import akka.stream.scaladsl._
import akka.{ Done, NotUsed }
import akka.util.ByteString
import akka.actor.ActorSystem
import scala.concurrent._
import scala.concurrent.duration._
import com.cscie88a.util.StreamUtil
import cats.implicits._
import java.nio.file.Paths

object MySimpleAkkaStream {

  import StreamUtil.defaultActorSystem._

  val letterSource: Source[String, NotUsed] = Source(65 to 90).map(_.asInstanceOf[Char].toString)
  val evenNumberSource100 = Source.fromIterator(() => Stream.iterate(2)(_+2).iterator.take(100))
  val intLogFlow: Flow[Int, String, NotUsed] = Flow[Int].map("<thread number>: "+_+"\n")
  val fileSink = FileIO.toPath(Paths.get("stream.log"));

  /* (1.1)
  In an object MySimpleAkkaStream, define a value numTo10Source
  that is a Source of numbers from 1 to 10 */
  val numTo10Source: Source[Int, NotUsed] = Source(1 to 10)

  /* (2.1)
  In an object MySimpleAkkaStream, define a value evenNumberSource
  that is a Source of even numbers. Use Stream.iterate from earlier
  lectures and Source.fromIterator to create the Source */
  val evenNumberSource: Source[Int, NotUsed] =
    Source.fromIterator(() => Stream.iterate(2)(_+2).iterator.take(10))

  /* (3.1)
  In an object MySimpleAkkaStream, define a value logFlow that will take
  a String element as input and output a new string that has the processing
  thread name prefixed to the input stream. */
  val logFlow: Flow[String, String, NotUsed] = Flow[String].map("<thread name>: "+_)

  /* (4.1)
  In an object MySimpleAkkaStream, define a value byteStringFlow that will
  take a String Source as input and emit a Source that is the ByteString
  representation of the string */
  val byteStringFlow: Flow[String, ByteString, NotUsed] = Flow[String].map(ByteString(_))

  /* (5.1)
  In an object MySimpleAkkaStream, define a value evensPipeline that will
  create a processing pipeline for logging the first 100 even numbers to
  a file stream.log. Use the Source, Flow defined in earlier problems and
  a FileIO sink to create the pipeline. */
  val evensPipeline: RunnableGraph[NotUsed] = evenNumberSource100.via(intLogFlow).map(i => ByteString(s"$i")).to(fileSink)
}
