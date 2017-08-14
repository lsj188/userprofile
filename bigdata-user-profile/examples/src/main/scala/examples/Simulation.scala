/*
 * Copyright (C) 2007-2008 Artima, Inc. All rights reserved.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Example code from:
 *
 * Programming in Scala (First Edition, Version 6)
 * by Martin Odersky, Lex Spoon, Bill Venners
 *
 * http://booksites.artima.com/programming_in_scala
 */

package org.stairwaybook.simulation

/**
 * 离散事件模拟在特定的时间执行用户定义的动作  18.5
 * */
abstract class Simulation {

  //执行动作类型
  type Action = () => Unit

  //工作项样本类
  case class WorkItem(time: Int, action: Action)

  private var curtime = 0
  def currentTime: Int = curtime

  //事项列表
  private var agenda: List[WorkItem] = List()

  /**
   * 有序插入工作项到事项列表
   * */
  private def insert(ag: List[WorkItem],
                     item: WorkItem): List[WorkItem] = {

    if (ag.isEmpty || item.time < ag.head.time) item :: ag
    else ag.head :: insert(ag.tail, item)
  }

  /**
   * 延时工作项
   * */
  def afterDelay(delay: Int)(block: => Unit) {
    val item = WorkItem(currentTime + delay, () => block)
    agenda = insert(agenda, item)
  }

  /**
   * 获取排在前面的工作项并执行
   * */
  private def next() {
    (agenda: @unchecked) match {
      case item :: rest =>
        agenda = rest
        curtime = item.time
        item.action()
    }
  }

  /**
   * 执行事项列表
   * */
  def run() {
    afterDelay(0) {
      println("*** simulation started, time = "+
        currentTime +" ***")
    }
    while (!agenda.isEmpty) next()
  }
}

/**
 * 电路模拟
 * */
abstract class BasicCircuitSimulation extends Simulation {
  /**
   * 反门延时
   * */
  def InverterDelay: Int
  /**
   * 与门延时
   * */
  def AndGateDelay: Int
  /**
   * 或门延时
   * */
  def OrGateDelay: Int

  /**
   * 线路
   * */
  class Wire {

    //当前线路信息
    private var sigVal = false

    //当前线路上的所有动作
    private var actions: List[Action] = List()

    /**
     * 返回当前线路加载信号true或false
     * */
    def getSignal = sigVal

    /**
     * 设置线路信号
     * */
    def setSignal(s: Boolean) =
      if (s != sigVal) {
        sigVal = s
        actions foreach (_ ())
      }

    /**
     * 线路附加动作，附加动作在附加时和以后线路信号每次改变时执行
     * */
    def addAction(a: Action) = {
      actions = a :: actions
      a()
    }
  }

  /**
   *反推器，由于返推器是具有延时，所以需要加入延迟执行
   * */
  def inverter(input: Wire, output: Wire) = {
    def invertAction() {
      val inputSig = input.getSignal

      //延时调用
      afterDelay(InverterDelay) {
        output setSignal !inputSig
      }
    }
    input addAction invertAction
  }

  /**
   * 与门
   * */
  def andGate(a1: Wire, a2: Wire, output: Wire) = {
    def andAction() = {
      val a1Sig = a1.getSignal
      val a2Sig = a2.getSignal
      afterDelay(AndGateDelay) {
        output setSignal (a1Sig & a2Sig)
      }
    }
    a1 addAction andAction
    a2 addAction andAction
  }

  /**
   * 或门
   * */
  def orGate(o1: Wire, o2: Wire, output: Wire) {
    def orAction() {
      val o1Sig = o1.getSignal
      val o2Sig = o2.getSignal
      afterDelay(OrGateDelay) {
        output setSignal (o1Sig | o2Sig)
      }
    }
    o1 addAction orAction
    o2 addAction orAction
  }

  /**
   * 模拟输出
   * */
  def probe(name: String, wire: Wire) {
    def probeAction() {
      println(name +" "+ currentTime +
        " new-value = "+ wire.getSignal)
    }
    wire addAction probeAction
  }
}

/**
 * 模拟器
 * */
abstract class CircuitSimulation
  extends BasicCircuitSimulation {

  /**
   * 半加器
   * */
  def halfAdder(a: Wire, b: Wire, s: Wire, c: Wire) {
    val d, e = new Wire
    orGate(a, b, d)
    andGate(a, b, c)
    inverter(c, e)
    andGate(d, e, s)
  }

  /**
   * 全加器
   * */
  def fullAdder(a: Wire, b: Wire, cin: Wire,
                sum: Wire, cout: Wire) {

    val s, c1, c2 = new Wire
    halfAdder(a, cin, s, c1)
    halfAdder(b, s, sum, c2)
    orGate(c1, c2, cout)
  }
}

object MySimulation extends CircuitSimulation {
  def InverterDelay = 1
  def AndGateDelay = 3
  def OrGateDelay = 5

  def main(args: Array[String]) {
    val input1, input2, sum, carry = new Wire

    probe("sum", sum)
    probe("carry", carry)
    halfAdder(input1, input2, sum, carry)

    input1 setSignal true
    run()

    input2 setSignal true
    run()
  }
}
