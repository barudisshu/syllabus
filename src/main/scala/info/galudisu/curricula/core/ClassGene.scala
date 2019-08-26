package info.galudisu.curricula.core

import java.util.StringTokenizer

import info.galudisu.curricula.GlobalLimit._
import org.jgap._

class ClassGene(conf: Configuration) extends CompareImpl(conf) {
  import ClassGene._

  private var _classId: Int     = _
  private var _classNumber: Int = _
  private var _classSize: Int   = _

  // Constructor
  @throws(classOf[InvalidConfigurationException])
  def this(conf: Configuration, classNumber: Int) = {
    this(conf)
    try {
      _classNumber = classNumber
      _classId = allClassIds(_classNumber)
      _classSize = allClassSize(_classNumber)
    } catch {
      case e: Throwable => throw new InvalidConfigurationException(e.getMessage)
    }
  }

  override protected def getIdentifyId: Integer = _classNumber

  @throws(classOf[InvalidConfigurationException])
  override def newGeneInternal(): Gene = {
    new ClassGene(getConfiguration, maxClassNumber - 1)
  }

  override def setAllele(a_newValue: scala.Any): Unit = {
    _classNumber = a_newValue.asInstanceOf[Int]
    _classId = allClassIds(_classNumber)
    _classSize = allClassSize(_classNumber)
  }

  override def getAllele: Integer = classNumber

  override def setToRandomValue(a_numberGenerator: RandomGenerator): Unit = {
    _classNumber = a_numberGenerator.nextInt(maxClassNumber)
    _classId = allClassIds(_classNumber)
    _classSize = allClassSize(_classNumber)
  }

  @throws(classOf[UnsupportedRepresentationException])
  override def getPersistentRepresentation: String =
    maxClassNumber.toString + TOKEN_SEPARATOR + _classNumber.toString

  @throws(classOf[UnsupportedRepresentationException])
  @throws(classOf[UnsupportedOperationException])
  override def setValueFromPersistentRepresentation(a_representation: String): Unit = {
    val tokenizer = new StringTokenizer(a_representation, TOKEN_SEPARATOR)
    if (tokenizer.countTokens() != 2)
      throw new UnsupportedRepresentationException("Unknown representation format: Two tokens expected!")
    try {
      maxClassNumber = Int.unbox(tokenizer.nextToken())
      _classNumber = Int.unbox(tokenizer.nextToken())
    } catch {
      case _: ClassCastException =>
        throw new UnsupportedRepresentationException("Unknown representation format: Expecting integer values!")
    }
  }

  override def applyMutation(a_index: Int, a_percentage: Double): Unit = {
    setAllele(getConfiguration.getRandomGenerator.nextInt(maxClassNumber))
  }

  def classId: Int                     = _classId
  def classId_=(newClassId: Int): Unit = _classId = newClassId

  def classNumber: Int                         = _classNumber
  def classNumber_=(newClassNumber: Int): Unit = _classNumber = newClassNumber

  def classSize: Int                       = _classSize
  def classSize_=(newClassSize: Int): Unit = _classSize = newClassSize

}

object ClassGene {

  protected var maxClassNumber: Int      = MAX_NUMBER_OF_CLASSES
  protected var allClassIds: Array[Int]  = Array.ofDim[Int](MAX_NUMBER_OF_CLASSES)
  protected var allClassSize: Array[Int] = Array.ofDim[Int](100)

  protected val TOKEN_SEPARATOR: String = ":"

  def apply(conf: Configuration, classNumber: Int): ClassGene = new ClassGene(conf, classNumber)

  def setAllClassSize(a_inputClassSize: Int, a_index: Int): Unit =
    allClassSize.update(a_index, a_inputClassSize)

  def setAllClassIds(a_classId: Int, a_index: Int): Unit =
    allClassIds.update(a_index, a_classId)

}
