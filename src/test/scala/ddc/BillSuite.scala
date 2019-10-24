package ddc

import ddc.MenuItemSuite._
import org.scalatest.FunSuite
import Bill.{sumMenuItems, calculateTotalBill}

class BillSuite extends FunSuite {
  test("sumMenuItems correctly calculates the sum of all items") {
    withClue("handle a bill with no items on it") {
      assert( sumMenuItems(List()) == 0)
    }

    withClue("handle a bill with only items of no value on it") {
      assert( sumMenuItems(List(freeItem)) == 0)
      assert( sumMenuItems(List(freeItem, freeItem)) == 0)
    }

    withClue("handle a bill with one item on it") {
      assert( sumMenuItems(List(coldDrink)) == coldDrink.price)
      assert( sumMenuItems(List(coldFood)) == coldFood.price)
    }

    withClue("handle a bill with many items on it") {
      assert( sumMenuItems(List(coldDrink, coldFood)) == coldDrink.price + coldFood.price)
      assert( sumMenuItems(List(coldFood, hotFood, freeItem)) == coldFood.price + hotFood.price + freeItem.price)
    }

    withClue("order of items on bill is not important") {
      assert( sumMenuItems(List(coldFood, hotFood, hotDrink)) == sumMenuItems(List(hotDrink, coldFood, hotFood)))
      assert( sumMenuItems(List(hotDrink, coldFood, hotFood)) == sumMenuItems(List(hotFood, hotDrink, coldFood)))
      assert( sumMenuItems(List(hotFood, hotDrink, coldFood)) == sumMenuItems(List(coldFood, hotFood, hotDrink)))
    }

    withClue("handle a bill with one item on it many times") {
      assert( sumMenuItems(List(coldDrink, coldDrink, coldDrink, coldDrink)) == coldDrink.price + coldDrink.price + coldDrink.price + coldDrink.price)
    }

    withClue("handle a bill with only hot items on it") {
      assert( sumMenuItems(List(hotDrink, hotDrink, hotFood, hotFood)) == hotDrink.price + hotDrink.price + hotFood.price + hotFood.price)
    }

    withClue("handle a bill with only cold items on it") {
      assert( sumMenuItems(List(coldDrink, coldDrink, coldFood, coldFood)) == coldDrink.price + coldDrink.price + coldFood.price + coldFood.price)
    }

    withClue("handle a bill with only food items on it") {
      assert( sumMenuItems(List(hotFood, coldFood)) == hotFood.price + coldFood.price)
    }

    withClue("handle a bill with only drink items on it") {
      assert( sumMenuItems(List(hotDrink, coldDrink)) == hotDrink.price + coldDrink.price)
    }
  }

  info("calculateTotalBill not tested as it doesn't contain any logic of its own")

}
