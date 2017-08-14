package examples

/**
 * Created by lsj on 2017/7/12.
 */
object App
{
  def grep(pattern : String, dir : java.io.File) =
  {
    val filesHere = dir.listFiles
    for (
      file <- filesHere;
      if (file.getName.endsWith(".scala") || file.getName.endsWith(".java"));
      line <- scala.io.Source.fromFile(file).getLines;
      if line.trim.matches(pattern)
    ) println(line)
  }
  def main(args : Array[String]) =
  {
    val pattern = ".*Created.*"
    grep(pattern, new java.io.File("F:\\资料文件\\scala教程\\scala_examples\\src\\main\\scala\\test"))
  }
}