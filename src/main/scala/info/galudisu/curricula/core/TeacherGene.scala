package info.galudisu.curricula.core

import java.util.StringTokenizer

import info.galudisu.curricula.GlobalLimit._
import org.jgap._

class TeacherGene(conf: Configuration) extends CompareImpl(conf) {
  import TeacherGene._

  private var _teacherId: Int                 = _
  private var _teacherName: String            = _
  private var _teacherNumber: Int             = _
  private var _availableLessons: Array[Int]   = Array.ofDim(MAX_NUMBER_OF_LESSONS)
  private var _availableTimeSlots: Array[Int] = Array.ofDim(40)

  @throws(classOf[InvalidConfigurationException])
  def this(conf: Configuration, teacherNumber: Int) = {
    this(conf)
    try {
      _teacherNumber = teacherNumber
      _teacherId = allTeacherIds(_teacherNumber)
      _teacherName = allTeacherNames(_teacherNumber)
      _availableLessons = allAvailableLessons(_teacherNumber)
      _availableTimeSlots = allAvailableTimeSlots(_teacherNumber)
    } catch {
      case e: Throwable => throw new InvalidConfigurationException(e.getMessage)
    }
  }

  override protected def getIdentifyId: Integer = _teacherNumber

  override def newGeneInternal(): Gene = {
    new TeacherGene(getConfiguration, maxTeacherNumber - 1)
  }

  override def setAllele(a_newValue: scala.Any): Unit = {
    _teacherNumber = a_newValue.asInstanceOf[Int]
    _teacherId = allTeacherIds(_teacherNumber)
    _teacherName = allTeacherNames(_teacherNumber)
    _availableLessons = allAvailableLessons(_teacherNumber)
    _availableTimeSlots = allAvailableTimeSlots(_teacherNumber)
  }

  override def getAllele: Integer = _teacherNumber

  override def getPersistentRepresentation: String =
    maxTeacherNumber.toString + TOKEN_SEPARATOR + teacherNumber.toString

  @throws(classOf[UnsupportedRepresentationException])
  @throws(classOf[UnsupportedOperationException])
  override def setValueFromPersistentRepresentation(a_representation: String): Unit = {
    val tokenizer = new StringTokenizer(a_representation, TOKEN_SEPARATOR)
    if (tokenizer.countTokens() != 2)
      throw new UnsupportedRepresentationException("Unknown representation format: Two tokens expected!")
    try {
      maxTeacherNumber = Int.unbox(tokenizer.nextToken())
      _teacherNumber = Int.unbox(tokenizer.nextToken())
    } catch {
      case _: ClassCastException =>
        throw new UnsupportedRepresentationException("Unknown representation format: Expecting integer values!")
    }
  }

  override def setToRandomValue(a_numberGenerator: RandomGenerator): Unit = {
    _teacherNumber = a_numberGenerator.nextInt(maxTeacherNumber)
    _teacherId = allTeacherIds(_teacherNumber)
    _teacherName = allTeacherNames(_teacherNumber)
    _availableLessons = allAvailableLessons(_teacherNumber)
    _availableTimeSlots = allAvailableTimeSlots(_teacherNumber)
  }

  override def applyMutation(a_index: Int, a_percentage: Double): Unit = {
    setAllele(getConfiguration.getRandomGenerator.nextInt(maxTeacherNumber))
  }

  def teacherId: Int                       = _teacherId
  def teacherId_=(newTeacherId: Int): Unit = _teacherId = newTeacherId

  def teacherName: String                         = _teacherName
  def teacherName_=(newTeacherName: String): Unit = _teacherName = newTeacherName

  def teacherNumber: Int                           = _teacherNumber
  def teacherNumber_=(newTeacherNumber: Int): Unit = _teacherNumber = newTeacherNumber

  def availableLessons: Array[Int]                              = _availableLessons
  def availableLessons_=(newAvailableLessons: Array[Int]): Unit = _availableLessons = newAvailableLessons

  def availableTimeSlots: Array[Int]                                = _availableTimeSlots
  def availableTimeSlots_=(newAvailableTimeSlots: Array[Int]): Unit = _availableTimeSlots = newAvailableTimeSlots

}

object TeacherGene {
  protected var maxTeacherNumber: Int          = MAX_NUMBER_OF_TEACHERS
  protected var allTeacherIds: Array[Int]      = Array.ofDim[Int](MAX_NUMBER_OF_TEACHERS)
  protected var allTeacherNames: Array[String] = Array.ofDim[String](MAX_NUMBER_OF_TEACHERS)

  /** 该教师能教什么课程，例如：李四既能教语文，又能教数学 */
  protected var allAvailableLessons: Array[Array[Int]] = Array.ofDim[Int](MAX_NUMBER_OF_TEACHERS, MAX_NUMBER_OF_LESSONS)

  /** 该教师在哪个时间段上课 */
  protected var allAvailableTimeSlots: Array[Array[Int]] = Array.ofDim[Int](MAX_NUMBER_OF_TEACHERS, 40)

  protected val TOKEN_SEPARATOR: String = ":"

  def apply(conf: Configuration, teacherNumber: Int): TeacherGene = new TeacherGene(conf, teacherNumber)

  def setAllAvailableLessons(a_availableLessons: Array[Int], a_index: Int): Unit =
    allAvailableLessons.update(a_index, a_availableLessons)

  def setAllAvailableTimeSlots(a_availableTimeSlots: Array[Int], a_index: Int): Unit =
    allAvailableTimeSlots.update(a_index, a_availableTimeSlots)

  def setAllTeacherIds(a_teacherId: Int, a_index: Int): Unit =
    allTeacherIds.update(a_index, a_teacherId)

  def setAllTeacherNames(a_teacherName: String, a_index: Int): Unit =
    allTeacherNames.update(a_index, a_teacherName)
}
