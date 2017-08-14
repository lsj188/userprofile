package examples

import java.io.File

/**
 * Created by lsj on 2017/7/12.
 * for 语句的使用方式
 */
object Test_for {
  def log(item: Int): Boolean = {
    println("item=" + item)
    true
  }

  def main(args: Array[String]) {
    //    for (i <- 1 to 10) {
    //      println(i)
    //    }


    //    for (i <- 1 to 10;if i%2==0) {
    //      println(i)
    //    }


    //    val a = (for (i <- 1 to 10; if i % 2 == 0) yield i)
    //    a.foreach(println(_))
    //    for (i<- 1 to 10;if log(i);if (i%2)==0)
    //    {
    //      println("main-item="+i)
    //    }

    val fileHere = (new File("F:\\资料文件\\scala教程\\scala_examples\\src\\main\\scala\\test").listFiles())
    //    for(file<-fileHere;if file.isFile;if file.getName.endsWith(".scala"))
    //    {
    //      println("scalaFN = "+file.getName)
    //    }
    for {
      file <- fileHere
      if file.isFile
      if file.getName.endsWith(".scala")
      fileName=file.getName
    } println("scalaFN = " + fileName)

  }
}
