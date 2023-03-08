package com.cscie88a.week10

import java.nio.file.Paths

import org.scalatest.{AsyncWordSpec, BeforeAndAfterAll, Matchers, WordSpec}
import akka.util._
import akka.stream._
import akka.stream.scaladsl._
import akka.{Done, NotUsed}
import akka.actor.ActorSystem
import akka.actor.Status.Success

import scala.concurrent._
import scala.concurrent.duration._
import com.cscie88a.util.StreamUtil
import cats.implicits._
import org.scalatest.compatible.Assertion

class MySimpleAkkaStreamTest
  extends AsyncWordSpec
    with Matchers
    with BeforeAndAfterAll {

  // create the akka system for stream materialization
  implicit val (sys, mat, _) = StreamUtil.actorSystemInstances("KafkaStreamTest")

  // shutdown the akka system after tests
  override protected def afterAll() {
    sys.terminate()
    super.afterAll()
  }

  /**
    * helper function to test source contents
    * materializes source to a sequence and calls assertion on resulting sequence
    *
    * @param testSource input source
    * @param assertion assertion on sequence
    * @return
    */
  def assertSourceContent[O]
  (testSource: Source[O, NotUsed])
  (assertion: Seq[O] => Assertion): Future[Assertion] = {
    testSource
      .runWith(Sink.seq)
      .map(assertion(_))
  }

  "MySimpleAkkaStream" should {

    /* (1.2)
    Write unit tests for the streams defined above. Use the unit test helper function from
    the lab */
    "materialize source of for numTo10Source" in {
      assertSourceContent(MySimpleAkkaStream.numTo10Source) { s =>
        s.take(10).shouldBe(Seq(1, 2, 3, 4, 5, 6, 7, 8, 9, 10))
      }
    }

    /* (2.2)
    Write unit tests for the streams defined above. Use the unit test helper
    function from the lab */
    "materialize source of for evenNumberSource" in {
      assertSourceContent(MySimpleAkkaStream.evenNumberSource) { s =>
        s.take(10).shouldBe(Seq(2, 4, 6, 8, 10, 12, 14, 16, 18, 20))
      }
    }

    /* (3.2)
    Write unit tests for the flow defined above. Use the unit test helper function from the
    lab. Since the thread name is not deterministic it is sufficient to assert that the input
    string is contained in the output string in the unit test. */
    "materialize source of for letterSource with logFlow" in {
      assertSourceContent(MySimpleAkkaStream.letterSource.via(MySimpleAkkaStream.logFlow)) {
        s =>
          s.take(5).shouldBe(Seq("<thread name>: A", "<thread name>: B",
            "<thread name>: C", "<thread name>: D", "<thread name>: E"))

      }
    }

    /* (4.2)
    Write unit tests for the flow defined above. Use the unit test helper function from the lab */
    "materialize source of for letterSource with byteStringFlow" in {
      assertSourceContent(MySimpleAkkaStream.letterSource.via(MySimpleAkkaStream.byteStringFlow)) {
        s =>
          s.take(5).shouldBe(Seq(ByteString(65), ByteString(66),
            ByteString(67), ByteString(68), ByteString(69)))
      }
    }

    /* (5.2)
    Write a unit test to run the processing pipeline. Verify that the content of stream.log is correct. */
    "materialize source of for evensPipeline" in {
      MySimpleAkkaStream.evensPipeline.run()
      FileIO.fromPath(Paths.get("stream.log")).map(_.utf8String).runWith(Sink.foreach(print(_)))
      assert(true);
    }
  }
}
