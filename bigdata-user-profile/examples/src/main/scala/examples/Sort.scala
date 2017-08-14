package examples

/**
 * Created by lsj on 2017/7/12.
 */
object Sort {
  def sort(xs: Array[Int]) {
    def swap(i: Int, j: Int) {
      val t = xs(i); xs(i) = xs(j); xs(j) = t
    }
    def sort1(l: Int, r: Int) {
      val pivot = xs((l + r) / 2)
      var i = l; var j = r
      while (i <= j) {
        while (xs(i) < pivot) i += 1
        while (xs(j) > pivot) j -= 1
        if (i <= j) {
          swap(i, j)
          i += 1
          j -= 1
        }
      }
      if (l < j) sort1(l, j)
      if (j < r) sort1(i, r)
    }
    sort1(0, xs.length-1)
  }
  def simple_sort(xs: Array[Int]): Array[Int] =
    if (xs.length <= 1) xs
    else {
      val pivot = xs(xs.length / 2)
      Array.concat(
        simple_sort(xs filter (pivot>)),
        xs filter (pivot ==),
        simple_sort(xs filter (pivot <)))
    }

  def main(args: Array[String]) {
    val a=Array(213,23,1,312,31,23,21,31,3,213,1,3,3243)
    val b=simple_sort(a)
    b.foreach(println(_))
  }
}
