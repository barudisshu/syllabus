package info.galudisu.curricula.concept

import java.util

import org.jgap.impl.MutationOperator
import org.jgap.{Configuration, IUniversalRateCalculator, Population}

trait CurriculaMutationOperator extends MutationOperator {
  override def operate(a_population: Population, a_candidateChromosomes: util.List[_]): Unit = {
    super.operate(a_population, a_candidateChromosomes)
  }
}

object CurriculaMutationOperator {
  def apply(conf: Configuration): CurriculaMutationOperator = new MutationOperator(conf) with CurriculaMutationOperator
  def apply(conf: Configuration, i: Int): CurriculaMutationOperator =
    new MutationOperator(conf, i) with CurriculaMutationOperator
  def apply(conf: Configuration, iUniversalRateCalculator: IUniversalRateCalculator): CurriculaMutationOperator = {
    new MutationOperator(conf, iUniversalRateCalculator) with CurriculaMutationOperator
  }
}
