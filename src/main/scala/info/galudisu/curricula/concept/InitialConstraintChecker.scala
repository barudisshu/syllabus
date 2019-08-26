package info.galudisu.curricula.concept

import org.jgap.{Gene, IChromosome, IGeneConstraintChecker}

class InitialConstraintChecker extends IGeneConstraintChecker {
  private var i: Int = 0
  // todo: Gene checking
  override def verify(a_gene: Gene, a_alleleValue: scala.Any, a_chromosome: IChromosome, a_geneIndex: Int): Boolean = {
    i += 1
    true
  }
}

object InitialConstraintChecker {
  def apply(): InitialConstraintChecker = new InitialConstraintChecker()
}
