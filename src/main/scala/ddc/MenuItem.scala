package ddc

/**
  * Represents an individual item that can be ordered from the menu
  *
  * @param name name of the item
  * @param isHot true if this item has been heated before serving, false if left cold
  * @param isFood true if this item is a food substance, false if it is a drink
  * @param price the price in pence of this menu item
  */
case class MenuItem(name: String, isHot: Boolean, isFood: Boolean, price: Money) {
  require(price >= 0, "A menu item can not represent giving money back to a customer!")
}
