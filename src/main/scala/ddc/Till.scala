package ddc

import ddc.Till.{cheeseSandwich, coffee, cola, steakSandwich, hestonDish}

/**
  * A simple class to exercise the implementation
  */
class Till(items: List[MenuItem]) extends App {
  import Bill._

  val itemsTotal = sumMenuItems(items)
  val totalBill = calculateTotalBill(items)
  val serviceCharge = totalBill - itemsTotal

  println("=====================")
  items.foreach( item => println(s"* ${item.name} ${item.price}p ") )
  println(s"\nSum: ${itemsTotal}p")
  println("=====================")

  println("=====================")
  println(s"Service charge: ${serviceCharge}p")
  println(s"Total to pay: ${totalBill}p")
  println("=====================")
}

object Till {
  val cola = MenuItem("Cola",false,false,50)
  val coffee = MenuItem("Coffee",true,false,100)
  val cheeseSandwich = MenuItem("Cheese Sandwich",false,true,200)
  val steakSandwich = MenuItem("Steak Sandwich",true,true,450)
  val hestonDish = MenuItem("Heston Blumenthal Dish",true,true,11000)
}

case object ColaCoffeeCheeseSandwich extends Till(List(cola, coffee, cheeseSandwich))
case object FullMealForFour extends Till(List(cola, coffee, coffee, coffee, cheeseSandwich, cheeseSandwich, steakSandwich, steakSandwich))
case object MaxServiceCharge extends Till(List(cola, hestonDish))