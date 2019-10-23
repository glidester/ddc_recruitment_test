package ddc

/**
  * Represents the entity responsible for calculating a bill
  */
object Till {

  /**
    * Sums all the menu items in the given list of items.
    *
    * @param items the menu items to be summed
    * @return the total price of all the given menu items in pence
    */
  def sumMenuItems(items: List[MenuItem]): Money = {
    items.foldLeft(0) ( (total, item) => total + item.price)
  }
}
