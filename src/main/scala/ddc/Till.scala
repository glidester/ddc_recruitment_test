package ddc

/**
  * Represents the entity responsible for calculating a bill
  */
object Till {
  def sumMenuItems(items: List[MenuItem]): Money = {
    items.foldLeft(0) ( (total, item) => total + item.price)
  }
}
