package examples.swing

/**
 * Created by lsj on 2017/8/10.
 */
import scala.swing._
import event._

object TempConverter extends SimpleSwingApplication {
  def top = new MainFrame {
    title = "Celsius/Fahrenheit Converter"
    object celsius extends TextField { columns = 5 }
    object fahrenheit extends TextField { columns = 5 }

    /**
     * FlowPanel容器表示可以用一行或多行，一个组件一个组件排列显示
     * BoxPanel是列显示
     * */
    contents = new FlowPanel {
      contents += celsius
      contents += new Label(" Celsius  =  ")
      contents += fahrenheit
      contents += new Label(" Fahrenheit")
      border = Swing.EmptyBorder(15, 10, 10, 10)
    }
    listenTo(celsius, fahrenheit)
    reactions += {
      case EditDone(`fahrenheit`) =>
        val f = fahrenheit.text.toInt
        val c = (f - 32) * 5 / 9
        celsius.text = c.toString
      case EditDone(`celsius`) =>
        val c = celsius.text.toInt
        val f = c * 9 / 5 + 32
        fahrenheit.text = f.toString
    }
  }
}