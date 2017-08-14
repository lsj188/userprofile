package com.tedneward.calcdsl {

private[calcdsl] abstract class Expr

private[calcdsl] case class Number(value: Double) extends Expr

private[calcdsl] case class UnaryOp(operator: String, arg: Expr) extends Expr

private[calcdsl] case class BinaryOp(operator: String, left: Expr, right: Expr)
  extends Expr

object Calc {

  def simplify(e: Expr): Expr = {
    // first simplify the subexpressions
    val simpSubs = e match {
      // Ask each side to simplify
      case BinaryOp(op, left, right) => BinaryOp(op, simplify(left), simplify(right))
      // Ask the operand to simplify
      case UnaryOp(op, operand) => UnaryOp(op, simplify(operand))
      // Anything else doesn't have complexity (no operands to simplify)
      case _ => e
    }

    def simplifyTop(x: Expr) = x match {
      // Double negation returns the original value
      case UnaryOp("-", UnaryOp("-", x)) => x
      // Positive returns the original value
      case UnaryOp("+", x) => x
      // Multiplying x by 1 returns the original value
      case BinaryOp("*", x, Number(1)) => x
      // Multiplying 1 by x returns the original value
      case BinaryOp("*", Number(1), x) => x
      // Multiplying x by 0 returns zero
      case BinaryOp("*", x, Number(0)) => Number(0)
      // Multiplying 0 by x returns zero
      case BinaryOp("*", Number(0), x) => Number(0)
      // Dividing x by 1 returns the original value
      case BinaryOp("/", x, Number(1)) => x
      // Adding x to 0 returns the original value
      case BinaryOp("+", x, Number(0)) => x
      // Adding 0 to x returns the original value
      case BinaryOp("+", Number(0), x) => x
      // Anything else cannot (yet) be simplified
      case _ => e
    }


    def evaluate(e: Expr): Double = {
      simplify(e) match {
        case Number(x) => x
        case UnaryOp("-", x) => -(evaluate(x))
        case BinaryOp("+", x1, x2) => (evaluate(x1) + evaluate(x2))
        case BinaryOp("-", x1, x2) => (evaluate(x1) - evaluate(x2))
        case BinaryOp("*", x1, x2) => (evaluate(x1) * evaluate(x2))
        case BinaryOp("/", x1, x2) => (evaluate(x1) / evaluate(x2))
      }
    }
    simplifyTop(simpSubs)
  }

  import scala.util.parsing.combinator._

  object ArithParser extends JavaTokenParsers {
    def expr: Parser[Any] = term ~ rep("+" ~ term | "-" ~ term)

    def term: Parser[Any] = factor ~ rep("*" ~ factor | "/" ~ factor)

    def factor: Parser[Any] = floatingPointNumber | "(" ~ expr ~ ")"

    def parse(text: String) = {
      parseAll(expr, text)
    }
  }

  def parse(text: String) = {
    val results = ArithParser.parse(text)
    System.out.println("parsed " + text + " as " + results + " which is a type "
      + results.getClass())
  }
}

}