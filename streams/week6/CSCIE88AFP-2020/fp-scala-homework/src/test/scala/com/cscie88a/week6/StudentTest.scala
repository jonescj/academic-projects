package com.cscie88a.week6

import org.scalatest.{ WordSpec, BeforeAndAfterAll, Matchers }

class StudentTest
  extends WordSpec
  with Matchers
  with BeforeAndAfterAll {
    "tests" should {
      "4.2" in {
        Student.studentNameByCountry("China") should be(List(Student(1,"Emmy","Conrart","econrart0@gizmodo.com","Male","China"), Student(3,"Jesse","Chismon","jchismon2@hostgator.com","Male","China"), Student(5,"Jocelyn","Blaxlande","jblaxlande4@europa.eu","Female","China")));
        Student.studentNameByCountry("United States") should be(List(Student(2,"Marin","Blasoni","mblasoni1@edublogs.org","Female","United States"), Student(4,"Delmore","Scriver","dscriver3@boston.com","Male","United States")));
        Student.studentNameByCountry("Canada") should be(List());

        Student.studentTotalsByCountry("China") should be(3);
        Student.studentTotalsByCountry("United States") should be(2);
        Student.studentTotalsByCountry("Canada") should be(0);
      }
    }
}
