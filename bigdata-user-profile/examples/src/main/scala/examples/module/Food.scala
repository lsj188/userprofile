package examples.module

// In file modules/FoodCategories.scala
abstract class Food(val name: String) {
  override def toString = "food is " + name
}

case class Recipe(
                   val name: String,
                   val ingredients: List[Food],
                   val instructions: String
                   ) {
  override def toString = "recipe is " + name
}

//object Apple extends Food("Apple")
//object Orange extends Food("Orange")
//object Cream extends Food("Cream")
//object Sugar extends Food("Sugar")
//
//object FruitSalad extends Recipe(
//  "fruit salad",
//  List(Apple, Orange, Cream, Sugar),
//  "Stir it all together."
//)
abstract class Browser {
  val database: Database

  def recipesUsing(food: Food) =
    database.allRecipes.filter(recipe =>
      recipe.ingredients.contains(food))

  def displayCategory(category: database.FoodCategory) {
    println(category)
  }
}

object StudentDatabase extends Database {

  object FrozenFood extends Food("FrozenFood")

  object HeatItUp extends Recipe(
    "heat it up",
    List(FrozenFood),
    "Microwave the 'food' for 10 minutes.")

  def allFoods = List(FrozenFood)

  def allRecipes = List(HeatItUp)

  def allCategories = List(
    FoodCategory("edible", List(FrozenFood)))
}

object StudentBrowser extends Browser {
  val database = StudentDatabase
}

trait FoodCategories {

  case class FoodCategory(name: String, foods: List[Food])

  def allCategories: List[FoodCategory]
}


// In file modules/Ex5.scala

abstract class Database extends FoodCategories {
  def allFoods: List[Food]

  def allRecipes: List[Recipe]

  def foodNamed(name: String) =
    allFoods.find(f => f.name == name)
}


// In file modules/SimpleDatabase.scala

object SimpleDatabase extends Database
with SimpleFoods with SimpleRecipes


// In file modules/SimpleFoods.scala

trait SimpleFoods {

  object Pear extends Food("Pear")

  object Apple extends Food("Apple")

  def allFoods = List(Apple, Pear)

  def allCategories = Nil
}


// In file modules/SimpleRecipes.scala

trait SimpleRecipes {
  this: SimpleFoods =>

  //将this指向SimpleFoods

  object FruitSalad extends Recipe(
    "fruit salad",
    List(Apple, Pear), // Now Pear is in scope
    "Mix it all together."
  )

  def allRecipes = List(FruitSalad)
}

object GotApples {
  def main(args: Array[String]) {
    val db: Database =
      if (args(0) == "student")
        StudentDatabase
      else
        SimpleDatabase

    object browser extends Browser {
      val database:db.type = db
    }

    val apple = SimpleDatabase.foodNamed("Apple").get
    println(apple)
    for (recipe <- browser.recipesUsing(apple))
      println(recipe)
  }
}