package info.galudisu.curricula.core

import java.util.StringTokenizer

import info.galudisu.curricula.GlobalLimit._
import org.jgap._

class GroupGene(conf: Configuration) extends CompareImpl(conf) {
  import GroupGene._

  private var _groupId: Int          = _
  private var _groupName: String     = _
  private var _groupNumber: Int      = _
  private var _groupSize: Int        = _
  private var _lessonIds: Array[Int] = Array.ofDim(MAX_NUMBER_OF_LESSONS)
  private var _timeSlots: Array[Int] = Array.ofDim(40)

  @throws(classOf[InvalidConfigurationException])
  def this(conf: Configuration, groupNumber: Int) = {
    this(conf)
    try {
      _groupNumber = groupNumber
      _groupId = allGroupIds(_groupNumber)
      _groupName = allGroupNames(_groupNumber)
      _groupSize = allGroupSize(_groupNumber)
      _lessonIds = allLessonIds(_groupNumber)
      _timeSlots = allTimeSlots(_groupNumber)
    } catch {
      case e: Throwable => throw new InvalidConfigurationException(e.getMessage)
    }
  }

  override protected def getIdentifyId: Integer = _groupNumber

  override def newGeneInternal(): Gene =
    new GroupGene(getConfiguration, maxGroupNumber - 1)

  override def setAllele(a_newValue: scala.Any): Unit = {
    _groupNumber = a_newValue.asInstanceOf[Int]
    _groupId = allGroupIds(_groupNumber)
    _groupName = allGroupNames(_groupNumber)
    _groupSize = allGroupSize(_groupNumber)
    _lessonIds = allLessonIds(_groupNumber)
    _timeSlots = allTimeSlots(_groupNumber)
  }

  override def getAllele: Integer = _groupNumber

  override def setToRandomValue(a_numberGenerator: RandomGenerator): Unit = {
    _groupNumber = a_numberGenerator.nextInt(maxGroupNumber)
    _groupId = allGroupIds(_groupNumber)
    _groupName = allGroupNames(_groupNumber)
    _groupSize = allGroupSize(_groupNumber)
    _lessonIds = allLessonIds(_groupNumber)
    _timeSlots = allTimeSlots(_groupNumber)
  }

  override def getPersistentRepresentation: String =
    maxGroupNumber.toString + TOKEN_SEPARATOR + groupNumber.toString

  @throws(classOf[UnsupportedRepresentationException])
  @throws(classOf[UnsupportedOperationException])
  override def setValueFromPersistentRepresentation(a_representation: String): Unit = {
    val tokenizer = new StringTokenizer(a_representation, TOKEN_SEPARATOR)
    if (tokenizer.countTokens() != 2)
      throw new UnsupportedRepresentationException("Unknown representation format: Two tokens expected!")
    try {
      maxGroupNumber = Int.unbox(tokenizer.nextToken())
      _groupNumber = Int.unbox(tokenizer.nextToken())
    } catch {
      case _: ClassCastException =>
        throw new UnsupportedRepresentationException("Unknown representation format: Expecting integer values!")
    }
  }

  override def applyMutation(a_index: Int, a_percentage: Double): Unit = {
    setAllele(getConfiguration.getRandomGenerator.nextInt(maxGroupNumber))
  }

  def groupId: Int                     = _groupId
  def groupId_=(newGroupId: Int): Unit = _groupId = newGroupId

  def groupName: String                       = _groupName
  def groupName_=(newGroupName: String): Unit = _groupName = newGroupName

  def groupNumber: Int                         = _groupNumber
  def groupNumber_=(newGroupNumber: Int): Unit = _groupNumber = newGroupNumber

  def groupSize: Int                       = _groupSize
  def groupSize_=(newGroupSize: Int): Unit = _groupSize = newGroupSize

  def lessonIds: Array[Int]                       = _lessonIds
  def lessonIds_=(newLessonIds: Array[Int]): Unit = _lessonIds = newLessonIds

  def timeSlots: Array[Int]                       = _timeSlots
  def timeSlots_=(newTimeSlots: Array[Int]): Unit = _timeSlots = newTimeSlots

}

object GroupGene {
  protected var maxGroupNumber: Int          = MAX_NUMBER_OF_GROUPS
  protected var allGroupIds: Array[Int]      = Array.ofDim[Int](MAX_NUMBER_OF_GROUPS)
  protected var allGroupNames: Array[String] = Array.ofDim[String](MAX_NUMBER_OF_GROUPS)
  protected var allGroupSize: Array[Int]     = Array.ofDim[Int](MAX_NUMBER_OF_GROUPS)

  protected var allLessonIds: Array[Array[Int]] = Array.ofDim[Int](MAX_NUMBER_OF_GROUPS, MAX_NUMBER_OF_LESSONS)
  protected var allTimeSlots: Array[Array[Int]] = Array.ofDim[Int](MAX_NUMBER_OF_GROUPS, MAX_NUMBER_OF_LESSONS)

  protected val TOKEN_SEPARATOR: String = ":"

  def apply(conf: Configuration, groupNumber: Int): GroupGene = new GroupGene(conf, groupNumber)

  def getMaxGroupNumber: Int = maxGroupNumber

  def getAllGroupNames: Array[String] = allGroupNames

  def setAllGroupSize(a_inputGroupSize: Int, a_index: Int): Unit = allGroupSize.update(a_index, a_inputGroupSize)

  def setAllGroupIds(a_allGroupId: Int, a_index: Int): Unit = allGroupIds.update(a_index, a_allGroupId)

  def setAllGroupNames(a_allName: String, a_index: Int): Unit = allGroupNames.update(a_index, a_allName)

  def setAllStudyPlan(a_lessons: Array[Int], a_times: Array[Int], a_index: Int): Unit = {
    for (i <- a_lessons.indices) {
      allLessonIds(a_index)(i) = a_lessons(i)
    }
    for (i <- a_times.indices) {
      allTimeSlots(a_index)(i) = a_times(i)
    }
  }
}
