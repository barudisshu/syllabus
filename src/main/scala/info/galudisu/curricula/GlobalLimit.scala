package info.galudisu.curricula

object GlobalLimit {

  val GROUP: Int      = 0
  val CLASS: Int      = 1
  val TEACHER: Int    = 2
  val LESSON: Int     = 3
  val TIME: Int       = 4
  val SUBGROUP: Int   = 1
  val SUPERGROUP: Int = 2

  var MAX_EVOLUTIONS: Int               = 1000
  var POPULATION_SIZE: Int              = 50
  var THRESHOLD: Double                 = 1
  var CHROMOSOME_SIZE: Int              = 4
  var MAX_NUMBER_OF_GROUPS: Int         = 2
  var MAX_NUMBER_OF_LESSONS: Int        = 3
  var MAX_NUMBER_OF_TEACHERS: Int       = 3
  var MAX_NUMBER_OF_CLASSES: Int        = 4
  var MAX_NUMBER_OF_TIME_SLOT: Int      = 3
  private[curricula] var START_T: Long  = 0
  private[curricula] var FINISH_T: Long = 0

  private[curricula] val GENOTYPE_FILENAME: String        = "./population.xml"
  private[curricula] val BEST_CHROMOSOME_FILENAME: String = "./best_chromosome.xml"
  private[curricula] val XML_TEST_FILENAME: String        = "./inputTimetable.xml"

}
