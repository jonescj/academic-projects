package com.cscie88a.week6

case class Subject(id:Int,Name:String)

object Subject {
  val allSubjects:Map[Int,Subject] = Map(
    1 -> Subject(1,"Physics"),
    2 -> Subject(2,"Chemistry"),
    3 -> Subject(3,"Math"),
    4 -> Subject(4,"English")
  )
}