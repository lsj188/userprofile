package examples

/**
 * Created by lsj on 2017/8/7.
 */
case class Tree(name: String,
                isMale: Boolean,
                children: Tree*)


object UseTypes extends App {
  println("==================类型使用===================")
  val l1t1 = Tree("l1t1", false)
  val l2t1 = Tree("l2t1", false)
  val l2t2 = Tree("l2t2", true, l1t1, l2t1)
  val trees = List(l1t1, l2t1, l2t2)
  val result = trees.filter(p => p.isMale).flatMap(p => (p.children.map(c => (p.name, c.name))))
  println(result)

  val l1s = for (t <- trees; //生成器
                 n = t.name; //定义
                 if (n.startsWith("l1")) //过滤器
  ) yield n
  l1s.foreach(println)

  /**
   * for 转义为高阶函数实现（map,flatMap,filter）
   *
   * for(x<-exp1) yield exp2             <=>  exp1.map(x=>exp2)
   * for(x<-exp1;if exp2) yield exp3     <=> for(x<-exp1 filter (x=>exp2)) yield exp3   <=> exp1.filter(x=>exp2).map(x=>exp3)
   * for(x<-exp1;y<-exp2;seq) yield exp3 <=> exp1.flatMap(x=>for(y<-exp2;seq) yield exp3)
   * for((x1...xn)=>exp1) yield exp2     <=> exp1.map(case(x1...xn)=>exp2)
   * for(pat<-exp1) yield exp2           <=> exp1.filter(case pat => true
   * case _ => false).map(case pat => exp2)
   *
   * for(x<-exp1;y=exp2;seq) yield exp3   <=> for((x,y)<-for(x<-exp1) yeild(x,exp2);seq) yeild exp3
   * ===================
   * for循环转化为foreach
   * for(x<-exp1) body                   <=> exp1.foreach(x=>body)
   * for(x<-exp1;if exp2;y<-exp3) body   <=> exp1.filter(x=>exp2).foreach(x=>exp3.foreach(y=>body))
   *
   **/

  /**
   * 抽取器
   **/
  println("==================抽取器===================")

  object Email {
    def apply(name: String, domain: String) = name + "@" + domain

    def unapply(str: String): Option[(String, String)] = {
      val parts = str.split("@")
      if (parts.length == 2) Some(parts(0), parts(1)) else None
    }
  }

  val email = "luoshijun58@163.com"
  email match {
    case Email(name, domain) => println(name, domain)
  }
  val email1 = "jlsdjflsjdlfksa"
  email1 match {
    case Email(name, domain) => println(name, domain)
    case _ => println("No match!")
  }


  /**
   * 变量抽取器，可以用在判断固定包含的格式
   **/
  println("==================变量抽取器===================")

  object Domain {
    def apply(parts: String*): String = parts.reverse.mkString(".")

    def unapplySeq(whole: String): Option[Seq[String]] = Some(whole.split("\\.").reverse)
  }

  val (dom, dom1) = ("163.com", "acm.org")

  def domTest(domain: String): Unit = domain match {
    case Domain("org", "acm") => println("acm.org")
    case Domain("com", "163") => println("163.com")
    case _ => println("No match!")
  }

  domTest(dom)
  domTest(dom1)

  def isLsjcom(email: String): Boolean = email match {
    case Email("lsj", Domain("com", _*)) => true
    case _ => false
  }

  println(isLsjcom("lsj@163.com"))
  println(isLsjcom("lsj@163.net"))
  println(isLsjcom("abc@163.com"))



  println("=============抽取器用法==============")
  object ExpandedEmail {
    //  def apply(name:String,doms:String*):String=name+"@"+doms.mkString(".") // 可选
    def unapplySeq(email: String): Option[(String, Seq[String])] = {
      val parts = email.split("@")
      if (parts.length == 2)
        Some(parts(0), parts(1).split("\\.").reverse.toSeq)   //去掉toSeq时软件检测报错，但编译运行没有问题
      else None
    }
  }
  val s = "luoshijun@163.com.cn"
  val ExpandedEmail(name, topdom, subdom @_*) = s //变量绑定
  println("name=" + name, "topdom=" + topdom, "subdom=" + subdom.mkString("."))

  /**
   * 正则表达式的使用
   * 两个特殊的符号'^'和'$'。他们的作用是分别指出一个字符串的开始和结束
   * '*'，'+'和'?'这三个符号，表示一个或一序列字符重复出现的次数。它们分别表示“没有或更多”，“一次或更多”还有“没有或一次”。
   * 你也可以使用范围，用大括号括起，用以表示重复次数的范围。
   *                  "ab{2}"：表示一个字符串有一个a跟着2个b（"abb"）；
   *                  "ab{2,}"：表示一个字符串有一个a跟着至少2个b；
   *                  "ab{3,5}"：表示一个字符串有一个a跟着3到5个b。
   *'|'，表示“或”操作：*
   * "hi|hello"：表示一个字符串里有"hi"或者"hello"；
   * "(b|cd)ef"：表示"bef"或"cdef"；
   * "(a|b)*c"：表示一串"a""b"混合的字符串后面跟一个"c"；
   * '.'可以替代任何字符：
   * "a.[0-9]"：表示一个字符串有一个"a"后面跟着一个任意字符和一个数字；
   * "^.{3}$"：表示有任意三个字符的字符串（长度为3个字符）；
   * 方括号表示某些字符允许在一个字符串中的某一特定位置出现：
   * "[ab]"：表示一个字符串有一个"a"或"b"（相当于"a|b"）；
   * "[a-d]"：表示一个字符串包含小写的'a'到'd'中的一个（相当于"a|b|c|d"或者"[abcd]"）；
   * "^[a-zA-Z]"：表示一个以字母开头的字符串；
   * "[0-9]%"：表示一个百分号前有一位的数字；
   * ",[a-zA-Z0-9]$"：表示一个字符串以一个逗号后面跟着一个字母或数字结束。
   * 你也可以在方括号里用'^'表示不希望出现的字符，'^'应在方括号里的第一位。（如："%[^a-zA-Z]%"表
   * 示两个百分号中不应该出现字母）。
   * 为了逐字表达，你必须在"^.$()|*+?{\"这些字符前加上转移字符'\'。
   * 请注意在方括号中，不需要转义字符。
   * 2.正则表达式验证控制文本框的输入字符类型
   * 1.只能输入数字和英文的：
   * <input onkeyup="value=value.replace(/[\W]/g,'') " onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))" ID="Text1" NAME="Text1">
   * 2.只能输入数字的：
   * <input onkeyup="value=value.replace(/[^\d]/g,'') " onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))" ID="Text2" NAME="Text2">
   * 3.只能输入全角的：
   * <input onkeyup="value=value.replace(/[^\uFF00-\uFFFF]/g,'')" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\uFF00-\uFFFF]/g,''))" ID="Text3" NAME="Text3">
   * 4.只能输入汉字的：
   * <input onkeyup="value=value.replace(/[^\u4E00-\u9FA5]/g,'')" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\u4E00-\u9FA5]/g,''))" ID="Text4" NAME="Text4">
   * 3.正则表达式的应用实例通俗说明
   *    *******************************************************************************
   * //校验是否全由数字组成
   * /^[0-9]{1,20}$/
   * ^ 表示打头的字符要匹配紧跟^后面的规则
   * $ 表示打头的字符要匹配紧靠$前面的规则
   * [ ] 中的内容是可选字符集
   * [0-9] 表示要求字符范围在0-9之间
   * {1,20}表示数字字符串长度合法为1到20，即为[0-9]中的字符出现次数的范围是1到20次。
   * /^ 和 $/成对使用应该是表示要求整个字符串完全匹配定义的规则，而不是只匹配字符串中的一个子串。
   *    *******************************************************************************
   * //校验登录名：只能输入5-20个以字母开头、可带数字、“_”、“.”的字串
   * /^[a-zA-Z]{1}([a-zA-Z0-9]|[._]){4,19}$/
   * ^[a-zA-Z]{1} 表示第一个字符要求是字母。
   * ([a-zA-Z0-9]|[._]){4,19} 表示从第二位开始（因为它紧跟在上个表达式后面）的一个长度为4到9位的字符串，它要求是由大小写字母、数字或者特殊字符集[._]组成。
   *    *******************************************************************************
   * //校验用户姓名：只能输入1-30个以字母开头的字串
   * /^[a-zA-Z]{1,30}$/
   *    *******************************************************************************
   * //校验密码：只能输入6-20个字母、数字、下划线
   * /^(\w){6,20}$/
   * \w：用于匹配字母，数字或下划线字符
   *    *******************************************************************************
   * //校验普通电话、传真号码：可以“+”或数字开头，可含有“-” 和 “ ”
   * /^[+]{0,1}(\d){1,3}[ ]?([-]?((\d)|[ ]){1,12})+$/
   * \d：用于匹配从0到9的数字；
   * “?”元字符规定其前导对象必须在目标对象中连续出现零次或一次
   * 可以匹配的字符串如：+123 -999 999 ； +123-999 999 ；123 999 999 ；+123 999999等
   *    *******************************************************************************
   * //校验URL
   * /^http[s]{0,1}:\/\/.+$/ 或 /^http[s]{0,1}:\/\/.{1,n}$/ (表示url串的长度为length(“https://”) + n )
   * \ / ：表示字符“/”。
   * . 表示所有字符的集
   * + 等同于{1,}，就是1到正无穷吧。
   *   "^\d+$"　　//非负整数（正整数 + 0）
   * "^[0-9]*[1-9][0-9]*$"　　//正整数
   * "^((-\d+)|(0+))$"　　//非正整数（负整数 + 0）
   * "^-[0-9]*[1-9][0-9]*$"　　//负整数
   * "^-?\d+$"　　　　//整数
   * "^\d+(\.\d+)?$"　　//非负浮点数（正浮点数 + 0）
   * "^(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-9]*))$"　　//正浮点数
   * "^((-\d+(\.\d+)?)|(0+(\.0+)?))$"　　//非正浮点数（负浮点数 + 0）
   * "^(-(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-9]*)))$"　　//负浮点数
   * "^(-?\d+)(\.\d+)?$"　　//浮点数
   * "^[A-Za-z]+$"　　//由26个英文字母组成的字符串
   * "^[A-Z]+$"　　//由26个英文字母的大写组成的字符串
   * "^[a-z]+$"　　//由26个英文字母的小写组成的字符串
   * "^[A-Za-z0-9]+$"　　//由数字和26个英文字母组成的字符串
   * "^\w+$"　　//由数字、26个英文字母或者下划线组成的字符串
   * "^[\w-]+(\.[\w-]+)*@[\w-]+(\.[\w-]+)+$"　　　　//email地址
   * "^[a-zA-z]+://(\w+(-\w+)*)(\.(\w+(-\w+)*))*(\?\S*)?$"　　//url
   * /^(d{2}|d{4})-((0([1-9]{1}))|(1[1|2]))-(([0-2]([1-9]{1}))|(3[0|1]))$/   //  年-月-日
   * /^((0([1-9]{1}))|(1[1|2]))/(([0-2]([1-9]{1}))|(3[0|1]))/(d{2}|d{4})$/   // 月/日/年
   * "^([w-.]+)@(([[0-9]{1,3}.[0-9]{1,3}.[0-9]{1,3}.)|(([w-]+.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(]?)$"   //Emil
   * "(d+-)?(d{4}-?d{7}|d{3}-?d{8}|^d{7,8})(-d+)?"     //电话号码
   * "^(d{1,2}|1dd|2[0-4]d|25[0-5]).(d{1,2}|1dd|2[0-4]d|25[0-5]).(d{1,2}|1dd|2[0-4]d|25[0-5]).(d{1,2}|1dd|2[0-4]d|25[0-5])$"   //IP地址
   * ^([0-9A-F]{2})(-[0-9A-F]{2}){5}$   //MAC地址的正则表达式
   * "^[-+]?\d+(\.\d+)?$"  //值类型正则表达式
   *
   * */
  object MyRegex{
    import  scala.util.matching.Regex
    def findAllNum(str:String):List[String]={
      val regex1=new Regex("(-)?(\\d+)(\\.\\d*)?")  //也可用 """ 三个引号不使用转义字符的形式，如果表达式中出现多个需要转义时这将很有用
      val regex1bak=new Regex("""(-)?(\d+)(\.\d*)?""")  //也可用 """ 三个引号不使用转义字符的形式
      regex1bak.findAllIn(str).toList
    }
    def findAllEmail(str:String):List[String]={
      val regex=new Regex("""[\w-]+(\.[\w-]+)*@[\w-]+(\.[\w-]+)+""")
      regex.findAllIn(str).toList
    }
  }
  MyRegex.findAllNum("for -1.0 to 99 by 3").foreach(println)
  val Decimal="""(-)?(\d+)(\.\d*)?""".r  //用正则抽取值
  val Decimal(fh,qian,hou) = "-12.23"    //用正则抽取值
  println(fh,qian,hou)
  val Decimal(fh1,qian1,hou1) = "12.23"    //用正则抽取值
  println(fh1,qian1,hou1)
  MyRegex.findAllEmail("test@163.com  kjsdlj safsdf test1@org.com kljkj test3@xl.org.cn ").foreach(println)


}