package info.galudisu.curricula.io

import org.scalatest.{FunSuite, Matchers}

import scala.xml.XML

class XMLTest extends FunSuite with Matchers {

  test("read xml file") {
    val xml = XML.loadFile("./inputTimetable.xml")
    println((xml \ "@chromosome_size").text)
  }

}
