package info.galudisu.curricula.io

import info.galudisu.curricula.concept.GroupClassTeacherLessonTimeSG
import org.jgap.Chromosome
import info.galudisu.curricula.GlobalLimit._
import info.galudisu.curricula.core._
import info.galudisu.curricula.concept.GroupClassTeacherLessonTimeSG
import info.galudisu.curricula.core.{ClassGene, GroupGene, LessonGene, TeacherGene, TimeGene}
import org.apache.commons.lang.StringUtils

object OutputData {

  private var maxGroupId: Int = _
  private var maxTimeId: Int = _

  def printToConsole(bestChromosome: Chromosome): Unit = {
    val s: Array[GroupClassTeacherLessonTimeSG] = Array.ofDim(CHROMOSOME_SIZE)
    s(0) = bestChromosome.getGene(0).asInstanceOf[GroupClassTeacherLessonTimeSG]

    val gg = s(0).geneAt(GROUP).asInstanceOf[GroupGene]
    maxGroupId = GroupGene.getMaxGroupNumber
    maxTimeId = TimeGene.getMaxTimeSlotNumber

    var str: Array[Array[String]] = Array.ofDim[String](maxGroupId, maxTimeId)

    for (i <- 0 until maxGroupId; j <- 0 until maxTimeId) {
      str(i)(j) = StringUtils.repeat("■", 12)
    }

    for (i <- 0 until CHROMOSOME_SIZE) {
      s(i) =
        bestChromosome.getGene(i).asInstanceOf[GroupClassTeacherLessonTimeSG]
      val lg: LessonGene =
        s(i).geneAt(LESSON).asInstanceOf[LessonGene]
      val tg: TeacherGene =
        s(i).geneAt(TEACHER).asInstanceOf[TeacherGene]
      val cg: ClassGene =
        s(i).geneAt(CLASS).asInstanceOf[ClassGene]
      for (j <- 0 until maxGroupId; k <- 0 until maxTimeId if s(i)
             .geneAt(GROUP)
             .getAllele
             .asInstanceOf[Int] ==
             j &&
             s(i)
               .geneAt(TIME)
               .getAllele
               .asInstanceOf[Int] ==
               k)
        str(j)(k) = lg.lessonName + "/" + tg.teacherName + "/" + cg.classId
    }
    println("-----------------------------------------------------------")
    println("----------------[Lesson/Teacher/Class]---------------------")
    for (groupName <- GroupGene.getAllGroupNames) {
      print(s"\t\t${Console.GREEN}$groupName${Console.RESET}")
    }
    println("")
    for (i <- 0 until maxTimeId) {
      print(s"${Console.BLUE}${TimeGene.getAllTimeSlotNames(i)}${Console.RESET}  ")
      for (j <- 0 until maxGroupId) {
        print(str(j)(i) + "    ")
      }
      println("")
    }
  }
}
