package info.galudisu.curricula

import java.io.IOException

import info.galudisu.curricula.GlobalLimit._
import info.galudisu.curricula.concept._
import info.galudisu.curricula.core._
import info.galudisu.curricula.io._
import info.galudisu.curricula.concept.{CurriculaFitnessFunction, CurriculaMutationOperator, GroupClassTeacherLessonTimeSG, InitialConstraintChecker}
import info.galudisu.curricula.core.{ClassGene, GroupGene, LessonGene, TeacherGene, TimeGene}
import info.galudisu.curricula.io.{InputData, OutputData}
import org.jgap._
import org.jgap.event.EventManager
import org.jgap.impl._

import scala.util.control.Breaks

object Boot {

  def main(args: Array[String]): Unit = {

    try {
      InputData.readFromFile(XML_TEST_FILENAME)
    } catch {
      case e: Throwable => throw new IOException(s"XML phrase error: ${e.printStackTrace()}")
    }

    val conf: Configuration = new Configuration("1", "myconf")
    val fitnessFunction = new CurriculaFitnessFunction()
    val timetableConstraintChecker = InitialConstraintChecker()

    // Creating genes
    val testGenes: Array[Gene] = Array.ofDim(CHROMOSOME_SIZE)

    for (i <- Range(0, CHROMOSOME_SIZE)) {
      testGenes(i) = GroupClassTeacherLessonTimeSG(conf,
        Array(new GroupGene(conf, 1),
          new ClassGene(conf, 1),
          new TeacherGene(conf, 1),
          new LessonGene(conf, 1),
          new TimeGene(conf, 1)))
    }

    println("===========================================================")
    val testChromosome = new Chromosome(conf, testGenes)
    testChromosome.setConstraintChecker(timetableConstraintChecker)
    // Setup configuration
    conf.setSampleChromosome(testChromosome)
    conf.setPopulationSize(POPULATION_SIZE)
    conf.setFitnessFunction(fitnessFunction)

    val myBestChromosomesSelector = new ThresholdSelector(conf, 0.75)
    conf.addNaturalSelector(myBestChromosomesSelector, false)

    conf.setRandomGenerator(new StockRandomGenerator())
    conf.setEventManager(new EventManager())
    conf.setFitnessEvaluator(new DefaultFitnessEvaluator())

    val myCrossoverOperator = new CrossoverOperator(conf)
    conf.addGeneticOperator(myCrossoverOperator)

    val myTwoWayMutationOperator = new TwoWayMutationOperator(conf)
    conf.addGeneticOperator(myTwoWayMutationOperator)

    val myMutationOperator = CurriculaMutationOperator(conf)
    conf.addGeneticOperator(myMutationOperator)

    conf.setKeepPopulationSizeConstant(false)

    // Creating genotype
    val population = Genotype.randomInitialGenotype(conf)
    println(s"Our Chromosome: \n ${testChromosome.getConfiguration.toString}")

    println("========================evolution==========================")

    // Begin evolution
    val start_t = System.currentTimeMillis(): Double
    Breaks.breakable {
      for (i <- Range(0, MAX_EVOLUTIONS)) {
        println(s"generation#: %-4d population size: %-2d   fitness: ${Console.RED}%.6f${Console.RESET}".format(i, population.getPopulation.size(), population.getFittestChromosome.getFitnessValue))

        if (population.getFittestChromosome.getFitnessValue >= THRESHOLD)
          Breaks.break()
        population.evolve()
      }
    }

    val finish_t = System.currentTimeMillis(): Double

    println("===================end of evolution========================")

    val fittestChromosome = population.getFittestChromosome.asInstanceOf[Chromosome]
    println(s"===========The best chromosome---fitness=[${Console.RED}%.2f${Console.RESET}]============".format(fittestChromosome.getFitnessValue))
    println(s"                ${Console.YELLOW}Group${Console.RESET}  ${Console.YELLOW}Class${Console.RESET}  ${Console.YELLOW}Teacher${Console.RESET} ${Console.YELLOW}Lesson${Console.RESET}   ${Console.YELLOW}Time${Console.RESET}")


    for (i <- Range(0, CHROMOSOME_SIZE)) {
      val sg = fittestChromosome.getGene(i).asInstanceOf[GroupClassTeacherLessonTimeSG]

      val lg = sg.geneAt(LESSON).asInstanceOf[LessonGene]
      val tg = sg.geneAt(TEACHER).asInstanceOf[TeacherGene]
      val cg = sg.geneAt(CLASS).asInstanceOf[ClassGene]
      val gg = sg.geneAt(GROUP).asInstanceOf[GroupGene]

      println(s"Gene $i contains: ${Console.CYAN_B}${gg.groupId}${Console.RESET}\t\t${Console.CYAN_B}${cg.classId}${Console.RESET}\t\t${Console.CYAN_B}${tg.teacherId}${Console.RESET}\t\t${Console.CYAN_B}${lg.lessonId}${Console.RESET}\t\t${Console.CYAN_B}${sg.geneAt(TIME).getAllele}${Console.RESET}")
    }

    println(s"Elapsed time: ${(finish_t - start_t) / 1000}s")

    OutputData.printToConsole(fittestChromosome)

    // reset configuration

    Configuration.reset("1")
//    timetable.clean()
  }

}



































