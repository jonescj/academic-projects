package com.cscie88a.week6

object FunctionUtils {

  def add2a(x: Int): Int = x + 2

  val add2b: Int => Int = x => x + 2

  val add2c: Int => Int = _ + 2

  def logUsage_a: Unit = println(s"calling method")

  val logUsage_b: Unit = println(s"calling function")

  val add3a: Int => Int = x => x + 3

  val add4a: Int => Int = x => x + 4

  val addN: Int => Int => Int = n => x => x + n

  val add3b: Int => Int = addN(3)

  val add4b: Int => Int = addN(4)

  val generateXY: (Int => Int) => List[(Int, Int)] = { f =>
    List(0,10,20,30,40,50)
      .map(x => (x, f(x)))
  }

  def generateAB(f: Int => Int): List[(Int, Int)] = {
    List(0,10,20,30,40,50)
      .map(x => (x, f(x)))
  }

  val add7: Int => Int = add3b andThen add4b

  def initializeWith[A](initVal: A, n: Int): List[A] =
    List.fill(n)(initVal)


}
