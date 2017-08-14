package examples.parse

/**
 * Created by lsj on 2017/8/9.
 */

import scala.util.parsing.combinator._

class Arith extends JavaTokenParsers {
  def expr: Parser[Any] = term~rep("+"~term | "-"~term)
  def term: Parser[Any] = factor~rep("*"~factor | "/"~factor)
  def factor: Parser[Any] = floatingPointNumber | "("~expr~")"
}

class Outerp{outer=>
  class Inner{
    println(Outerp.this eq outer)   //这里打印为true
  }
}

object ParseExpr extends Arith {
  def paresns:Parser[Any]=rep(floatingPointNumber | "("~paresns~")"|",("~paresns~")"|failure("is error!"))
  def paresnsll1:Parser[Any]=rep(floatingPointNumber | "("~!paresnsll1~!")"|",("~!paresnsll1~!")"|failure("is error!"))
  def main(args: Array[String]) {
    val str="2*(7+3)"
    println("input : "+ str)
    println(parseAll(expr, str))
    println( parseAll(paresns,"(12233132)(2132432)"))
    println( parseAll(paresnsll1,"(12233132),(2132432)"))
  }
}
