package info.galudisu.curricula.concept

import java.util

import org.jgap.impl.MutationOperator
import org.jgap.{Configuration, IUniversalRateCalculator, Population}

trait SyllabusMutationOperator extends MutationOperator {
  override def operate(a_population: Population, a_candidateChromosomes: util.List[_]): Unit = {
    super.operate(a_population, a_candidateChromosomes)
  }
}

object SyllabusMutationOperator {
  def apply(conf: Configuration): SyllabusMutationOperator = new MutationOperator(conf) with SyllabusMutationOperator
  def apply(conf: Configuration, i: Int): SyllabusMutationOperator =
    new MutationOperator(conf, i) with SyllabusMutationOperator
  def apply(conf: Configuration, iUniversalRateCalculator: IUniversalRateCalculator): SyllabusMutationOperator = {
    new MutationOperator(conf, iUniversalRateCalculator) with SyllabusMutationOperator
  }
}
