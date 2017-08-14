package examples

/**
 * Created by lsj on 2017/7/12.
 */
object Application
{
  def generateException()
  {
    System.out.println("Generating exception...");
    throw new Exception("Generated exception");
  }
  def main(args : Array[String])
  {
    tryWithLogging // This is not part of the language
    {
      generateException
    }
    System.out.println("Exiting main()");
  }
  def tryWithLogging (s:() => Unit) {
    try {
      s()
    }
    catch {
      case ex: Exception =>
        // where would you like to log this?
        // I choose the console window, for now
        ex.printStackTrace()

    }
  }
}