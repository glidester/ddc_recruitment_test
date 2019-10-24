package ddc

/** Represents structure of a service charge   */
trait ServiceCharge {
  /** The value to multiply the total bill by to define the service charge amount */
  def multiplier: Double

  /** if defined the maximum amount the service charge can reach for any bill */
  def maximumAmount: Option[Money]
}

object ServiceCharge {
  case object DefaultRate extends ServiceCharge {
    val multiplier  = 0d
    val maximumAmount = None
  }

  case object ColdFoodRate extends ServiceCharge {
    val multiplier  = 0.1d
    val maximumAmount = None
  }

  case object HotFoodRate extends ServiceCharge {
    val multiplier  = 0.2d
    val maximumAmount = Some(2000)
  }

  /**
    * Returns the correct service rate for a given item
    *
    * @param item the MenuItem to define a service rate for
    * @return a multiplier rate to calculate a service charge
    */
  def getServiceChargeForItem(item: MenuItem): ServiceCharge = {
    item match {
      case MenuItem(_, false, true, _) => ColdFoodRate
      case MenuItem(_, true, true, _) => HotFoodRate
      case _ => DefaultRate
    }
  }

  /**
    * Calculates the service charge rate for the entire bill.
    *
    * @param items all MenuItems on the bill
    * @return a multiplier rate to calculate a service charge
    */
  def defineServiceChargeRateForBill(items: List[MenuItem]): ServiceCharge = {
    val startingRate: ServiceCharge = DefaultRate
    items.foldLeft(startingRate) { (highestRate, item) =>
      val itemServiceCharge = getServiceChargeForItem(item)

      if (itemServiceCharge.multiplier > highestRate.multiplier) itemServiceCharge else highestRate
    }
  }

  /**
    * Round service charge to the nearest penny
    *
    * @param serviceCharge the service charge calculated as a percentage of the total bill
    * @return the service charge rounded to the nearest penny
    */
  def roundServiceCharge(serviceCharge: Double): Money =
    BigDecimal(serviceCharge).setScale(0, BigDecimal.RoundingMode.HALF_UP).toInt

  /**
    * Takes all the items from a bill and the chosen service charge rate to apply to the bill and
    * returns the total service charge amount to be added to the bill
    *
    * @param itemsTotal all items that form a particular bill
    * @param serviceCharge the chosen service charge rate to apply to this bill
    * @return the total service charge amount for this bill
    */
  def calculateServiceChargeAmount(itemsTotal: Money, serviceCharge: Double): Money = {
    assert(itemsTotal >= 0, "A itemsTotal can not represent a refund of money!")
    assert(serviceCharge >= 0d, "A serviceCharge can not represent a deduction of money off the bill!")

    roundServiceCharge(itemsTotal * serviceCharge)
  }

  /**
    * Apply any restrictions to the total service charge that is applied to a bill
    *
    * @param chargeAmount the total service charge amount calculated for a given bill
    * @param maybeUpperBound an optional upper bound value that the total service charge amount
    *                        may not exceed
    * @return the new total service charge value after restrictions have been applied
    */
  def boundServiceCharge(chargeAmount: Money, maybeUpperBound: Option[Money]): Money = {
    maybeUpperBound
      .map( maxCharge => if (chargeAmount > maxCharge) maxCharge else chargeAmount )
      .getOrElse(chargeAmount)
  }
}
