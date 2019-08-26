package info.galudisu.curricula.concept

import info.galudisu.curricula.core._
import info.galudisu.curricula.core.{ClassGene, GroupGene, LessonGene, TeacherGene, TimeGene}
import org.jgap.Configuration
import org.scalatest.FunSuite

class GroupClassTeacherLessonTimeSGTest extends FunSuite {

  test("testApply") {
    val conf = new Configuration("1", "myconf")
    val SG = GroupClassTeacherLessonTimeSG(conf,
                                           Array(new GroupGene(conf, 1),
                                                 new ClassGene(conf, 3),
                                                 new TeacherGene(conf, 2),
                                                 new LessonGene(conf, 1),
                                                 new TimeGene(conf, 2)))
    println(SG.getPersistentRepresentation)
  }

}
