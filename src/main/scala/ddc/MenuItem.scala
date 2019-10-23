package ddc

/**
  * Represents an individual item that can be ordered from the menu
  *
  * @param name name of the item
  * @param isHot true if this item has been heated before serving, false if left cold
  * @param price the price in pence of this menu item
  */
case class MenuItem(name: String, isHot: Boolean, price: Money)
