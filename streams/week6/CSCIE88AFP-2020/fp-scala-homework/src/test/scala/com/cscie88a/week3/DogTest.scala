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
    "have the correct age" in {
      Dog("hannah", 6).age should be(6)
    }
    "indicate that a puppy needs training" in {
      Dog("benji",1).needsTraining should be(true)
    }
    "indicate that an old dog does not need training" in {
      Dog("old yeller",14).needsTraining should be(false)
    }
    "create a dog from comma separated values" in {
      val dog1 = Dog("blackie,2")
      dog1.name should be("blackie")
      dog1.age should be (2)
    }
  }
  "DogAggregator" should {
    "calculate average with ones" in {
      DogAggregator(1,1).avg should be(1)
    }
    "calculate avg correctly for a single dog" in {
      DogAggregator(1, 12).avg should be(12)
    }
    "calculate avg correctly for 3 dogs" in {
      DogAggregator(3, 12).avg should be(4)
    }
    "add two dogs correctly" in {
      val twoDogs: DogAggregator = DogAggregator(1, 6) + DogAggregator(1, 8)
      twoDogs.count should be (2)
      twoDogs.total should be(14)
      twoDogs.avg should be (7)
    }
  }
}
