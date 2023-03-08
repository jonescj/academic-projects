package com.cscie88a.week7

import org.scalatest.{ WordSpec, BeforeAndAfterAll, Matchers }

class FunctionUtilsTest
    extends WordSpec
    with Matchers
    with BeforeAndAfterAll {

  "FunctionUtils" should {

    "return same value for function applied twice" in {
      val v1 = FunctionUtils.increment(4)
      val v2 = FunctionUtils.increment(4)
      v1 should be(v2)
    }

    "return same value for function applied twice with a function" in {
      val v1 = FunctionUtils.myLogger2("hello")
      val v2 = FunctionUtils.myLogger2("hello")
      v1() should be(v2())
    }

  }
}
