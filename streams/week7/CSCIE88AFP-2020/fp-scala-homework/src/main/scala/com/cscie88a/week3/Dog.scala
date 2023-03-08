package com.cscie88a.week3

case class Dog(name: String, age: Int) {
  def needsTraining: Boolean =
    if (age < 2) true else false

  def sayHello: String =
    s"${name} says woof!, woof!"

}

object Dog {
  def apply(csvRow: String): Dog = {
    val fields = csvRow.split(",")
    Dog(fields(0), fields(1).toInt)
  }
}

case class DogAggregator(count: Int, total: Long) {
  def avg: Double =
    total / count

  def + (another: DogAggregator): DogAggregator =
    DogAggregator(count + another.count, total + another.total)
}

object DogAggregator {
  def apply(dog: Dog): DogAggregator =
    DogAggregator(1, dog.age)

  val zero = DogAggregator(0,0)
}
