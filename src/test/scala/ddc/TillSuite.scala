package ddc

import org.scalatest.{BeforeAndAfter, FunSuite}

class TillSuite extends FunSuite with BeforeAndAfter {

  val cola = MenuItem("Cola",false,50)
  val coffee = MenuItem("Coffee",true,100)
  val cheeseSandwich = MenuItem("Cheese Sandwich",false,200)
  val steakSandwich = MenuItem("Steak Sandwich",true,450)

  test("handle a bill with no items on it") {
    assert(Till.sumMenuItems(List()) == 0)
  }

  test("handle a bill with one item on it") {
    assert(Till.sumMenuItems(List(cola)) == cola.price)
    assert(Till.sumMenuItems(List(cheeseSandwich)) == cheeseSandwich.price)
  }

  test("handle a bill with many items on it") {
    assert(Till.sumMenuItems(List(cola, cheeseSandwich)) == cola.price + cheeseSandwich.price)
    assert(Till.sumMenuItems(List(cheeseSandwich, steakSandwich)) == cheeseSandwich.price + steakSandwich.price)
  }

  test("order of items on bill is not important") {
    assert(Till.sumMenuItems(List(cheeseSandwich, steakSandwich, coffee)) == Till.sumMenuItems(List(coffee, cheeseSandwich, steakSandwich)))
    assert(Till.sumMenuItems(List(coffee, cheeseSandwich, steakSandwich)) == Till.sumMenuItems(List(steakSandwich, coffee, cheeseSandwich)))
    assert(Till.sumMenuItems(List(steakSandwich, coffee, cheeseSandwich)) == Till.sumMenuItems(List(cheeseSandwich, steakSandwich, coffee)))
  }

  test("handle a bill with one item on it many times") {
    assert(Till.sumMenuItems(List(cola, cola, cola, cola)) == cola.price + cola.price + cola.price + cola.price)
  }

  test("handle a bill with only hot items on it") {
    assert(Till.sumMenuItems(List(coffee, coffee, coffee, coffee)) == coffee.price + coffee.price + coffee.price + coffee.price)
  }

  test("handle a bill with only cold items on it") {
    assert(Till.sumMenuItems(List(cola, cola, cola, cola)) == cola.price + cola.price + cola.price + cola.price)
  }
}
