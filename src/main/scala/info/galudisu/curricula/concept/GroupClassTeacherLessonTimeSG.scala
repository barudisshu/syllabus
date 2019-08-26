package info.galudisu.curricula.concept

import info.galudisu.curricula.GlobalLimit._
import org.jgap.supergenes.{AbstractSupergene, Supergene}
import org.jgap.{Configuration, Gene}

trait GroupClassTeacherLessonTimeSG extends AbstractSupergene {
  private val TOKEN_SEPARATOR: String = ":"
  private val GENE_DELIMITER: String  = "+"

  override def isValid(a_case: Array[Gene], a_forSupergene: Supergene): Boolean = true
  @throws(classOf[UnsupportedOperationException])
  override def getPersistentRepresentation: String = {
    this.geneAt(GROUP).getPersistentRepresentation +
      GENE_DELIMITER + this.geneAt(CLASS).getPersistentRepresentation +
      GENE_DELIMITER + this.geneAt(TEACHER).getPersistentRepresentation +
      GENE_DELIMITER + this.geneAt(LESSON).getPersistentRepresentation +
      GENE_DELIMITER + this.geneAt(TIME).getPersistentRepresentation
  }
}

object GroupClassTeacherLessonTimeSG {

  def apply(): GroupClassTeacherLessonTimeSG = {
    new AbstractSupergene() with GroupClassTeacherLessonTimeSG
  }

  def apply(conf: Configuration): GroupClassTeacherLessonTimeSG = {
    new AbstractSupergene(conf) with GroupClassTeacherLessonTimeSG
  }

  def apply(conf: Configuration, genes: Array[Gene]): GroupClassTeacherLessonTimeSG = {
    new AbstractSupergene(conf, genes) with GroupClassTeacherLessonTimeSG
  }
}
