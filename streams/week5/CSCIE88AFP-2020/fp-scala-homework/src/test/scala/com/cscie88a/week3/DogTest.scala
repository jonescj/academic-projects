package com.cscie88a.week3

import org.scalatest.{ WordSpec, BeforeAndAfterAll, Matchers }

final class DogTest
    extends WordSpec
    with Matchers
    with BeforeAndAfterAll {

  "Dog case class" should {
    "create a Dog instance without using the new operator" in {
      Dog("fido", 5).name should be("fido")
    }
  }
}
