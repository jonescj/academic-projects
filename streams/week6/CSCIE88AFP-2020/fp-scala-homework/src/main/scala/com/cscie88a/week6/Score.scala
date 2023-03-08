package com.cscie88a.week6

case class Score(studentId:Int, subjectId:Int, numericScore:Int)

object Score{
  val allScores:List[Score] = List(
    Score(1, 1, 87), Score(1, 2, 92),
    Score(2, 1, 82), Score(2, 2, 80),
    Score(2, 3, 89), Score(2, 4, 85),
    Score(3, 3, 89), Score(3, 4, 90),
    Score(4, 1, 89), Score(4, 2, 86),
    Score(4, 4, 90), Score(5, 2, 90),
    Score(5, 3, 87), Score(5, 4, 92)
  );

  def scoresByStudentId(studentID:Int, scores:List[Score],subjectMap:Map[Int, Subject]):Map[Subject,Int] = {
    var map: Map[Subject, Int] = Map[Subject, Int]();
    allScores.filter(score => score.studentId == studentID)
      .groupBy(_.subjectId)
      .foreach(x => {
        val subject = subjectMap(x._1)
        val grade = x._2(0).numericScore;
        map = map + (subject -> grade);
      })
    return map;
  }

  def scoresBySubjectId(subjectID:Int, scores:List[Score]):Map[Student,Int] = {
    var map: Map[Student, Int] = Map[Student, Int]();
    allScores.filter(score => score.subjectId == subjectID)
      .foreach(x => {
        val student = Student.allStudentsMap(x.studentId);
        val grade = x.numericScore
        map = map + (student -> grade)
      })
    return map;
  }
}