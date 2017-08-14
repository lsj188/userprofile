import java.util.Date

import examples.tools.DateTool
import org.junit.Assert._
import org.junit._;

/**
 *
 * Created by lsj on 2017/7/13.
 */
class TypeAndTest {
  @Test def recurseWithPM = {
    val myVIPList = "Ted" :: "Amanda" :: "Luke" :: "Don" :: "Martin" :: Nil
    def count(VIPs: List[String]):
    Int = {
      VIPs match {
        case h :: t => count(t) + 1
        case Nil => 0
      }
    }
    assertEquals(count(myVIPList),
      myVIPList.length)
  }

  @Test def recurseWithPMAndSayHi = {
    val myVIPList = "Ted" :: "Amanda" :: "Luke" :: "Don" :: "Martin" :: Nil
    var foundAmanda = false
    def count(VIPs: List[String]): Int = {
      VIPs match {
        case "Amanda" :: t =>
          System.out.println("Hey, Amanda!"); foundAmanda = true; count(t) + 1
        case h :: t =>
          count(t) + 1
        case Nil =>
          0
      }
    }
    assertEquals(count(myVIPList), myVIPList.length)
    assertTrue(foundAmanda)
  }

  /**
   * 模式匹配
   **/
  @Test def optionWithPM = {
    val footballTeamsAFCEast =
      Map("New England" -> "Patriots",
        "New York" -> "Jets",
        "Buffalo" -> "Bills",
        "Miami" -> "Dolphins")
    def show(value: Option[String]) = {
      value match {
        case Some(x) => x
        case None => "No team found"
      }
    }
    assertEquals(show(footballTeamsAFCEast.get("Miami")), "Dolphins")
    //    assertEquals(show(footballTeamsAFCEast.get("Buffalo")), "Dolphins")
    //    assertEquals(show(footballTeamsAFCEast.get("CQ")),"CQ")
  }

  /**
   * 元组类型
   **/
  @Test def simpleTuples() = {
    val tedsStartingDateWithScala = Date.parse("3/7/2006")
    val tuple = ("Ted", "Scala", tedsStartingDateWithScala)
    assertEquals(tuple._1, "Ted")
    assertEquals(tuple._2, "Scala")
    assertEquals(tuple._3, tedsStartingDateWithScala)
  }

  /**
   * 测试
   * */
  @Test
  def testDate: Unit = {
    print("现在时间：" + DateTool.getNowDate())
    print("昨天时间：" + DateTool.getYesterday())
    print("本周开始" + DateTool.getNowWeekStart())
    print("本周结束" + DateTool.getNowWeekEnd())
    print("本月开始" + DateTool.getNowMonthStart())
    print("本月结束" + DateTool.getNowMonthEnd())
    print("\n")
    print(DateTool.timeFormat("1436457603"))
    print(DateTool.DateFormat("1436457603"))

  }
}