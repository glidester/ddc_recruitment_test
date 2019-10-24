package ddc

import org.scalatest.FunSuite

class MenuItemSuite extends FunSuite {
  test("MenuItem does not allow items with a price of less than 0") {
    intercept[IllegalArgumentException] {
      MenuItem("invalid", false, false, -1)
    }
  }
}

object MenuItemSuite {
  val coldDrink = MenuItem("Cola",false,false,50)
  val hotDrink = MenuItem("Coffee",true,false,100)
  val coldFood = MenuItem("Cheese Sandwich",false,true,200)
  val hotFood = MenuItem("Steak Sandwich",true,true,450)
  val freeItem = MenuItem("Mints",false,false,0)
}