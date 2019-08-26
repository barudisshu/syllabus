package info.galudisu.curricula.core

import java.util.StringTokenizer

import info.galudisu.curricula.GlobalLimit._
import org.jgap._

class LessonGene(conf: Configuration) extends CompareImpl(conf) {
  import LessonGene._

  private var _lessonId: Int      = _
  private var _lessonName: String = _
  private var _lessonGroup: Int   = _
  private var _lessonNumber: Int  = _

  @throws(classOf[InvalidConfigurationException])
  def this(conf: Configuration, lessonNumber: Int) = {
    this(conf)
    try {
      _lessonNumber = lessonNumber
      _lessonId = allLessonIds(_lessonNumber)
      _lessonName = allLessonNames(_lessonNumber)
      _lessonGroup = allLessonGroups(_lessonNumber)
    } catch {
      case e: Throwable => throw new InvalidConfigurationException(e.getMessage)
    }
  }

  override protected def getIdentifyId: Integer = _lessonNumber

  @throws(classOf[InvalidConfigurationException])
  override def newGeneInternal(): Gene =
    new LessonGene(getConfiguration, maxLessonNumber - 1)

  override def setAllele(a_newValue: scala.Any): Unit = {
    _lessonNumber = a_newValue.asInstanceOf[Int]
    _lessonId = allLessonIds(_lessonNumber)
    _lessonName = allLessonNames(_lessonNumber)
    _lessonGroup = allLessonGroups(_lessonNumber)
  }

  override def getAllele: Integer = lessonNumber

  @throws(classOf[UnsupportedOperationException])
  override def getPersistentRepresentation: String =
    maxLessonNumber.toString + TOKEN_SEPARATOR + lessonId.toString

  @throws(classOf[UnsupportedOperationException])
  @throws(classOf[UnsupportedRepresentationException])
  override def setValueFromPersistentRepresentation(a_representation: String): Unit = {
    val tokenizer = new StringTokenizer(a_representation, TOKEN_SEPARATOR)
    if (tokenizer.countTokens() != 2)
      throw new UnsupportedRepresentationException("Unknown representation format: Two tokens expected!")
    try {
      maxLessonNumber = Int.unbox(tokenizer.nextToken())
      _lessonId = Int.unbox(tokenizer.nextToken())
    } catch {
      case _: ClassCastException =>
        throw new UnsupportedRepresentationException("Unknown representation format: Expecting integer values!")
    }
  }

  override def setToRandomValue(a_numberGenerator: RandomGenerator): Unit = {
    _lessonNumber = a_numberGenerator.nextInt(maxLessonNumber)
    _lessonId = allLessonIds(_lessonNumber)
    _lessonName = allLessonNames(_lessonNumber)
    _lessonGroup = allLessonGroups(_lessonNumber)
  }

  override def applyMutation(a_index: Int, a_percentage: Double): Unit = {
    setAllele(getConfiguration.getRandomGenerator.nextInt(maxLessonNumber))
  }

  def lessonId: Int                      = _lessonId
  def lessonId_=(newLessonId: Int): Unit = _lessonId = newLessonId

  def lessonName: String                        = _lessonName
  def lessonName_=(newLessonName: String): Unit = _lessonName = newLessonName

  def lessonGroup: Int                         = _lessonGroup
  def lessonGroup_=(newLessonGroup: Int): Unit = _lessonGroup = newLessonGroup

  def lessonNumber: Int                          = _lessonNumber
  def lessonNumber_=(newLessonNumber: Int): Unit = _lessonNumber = newLessonNumber

}

object LessonGene {
  protected var maxLessonNumber: Int          = MAX_NUMBER_OF_LESSONS
  protected var allLessonIds: Array[Int]      = Array.ofDim[Int](MAX_NUMBER_OF_LESSONS)
  protected var allLessonNames: Array[String] = Array.ofDim[String](MAX_NUMBER_OF_LESSONS)
  protected var allLessonGroups: Array[Int]   = Array.ofDim[Int](MAX_NUMBER_OF_LESSONS)
  protected var allLessonSize: Array[Int]     = Array.ofDim[Int](100)

  protected val TOKEN_SEPARATOR: String = ":"

  def apply(conf: Configuration, lessonNumber: Int): LessonGene = new LessonGene(conf, lessonNumber)

  def setMaxLessonNumber(a_maxLesson: Int): Unit = maxLessonNumber = a_maxLesson

  def getMaxLessonNumber: Int = maxLessonNumber

  def setAllLessonIds(a_lessonId: Int, a_index: Int): Unit = allLessonIds.update(a_index, a_lessonId)

  def getAllLessonIds: Array[Int] = allLessonIds

  def setAllLessonNames(a_lessonName: String, a_index: Int): Unit = allLessonNames.update(a_index, a_lessonName)

  def getAllLessonNames: Array[String] = allLessonNames

  def setLessonGroups(a_group: Int, a_index: Int): Unit = allLessonGroups.update(a_index, a_group)
}
