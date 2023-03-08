/**
  * scala cats law testing for simple app
  */

package com.cscie88a

import cats._
import cats.implicits._
import org.scalatest.{ WordSpec, BeforeAndAfterAll, Matchers }

// no cats checking; simple unit test example instead
final class SimpleCatsTypeclassTest
    extends WordSpec
    with Matchers
    with BeforeAndAfterAll {

  "Monoid for List" should {
    "sum integers with combineAll" in {
      Monoid.combineAll(List(1, 2, 3)) shouldBe 6
    }
  }
}
