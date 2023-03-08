package com.cscie88a.week3

// complete the code for problem 4 and 5 below
case class HarvardStudent(firstName:String,lastName:String,subjectName:String,percentScore:Int){

  def fullName: String =
    s"${firstName.toUpperCase()} ${lastName.toUpperCase()}"

  def failedSubject: Boolean =
    if(percentScore < 50) true else false

}

object HarvardStudent {
  def apply(csv: String): HarvardStudent = {
    val fields = csv.split(",")
    HarvardStudent(fields(0), fields(1), fields(2), fields(3).toInt)
  }
  def fromCSVStrings(csvList: List[String]):List[HarvardStudent] = {
    csvList.map(apply)
  }
  def avgStudentScore(harvardStudents: List[HarvardStudent]):Int = {
    harvardStudents.map(_.percentScore).reduce(_+_) / harvardStudents.length
  }
  def avgPassingScore(harvardStudents: List[HarvardStudent]): Int ={
    val hasPassed = (x:HarvardStudent) => !x.failedSubject
    val passingStudents = harvardStudents.filter(hasPassed)
    passingStudents.map(_.percentScore).reduce(_+_) / passingStudents.length
  }
}