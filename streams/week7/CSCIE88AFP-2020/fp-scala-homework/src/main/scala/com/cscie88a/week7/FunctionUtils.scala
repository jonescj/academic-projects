package com.cscie88a.week7

object FunctionUtils {

  var globalCounter: Int = 5

  def increment(i: Int): Int = {
    globalCounter += i
    globalCounter
  }


  def incrementCounter(i: Int): Unit =
    globalCounter += i


  def myLogger(message: String): Unit =
    println(s"[${message}]")


  def myLogger2(message: String): () => Unit =
    () => println(s"[${message}]")

}
