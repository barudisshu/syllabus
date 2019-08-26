package info.galudisu.curricula.io

import info.galudisu.curricula.GlobalLimit
import org.scalatest.FunSuite

class InputDataTest extends FunSuite {

  test("read file") {
    InputData.readFromFile("./inputTimetable.xml")
    println(GlobalLimit.MAX_NUMBER_OF_TEACHERS)
  }
}
