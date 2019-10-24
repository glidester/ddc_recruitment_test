package ddc

import org.scalatest.FunSuite

class ServiceChargeSuite extends FunSuite {
  import ServiceCharge._
  import MenuItemSuite._

  test("getServiceChargeForItem returns the correct service rate for the given item") {
    assert( getServiceChargeForItem(coldDrink) == DefaultRate)
    assert( getServiceChargeForItem(hotDrink) == DefaultRate)
    assert( getServiceChargeForItem(coldFood) == ColdFoodRate)
    assert( getServiceChargeForItem(hotFood) == HotFoodRate)
  }

  test("defineServiceChargeRateForBill correctly determines the service rate for a bill") {
    withClue("no items on the bill") {
      assert( defineServiceChargeRateForBill(List()) == DefaultRate)
    }

    withClue("only drink items on the bill") {
      assert( defineServiceChargeRateForBill(List(coldDrink)) == DefaultRate)
      assert( defineServiceChargeRateForBill(List(hotDrink)) == DefaultRate)
      assert( defineServiceChargeRateForBill(List(coldDrink, hotDrink)) == DefaultRate)
    }

    withClue("only cold food items on the bill") {
      assert( defineServiceChargeRateForBill(List(coldFood)) == ColdFoodRate)
      assert( defineServiceChargeRateForBill(List(coldFood, coldFood)) == ColdFoodRate)
    }

    withClue("only cold food and drink items on the bill") {
      assert( defineServiceChargeRateForBill(List(coldDrink, coldFood)) == ColdFoodRate)
      assert( defineServiceChargeRateForBill(List(hotDrink, coldFood)) == ColdFoodRate)
      assert( defineServiceChargeRateForBill(List(coldDrink, hotDrink, coldFood)) == ColdFoodRate)
    }

    withClue("only hot food items on the bill") {
      assert( defineServiceChargeRateForBill(List(hotFood)) == HotFoodRate)
      assert( defineServiceChargeRateForBill(List(hotFood, hotFood)) == HotFoodRate)
    }

    withClue("only hot food and drink items on the bill") {
      assert( defineServiceChargeRateForBill(List(coldDrink, hotFood)) == HotFoodRate)
      assert( defineServiceChargeRateForBill(List(hotDrink, hotFood)) == HotFoodRate)
      assert( defineServiceChargeRateForBill(List(coldDrink, hotDrink, hotFood)) == HotFoodRate)
    }

    withClue("hot and cold food plus drink items on the bill") {
      assert( defineServiceChargeRateForBill(List(coldDrink, coldFood, hotFood)) == HotFoodRate)
      assert( defineServiceChargeRateForBill(List(hotDrink, coldFood, hotFood)) == HotFoodRate)
      assert( defineServiceChargeRateForBill(List(coldDrink, hotDrink, coldFood, hotFood)) == HotFoodRate)
    }

    withClue("order of items on the bill does not affect the service charge applied") {
      assert( defineServiceChargeRateForBill(List(coldDrink, coldFood, hotFood)) == defineServiceChargeRateForBill(List(hotFood, coldDrink, coldFood)))
      assert( defineServiceChargeRateForBill(List(hotFood, coldDrink, coldFood)) == defineServiceChargeRateForBill(List(coldFood, hotFood, coldDrink)))
      assert( defineServiceChargeRateForBill(List(coldFood, hotFood, coldDrink)) == defineServiceChargeRateForBill(List(coldDrink, coldFood, hotFood)))
    }
  }

  test("calculateServiceChargeAmount correctly calculates the service amount to add to a bill") {
    withClue("should not accept negative values") {
      intercept[AssertionError] { calculateServiceChargeAmount(-1, 0d) }
      intercept[AssertionError] { calculateServiceChargeAmount(0, -1d) }
      intercept[AssertionError] { calculateServiceChargeAmount(-1, -1d) }
    }

    withClue("calculate service charge on a zero total and/or zero service rate"){
      assert( calculateServiceChargeAmount(0, 0d) == 0)
      assert( calculateServiceChargeAmount(10, 0d) == 0)
      assert( calculateServiceChargeAmount(0, 0.5d) == 0)
    }

    withClue("calculate service charge on a non-zero total and a non-zero service rate"){
      assert( calculateServiceChargeAmount(100, 0.1d) == 10)
      assert( calculateServiceChargeAmount(100, 0.2d) == 20)
    }

    withClue("service charge should round up to the nearest penny"){
      assert( calculateServiceChargeAmount(100, 0.234d) == 23)
      assert( calculateServiceChargeAmount(100, 0.235d) == 24)
      assert( calculateServiceChargeAmount(100, 0.236d) == 24)
    }
  }

  test("boundServiceCharge correctly restricts the upper bounds of a service charge") {
    withClue("no upper bounds set"){
      assert( boundServiceCharge(0, None) == 0)
      assert( boundServiceCharge(20, None) == 20)
      assert( boundServiceCharge(30, None) == 30)
    }

    withClue("upper bounds set"){
      assert( boundServiceCharge(0, Some(20)) == 0)
      assert( boundServiceCharge(19, Some(20)) == 19)
      assert( boundServiceCharge(20, Some(20)) == 20)
      assert( boundServiceCharge(21, Some(20)) == 20)
    }
  }
}
