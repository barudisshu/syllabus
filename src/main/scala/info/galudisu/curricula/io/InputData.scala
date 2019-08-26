package info.galudisu.curricula.io

import info.galudisu.curricula.core._
import info.galudisu.curricula.GlobalLimit
import info.galudisu.curricula.core.{ClassGene, GroupGene, LessonGene, TeacherGene, TimeGene}

import scala.xml.XML

object InputData {

  def readFromFile(fileName: String): Unit = {
    val xml = XML.loadFile(fileName)

    GlobalLimit.CHROMOSOME_SIZE = (xml \ "@chromosome_size").text.toInt
    GlobalLimit.POPULATION_SIZE = (xml \ "@population_size").text.toInt
    GlobalLimit.MAX_EVOLUTIONS = (xml \ "@max_evolution").text.toInt
    GlobalLimit.THRESHOLD = (xml \ "@threshold").text.toInt

    //----------------------------------------------------------------------------
    //-----------------------------get classGene data-----------------------------
    val classGene = xml \\ "genes" \\ "classGene"
    GlobalLimit.MAX_NUMBER_OF_CLASSES = classGene.length
    classGene.zipWithIndex.foreach { case (gene, idx) =>
      ClassGene.setAllClassIds((gene \ "@classId").text.toInt, idx)
      ClassGene.setAllClassSize((gene \ "@classSize").text.toInt, idx)
    }

    //----------------------------------------------------------------------------
    //----------------------------get lessonGene data-----------------------------
    val lessonGene = xml \\ "genes" \\ "lessonGene"
    GlobalLimit.MAX_NUMBER_OF_LESSONS = lessonGene.length
    lessonGene.zipWithIndex.foreach { case (gene, idx) =>
      LessonGene.setAllLessonIds((gene \ "@lessonId").text.toInt, idx)
      LessonGene.setAllLessonNames((gene \ "@lessonName").text.toString, idx)
    }

    //----------------------------------------------------------------------------
    //----------------------------get groupGene data-----------------------------
    val groupGene = xml \\ "genes" \\ "groupGene"
    GlobalLimit.MAX_NUMBER_OF_GROUPS = groupGene.length
    groupGene.zipWithIndex.foreach { case (gene, idx) =>
      GroupGene.setAllGroupIds((gene \ "@groupId").text.toInt, idx)
      GroupGene.setAllGroupNames((gene \ "@groupName").text.toString, idx)
      GroupGene.setAllGroupSize((gene \ "@groupSize").text.toInt, idx)
      GroupGene.setAllStudyPlan((gene \ "@lessons").text.split(" ").map(_.toInt), (gene \ "@times").text.split(" ").map(_.toInt), idx)
    }

    //----------------------------------------------------------------------------
    //----------------------------get timeGene data-----------------------------
    val timeGene = xml \\ "genes" \\ "timeGene"
    GlobalLimit.MAX_NUMBER_OF_TIME_SLOT = timeGene.length
    timeGene.zipWithIndex.foreach { case (gene, idx) =>
      TimeGene.setAllTimeSlotNames((gene \ "@timeSlotName").text, idx)
    }

    //----------------------------------------------------------------------------
    //----------------------------get teacherGene data-----------------------------
    val teacherGene = xml \\ "genes" \\ "teacherGene"
    GlobalLimit.MAX_NUMBER_OF_TEACHERS = teacherGene.length
    teacherGene.zipWithIndex.foreach { case (gene, idx) =>
      TeacherGene.setAllTeacherIds((gene \ "@teacherId").text.toInt, idx)
      TeacherGene.setAllTeacherNames((gene \ "@teacherName").text.toString, idx)
      TeacherGene.setAllAvailableLessons((gene \ "@availableLessons").text.split(" ").map(_.toInt), idx)
      TeacherGene.setAllAvailableTimeSlots((gene \ "@availableTimeSlots").text.split(" ").map(_.toInt), idx)
    }
  }
}
