package examples

/**
 * Created by lsj on 2017/7/12.
 */
object MulitiArg {
  def main(args: Array[String]) {
    def func(fname: String) = (lname: String) => println(fname + " " + lname)

    func("luo")("shijun")

  }
}