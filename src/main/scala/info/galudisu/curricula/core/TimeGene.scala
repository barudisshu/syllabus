package info.galudisu.curricula.core

import java.util.StringTokenizer

import info.galudisu.curricula.GlobalLimit._
import org.jgap._

class TimeGene(conf: Configuration) extends CompareImpl(conf) {
  import TimeGene._

  private var _timeSlotId: Int      = _
  private var _timeSlotName: String = _

  @throws(classOf[InvalidConfigurationException])
  def this(conf: Configuration, timeSlotId: Int) = {
    this(conf)
    if (timeSlotId < 0)
      throw new IllegalArgumentException("Time slot id must be non-negative!")
    _timeSlotId = timeSlotId
    _timeSlotName = allTimeSlotNames(_timeSlotId)
  }

  override protected def getIdentifyId: Integer = _timeSlotId

  override def newGeneInternal(): Gene = {
    new TimeGene(getConfiguration, maxTimeSlotNumber - 1)
  }

  override def setAllele(a_newValue: scala.Any): Unit = {
    _timeSlotId = a_newValue.asInstanceOf[Int]
    _timeSlotName = allTimeSlotNames(_timeSlotId)
  }

  override def getAllele: Integer = timeSlotId

  override def setToRandomValue(a_numberGenerator: RandomGenerator): Unit = {
    _timeSlotId = a_numberGenerator.nextInt(maxTimeSlotNumber)
    _timeSlotName = allTimeSlotNames(_timeSlotId)
  }

  @throws(classOf[UnsupportedRepresentationException])
  override def getPersistentRepresentation: String =
    maxTimeSlotNumber.toString + TOKEN_SEPARATOR + timeSlotId.toString

  @throws(classOf[UnsupportedRepresentationException])
  @throws(classOf[UnsupportedOperationException])
  override def setValueFromPersistentRepresentation(a_representation: String): Unit = {
    val tokenizer = new StringTokenizer(a_representation, TOKEN_SEPARATOR)
    if (tokenizer.countTokens() != 2)
      throw new UnsupportedRepresentationException("Unknown representation format: Two tokens expected!")
    try {
      maxTimeSlotNumber = Int.unbox(tokenizer.nextToken())
      _timeSlotId = Int.unbox(tokenizer.nextToken())
    } catch {
      case _: ClassCastException =>
        throw new UnsupportedRepresentationException("Unknown representation format: Expecting integer values!")
    }
  }

  override def applyMutation(a_index: Int, a_percentage: Double): Unit = {
    setAllele(getConfiguration.getRandomGenerator.nextInt(maxTimeSlotNumber))
  }

  def timeSlotId: Int                        = _timeSlotId
  def timeSlotId_=(newTimeSlotId: Int): Unit = _timeSlotId = newTimeSlotId

  def timeSlotName: String                          = _timeSlotName
  def timeSlotName_=(newTimeSlotName: String): Unit = _timeSlotName = newTimeSlotName

}

object TimeGene {
  protected var maxTimeSlotNumber: Int          = MAX_NUMBER_OF_TIME_SLOT
  protected var allTimeSlotNames: Array[String] = Array.ofDim[String](MAX_NUMBER_OF_TIME_SLOT)

  protected val TOKEN_SEPARATOR: String = ":"

  def apply(conf: Configuration, timeSlotId: Int): TimeGene = new TimeGene(conf, timeSlotId)

  def getMaxTimeSlotNumber: Int = maxTimeSlotNumber

  def setAllTimeSlotNames(a_timeSlotName: String, a_index: Int): Unit = allTimeSlotNames.update(a_index, a_timeSlotName)

  def getAllTimeSlotNames: Array[String] = allTimeSlotNames
}
