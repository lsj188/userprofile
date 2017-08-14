package examples

import scala.collection.mutable.ArrayBuffer


/**
 * Created by lsj on 2017/7/27.
 * 特质示例，堆叠功能 12章
 */
class Rational1(n:Int,d:Int) extends Ordered[Rational1]{
  val numer=n
  val denom=d
  override def compare(that: Rational1): Int = (this.numer*that.denom)-(this.denom*that.numer)
}
abstract class IntQueue{
  def get():Int
  def put(x:Int)
}
class BasicIntQueue extends IntQueue
{
  private val buf =new ArrayBuffer[Int]()
  override def get(): Int = buf.remove(0)
  override def put(x: Int): Unit = {buf+=x;print("Basic",x)}
}
trait Incrementing extends IntQueue
{
  abstract override def put(x:Int)={super.put(x+1)}
}
trait Filtering extends IntQueue
{
  abstract override def put(x:Int)={ if(x>=0) super.put(x)}
}
trait Doubling extends IntQueue
{
  abstract override def put(x:Int)={super.put(x*2)}
}
trait Three extends IntQueue
{
  abstract override def put(x:Int)={super.put(x*3)}
}
class  MyQueue3 extends BasicIntQueue with  Doubling with Three
class MyQueue2 extends BasicIntQueue with Doubling

object TestTrait extends App
{
//  val myq2=new MyQueue2
  val myq1= new BasicIntQueue with Doubling
  myq1.put(10)
  print(myq1.get())
  val myq3=new MyQueue3
//  myq2.put(10)
  myq3.put(10)
//  print(myq2.get(),myq3.get())
  print(myq3.get())
  val quefilter=new BasicIntQueue with Filtering with Doubling
  quefilter.put(-1);quefilter.put(1);quefilter.put(3)
  print(quefilter.get(),quefilter.get())
//  val a:Rational1=new Rational1(1,3)
//  val b:Rational1=new Rational1(3,5)
//  //val t=a < b
//  print(a < b )
}
