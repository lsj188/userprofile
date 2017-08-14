package examples

/**
 * 特质（trait）也是抽象，抽象类是不能实例化的，特质可以在实例化时实现抽象
 */
//抽象Val
abstract class Fruit {
  val v: String

  def m: String
}

abstract class Apple extends Fruit {
  val v: String
  val m: String
}

//由于父类定义为Val类型所有不能被覆盖为其它类型
//abstract class BadApple extends Fruit{
//  def v:String
//  val m:String
//}

//抽象Var
trait AbstractTime {
  var hour: Int
  var minute: Int
}

trait AbstractTime1 {
  def hour: Int

  def hour_=(x: Int)

  def minute: Int

  def minute_=(x: Int)
}

/**
 * 类参数和类抽象字段
 * 类参数构造类实例，参数表达式会在类初使化时计算，而类抽象实例是父类初使化完后才计算
 * Scala将提供两种方法解决val抽像问题，预初使化和懒加载，lazy只能用在val关键字上
 **/
trait RelationTrait {
  val fz: Int
  val fm: Int
  require(fm != 0)
  private val g = gc(fz, fm)
  val numer = fz / g
  val denmo = fm / g

  def gc(a: Int, b: Int): Int = {
    if (b == 0) a else gc(b, a % b)
  }

  override def toString = numer + "/" + denmo
}

//abstract class RelationTrait1(val fz: Int, val fm: Int) {
class RelationTrait1(val fz: Int, val fm: Int) {

  require(fm != 0)
  private val g = gc(fz, fm)
  val numer = fz / g
  val denmo = fm / g

  def gc(a: Int, b: Int): Int = {
    if (b == 0) a else gc(b, a % b)
  }

  override def toString = numer + "/" + denmo
}

class AbstractMember {

}
/**
 * 抽象类型
 * */
class Food
abstract class Animal{

  type suitable <:Food
  //父类指定eat传入参数的限制，而不指定具体的类型
  def eat(food:suitable)
}
class Grass extends Food
class Fish extends Food
class Cow extends Animal
{
  //子类实现的时候指定具体的类型
  override type suitable = Grass

  override def eat(food: suitable){}
}
class DogFood extends Food
class Dog extends Animal{
  override type suitable = DogFood
  override def eat(food: suitable){}
}
/**
 *路径依赖
 **/
class Outer{
  class Inner{}
}
/**
 * 枚举类型，可以使用ID查看位置序号，从0开始
 * */
object Direction extends Enumeration{
  val one=Value("1")
  val two=Value("2")
  val three=Value("3")
}







 object RunAbstractMember extends App {
  //枚举类型
   for(desc <- Direction.values) println(desc)
  //抽象类型
  val cow=new Cow();cow.eat(new Grass)
//  new Cow().eat(new Fish)    //会报错，因为在Cow只指定了具体的类型（Grass）
//路径依赖类型
//  val dogA=new Dog;dogA.eat(new cow.suitable)  //报错
  val dogA=new Dog;dogA.eat(new DogFood)
  val dogB=new Dog;dogB.eat(new dogA.suitable)
  val o1=new Outer
  val i1=new o1.Inner;
//  val i2=new Outer#Inner   //会报错，因为没有Outer实例，调用内部类，必须基于外部类实例

  val abTime = new AbstractTime {
    override var hour: Int = 3
    override var minute: Int = 4

    override def toString = "hour=" + hour + ",minute=" + minute
  }
  println(abTime)

  //匿名构造类
  val x = 3
  //会报错，因为实例化会先初使化RelationTrait，参数未代入值，加上lazy关键字就不会报错了
  val relation2 = new RelationTrait {
    lazy override val fm: Int = 1 * x
    lazy override val fz: Int = 2 * x
  }
  //Scala将提供两种方法解决这种问题，预初使化和懒加载
  val relation = new {
    val fm: Int = 1 * x
    val fz: Int = 2 * x
  } with RelationTrait
  println(relation)

  //懒加载，只有在使用到变量的时候才会计算
  object Demo {
    val x = {
      println("this is Demo x"); "done"
    }
  }

  object Demo1 {
    lazy val x = {
      println("this is Demo x"); "done"
    }
  }

  Demo;
  println("==");
  Demo.x;
  println("==");
  Demo1;
  println("==");
  Demo1.x
  val relation1 = new RelationTrait1(1 * x, 2 * x)


}
