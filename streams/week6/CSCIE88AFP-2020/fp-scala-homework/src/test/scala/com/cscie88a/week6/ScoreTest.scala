package com.cscie88a.week6

import org.scalatest.{BeforeAndAfterAll, Matchers, WordSpec}

class ScoreTest
  extends WordSpec
    with Matchers
    with BeforeAndAfterAll {
    "tests" should {
      "5.2" in {
        Score.scoresByStudentId(1, Score.allScores, Subject.allSubjects) should be(
          Map(
            Subject(2,"Chemistry") -> 92,
            Subject(1,"Physics") -> 87)
        );
        Score.scoresBySubjectId(1, Score.allScores) should be(
          Map(
            Student(1,"Emmy","Conrart","econrart0@gizmodo.com","Male","China") -> 87,
            Student(2,"Marin","Blasoni","mblasoni1@edublogs.org","Female","United States") -> 82,
            Student(4,"Delmore","Scriver","dscriver3@boston.com","Male","United States") -> 89)
        );
      }
    }
  }
