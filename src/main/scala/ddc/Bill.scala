package ddc

import ddc.ServiceCharge.{boundServiceCharge, calculateServiceChargeAmount, defineServiceChargeRateForBill}

/**
  * Holds the logic for calculating a bill
  */
object Bill {
  /**
    * Sums all the menu items in the given list of items.
    *
    * @param items the menu items to be summed
    * @return the total price of all the given menu items in pence
    */
  def sumMenuItems(items: List[MenuItem]): Money = {
    items.foldLeft(0) ( (total, item) => total + item.price)
  }

  /**
    * Given a list of MenuItems it returns the correct total amount of the
    * bill including the applicable service charge amount
    *
    * @param items the menu items present on a bill
    * @return the total bill amount including any service charge applicable
    */
  def calculateTotalBill(items: List[MenuItem]): Money = {
    val totalItemsOnBillAmount: Money = sumMenuItems(items)

    val serviceChargeRate = defineServiceChargeRateForBill(items)
    val serviceChargeAmount = calculateServiceChargeAmount(totalItemsOnBillAmount, serviceChargeRate.multiplier)

    val boundedServiceChargeAmount = boundServiceCharge(serviceChargeAmount, serviceChargeRate.maximumAmount)

    totalItemsOnBillAmount + boundedServiceChargeAmount
  }
}
