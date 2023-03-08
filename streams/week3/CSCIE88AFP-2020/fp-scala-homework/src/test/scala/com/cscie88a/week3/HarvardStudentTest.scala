package com.cscie88a.week3

import org.scalatest.{BeforeAndAfterAll, Matchers, WordSpec}

class HarvardStudentTest
  extends WordSpec
    with Matchers
    with BeforeAndAfterAll {


  "HarvardStudent case class" should {
    // add unit tests for problem 4 below
    "return proper outputs for the HarvardStudent(\"John\",\"Doe\",\"Computer Science\",??) object" in {
      HarvardStudent("John", "Doe", "Computer Science", 10).fullName should be("JOHN DOE")
      HarvardStudent("John", "Doe", "Computer Science", 10).failedSubject should be(true)
      HarvardStudent("John", "Doe", "Computer Science", 90).fullName should be("JOHN DOE")
      HarvardStudent("John", "Doe", "Computer Science", 90).failedSubject should be(false)
    }
  }

  "HarvardStudent list operations" should {
    // add unit tests for problem 5 below
    "return proper outputs for the HarvardStudent(\"John,Doe,Computer Science,??\") object" in {

      HarvardStudent("John,Doe,Computer Science,10").fullName should be("JOHN DOE")
      HarvardStudent("John,Doe,Computer Science,10").failedSubject should be(true)
      HarvardStudent("John,Doe,Computer Science,90").fullName should be("JOHN DOE")
      HarvardStudent("John,Doe,Computer Science,90").failedSubject should be(false)

      val studentsCSV = List("John,Doe,Computer Science,10","John,Doe,Computer Science,90")
      val studentsList = HarvardStudent.fromCSVStrings(studentsCSV)
      HarvardStudent.fromCSVStrings(studentsCSV) should be(List(HarvardStudent("John","Doe","Computer Science",10),HarvardStudent("John","Doe","Computer Science",90)))
      HarvardStudent.avgStudentScore(studentsList) should be(50)
      HarvardStudent.avgPassingScore(studentsList) should be(90)
    }
  }
}
