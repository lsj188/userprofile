package examples

/**
 * Created by lsj on 2017/7/13.
 */
trait Student {
  def getFirstName: String;

  def getLastName: String;

  def setFirstName(fn: String): Unit;

  def setLastName(fn: String): Unit;

  def teach(subject: String)
}

object StudentFactory {

  class StudentImpl(var first: String, var last: String, var subject: String)
    extends Student {
    def getFirstName: String = first

    def setFirstName(fn: String): Unit = first = fn

    def getLastName: String = last

    def setLastName(ln: String): Unit = last = ln

    def teach(subject: String) =
      System.out.println("I know " + subject)
  }

  def getStudent(firstName: String, lastName: String): Student = {
    new StudentImpl(firstName, lastName, "Scala")
  }
}
