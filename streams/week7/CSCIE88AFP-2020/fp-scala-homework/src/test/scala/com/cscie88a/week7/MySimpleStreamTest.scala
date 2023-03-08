package com.cscie88a.week7

import org.scalatest.{BeforeAndAfterAll, Matchers, WordSpec}

class MySimpleStreamTest
  extends WordSpec
    with Matchers
    with BeforeAndAfterAll {
  "MySimpleStream" should {

    // example of stream unit test
    "1.1" in {
      MySimpleStreams.numTo10.toList should be(List(1,2,3,4,5,6,7,8,9,10))
    }
    "1.2" in {
      MySimpleStreams.numFrom10To20.toList should be(List(10,11,12,13,14,15,16,17,18,19,20))
    }
    "1.3" in {
      MySimpleStreams.numTo20.toList should be(List(1,2,3,4,5,6,7,8,9,10,10,11,12,13,14,15,16,17,18,19,20))
    }

    "2.1" in {
      MySimpleStreams.evens.take(6).toList should be(List(0,2,4,6,8,10))
    }
    "2.2" in {
      MySimpleStreams.odds.take(6).toList should be(List(1,3,5,7,9,11))
    }
    "2.3" in {
      MySimpleStreams.naturalNumbers.take(6).toList should be(List(0,1,2,3,4,5))
    }

    "3.1" in {
      MySimpleStreams.multN(7).take(5).toList should be(List(7,14,21,28,35));
    }
    "3.2" in {
      MySimpleStreams.mult3.take(5).toList should be(List(3,6,9,12,15));
      MySimpleStreams.mult4.take(5).toList should be(List(4,8,12,16,20));
    }
    "3.3" in {
      MySimpleStreams.mult34Pairs.take(5).toList should be(List("[3,4]","[6,8]","[9,12]","[12,16]","[15,20]"));
    }
    "3.4" in {
      MySimpleStreams.mult12.take(10).toList should be (List(12, 12, 24, 24, 36, 36, 48, 60, 48, 72))
    }

   "4.1" in {
      MySimpleStreams.pythTest((1,2,3)) should be (false)
      MySimpleStreams.pythTest((3,4,5)) should be (true)
    }
    "4.2" in {
      MySimpleStreams.upto100.toList.drop(93).toList should be (List(94,95,96,97,98,99,100))
      MySimpleStreams.upto100.toList.take(10).toList should be (List(1,2,3,4,5,6,7,8,9,10))
    }
    "4.3" in {
      MySimpleStreams.pythTriples.take(10).toList should be (List((3,4,5), (4,3,5), (5,12,13), (6,8,10), (7,24,25), (8,6,10), (8,15,17), (9,12,15), (9,40,41), (10,24,26)))
    }
  }
}

