package com.cscie88a.week3

import org.scalatest.{ WordSpec, BeforeAndAfterAll, Matchers }

final class MyStudentTest
  extends WordSpec
  with Matchers
  with BeforeAndAfterAll {

  // add unit tests for problem 3 here
  "MyStudent object" should {
    "return the value \"Hello JOHN DOE\" on MyStudent.greet" in {
      MyStudent.greet should be("Hello JOHN DOE")
    }
  }
}
