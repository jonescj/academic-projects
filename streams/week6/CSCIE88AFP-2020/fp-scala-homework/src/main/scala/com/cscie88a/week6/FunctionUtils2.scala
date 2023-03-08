package com.cscie88a.week6

object FunctionUtils2 {
  /* prefixLogger: takes a string prefix and returns a function that returns an input
    string with the prefix. */
  def prefixLogger: String => String => String = prefix => value => prefix + value;

  /* sumInt: that returns the sum of an input list of integers */
  def sumInt(inList: List[Int]): Int = inList.foldRight(0)(_ + _);

  /* productInt:  that returns the product of an input list of integers */
  def productInt(inList: List[Int]): Int = inList.foldRight(1)(_ * _);

  /* all: that returns true if all values in an input list of Booleans are true and false
    if at least one value in the input list of Booleans if false. */
  def all(inList: List[Boolean]): Boolean = inList.foldRight(true)(_ && _);

  /* any: that returns true if at least one value in an input list of Booleans is true and
    false if none of the values in the input list of Booleans is true */
  def any(inList: List[Boolean]): Boolean = inList.foldRight(false)(_ || _);

  /* tokenize: that returns a list of Strings for the input text. Use a for comprehension
    for this function */
  def tokenize(text: List[String]): List[String] = text.flatMap(_.split(" "));

  /* updateCounts which takes an input Map of string counts and a string and returns a new
    Map that increments the count of the string if it already exists or adds a new key to
    the map with the input string as the key and a count of 1 as the value */
  def updateCounts(text: String, map: Map[String, Int]): Map[String, Int] =
    if (map.contains(text)) {
      map + (text -> (map(text) + 1))
    } else {
      map + (text -> 1);
    }

  /* wordCount that uses tokenize and updateCounts to return a Map of word counts in the
    input text */
  def wordCount(text: List[String]): Map[String, Int] = {
    val tokens:List[String] = this.tokenize(text);
    var map: Map[String, Int] = Map[String, Int]();
    tokens.foreach(word =>  {
      map = this.updateCounts(word,map)
    });
    return map;
  };
}