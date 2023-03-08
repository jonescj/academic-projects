package com.cscie88a.week6

import org.scalatest.{ WordSpec, BeforeAndAfterAll, Matchers }

class ListUtilsTest
    extends WordSpec
    with Matchers
    with BeforeAndAfterAll {

  "add10ToMapValues" should {
    "add to simple map" in {
      ListUtils.add10ToMapValues(Map("A" -> 1, "B" -> 2)) should be(Map("A" -> 11, "B" -> 12))
    }
  }

}
