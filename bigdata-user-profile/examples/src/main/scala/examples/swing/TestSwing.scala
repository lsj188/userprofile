package examples.swing

import scala.swing._

/**
 * Created by lsj on 2017/8/10.
 */
object FirstSwing extends SimpleSwingApplication {
  override def top: Frame = new MainFrame {
    title = "First Swing window"
    val button=new Button{text="click me"}
    val lable=new Label{text="init"}

    contents = new BoxPanel(Orientation.Vertical){
      contents+=button
      contents+=lable
      border=Swing.EmptyBorder(30,30,10,30)
    }
  }
}