package info.galudisu.curricula.concept

import info.galudisu.curricula.GlobalLimit._
import info.galudisu.curricula.core._
import info.galudisu.curricula.core.{ClassGene, GroupGene, LessonGene, TeacherGene, TimeGene}
import org.jgap._

/**
  * 避免过早收敛的解决方案：适值训练
  * 设 `f` 为当前适值, `fmax`为种群最大适值, `favg` 为种群平均适值
  *
  * 重演适值(FitnessFunction): F = (fmax - f) / (fmax - favg) * f
  */
class SyllabusFitnessFunction extends FitnessFunction {

  /** 惩罚点 */
  private var penalty: Double = _
  private var gg: GroupGene   = _
  private var cg: ClassGene   = _
  private var tg: TeacherGene = _
  private var lg: LessonGene  = _
  private var tig: TimeGene   = _

  /** 解组染色体 */
  private def extractChromosome(a_subject: IChromosome,
                                chromosomeSize: Int): Iterable[GroupClassTeacherLessonTimeSG] = {
    val range = Range(0, chromosomeSize)
    range.map { r =>
      a_subject.getGene(r).asInstanceOf[GroupClassTeacherLessonTimeSG]
    }
  }

  override def evaluate(a_subject: IChromosome): Double = {
    penalty = 0
    // Extract supergenes from chromosome
    var s: Array[GroupClassTeacherLessonTimeSG] = extractChromosome(a_subject, CHROMOSOME_SIZE).toArray

    // -----------------------Checking hard constraints---------------------------

    for (i <- Range(0, CHROMOSOME_SIZE)) {

      //------教室座位数 > 班级学生数
      cg = s(i).geneAt(CLASS).asInstanceOf[ClassGene]
      gg = s(i).geneAt(GROUP).asInstanceOf[GroupGene]
      if (cg.classSize < gg.groupSize) penalty += 10

      //-----教师仅能教他自己擅长的课程
      tg = s(i).geneAt(TEACHER).asInstanceOf[TeacherGene]
      lg = s(i).geneAt(LESSON).asInstanceOf[LessonGene]
      tig = s(i).geneAt(TIME).asInstanceOf[TimeGene]

      val availableLessons: Array[Int] = tg.availableLessons
      val idCurrentLesson: Int         = lg.lessonId

      //-----当前的课程不在可用课程列表中
      var flag = false
      for (lesson <- availableLessons) {
        if (lesson == idCurrentLesson) flag = true
      }
      if (!flag) penalty += 10

      //-----教师在特定时间上课
      val availableTimeSlots: Array[Int] = tg.availableTimeSlots
      val idCurrentTimeSlot: Int         = s(i).geneAt(TIME).getAllele.asInstanceOf[Int]

      flag = false
      for (time <- availableTimeSlots) {
        if (time == idCurrentTimeSlot) flag = true
      }
      if (!flag) penalty += 10

      //-----Checking study plan

      for (j <- Range(0, CHROMOSOME_SIZE)) {
        //-----------避免一个班的学生，同一时间不会在两个教室
        if (i != j && s(i).geneAt(GROUP) == s(j).geneAt(GROUP) && s(i).geneAt(TIME) == s(j).geneAt(TIME)) {
          penalty += 10
        } else
        //-----------同一时间一个教室，仅容许一个班级、一名老师、一门课程
        if (i != j &&
            s(i).geneAt(CLASS) == s(j).geneAt(CLASS) && // 同一班级
            s(i).geneAt(TIME) == s(j).geneAt(TIME)) { // 同一时间
          penalty += 10
        } else //-----------同一时间，一个教师不会在两个教室
        if (i != j && s(i).geneAt(TEACHER) == s(j).geneAt(TEACHER) && s(i).geneAt(TIME) == s(j).geneAt(TIME)) {
          //return 0;
          penalty += 10
        }
      }
    }

    // todo:
    // -----------------------Checking soft constraints---------------------------
    // -----------种群要求满足：一门课程至少上一节课
    // 计算方法：
    // 1. 总基因数 - 所有课程数 = 约束数
    val limit = CHROMOSOME_SIZE - LessonGene.getAllLessonIds.length
    if (limit > 0) {
      var count = 0 // 重复记录数不超过限制数[个体范围内]
      for (i <- Range(0, CHROMOSOME_SIZE)) {
        for (k <- Range(0, CHROMOSOME_SIZE)) {
          if (i != k && s(i).geneAt(LESSON) == s(k).geneAt(LESSON)) count += 1
        }
      }
      // 重复数成对出现的，除2
      if (count / 2 > limit) penalty += 10
    }

    1 / (1 + penalty)
  }
}
