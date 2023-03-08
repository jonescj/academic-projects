package com.cscie88a.week6

case class Student(id:Int, firstName:String, lastName:String, email:String, gender:String, country:String)

object Student {
  val allStudents: List[Student] = List(
    Student(1, "Emmy", "Conrart", "econrart0@gizmodo.com", "Male", "China"),
    Student(2, "Marin", "Blasoni", "mblasoni1@edublogs.org", "Female", "United States"),
    Student(3, "Jesse", "Chismon", "jchismon2@hostgator.com", "Male", "China"),
    Student(4, "Delmore", "Scriver", "dscriver3@boston.com", "Male", "United States"),
    Student(5, "Jocelyn", "Blaxlande", "jblaxlande4@europa.eu", "Female", "China")
  );
  val allStudentsMap:Map[Int,Student] = Map(
    1 -> Student(1, "Emmy", "Conrart", "econrart0@gizmodo.com", "Male", "China"),
    2 -> Student(2, "Marin", "Blasoni", "mblasoni1@edublogs.org", "Female", "United States"),
    3 -> Student(3, "Jesse", "Chismon", "jchismon2@hostgator.com", "Male", "China"),
    4 -> Student(4, "Delmore", "Scriver", "dscriver3@boston.com", "Male", "United States"),
    5 -> Student(5, "Jocelyn", "Blaxlande", "jblaxlande4@europa.eu", "Female", "China")
  )

  def studentNameByCountry(country: String): List[Student] =
    allStudents.filter(student => student.country == country);

  def studentTotalsByCountry(country: String):Int =
    allStudents.filter(student => student.country == country)
      .map( student => 1 ).foldRight(0)(_+_);
      //.reduce(_+_); // if assumed non-empty results
}