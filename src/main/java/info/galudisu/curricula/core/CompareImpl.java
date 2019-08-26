package info.galudisu.curricula.core;

import org.jgap.Configuration;
import org.jgap.Gene;
import org.jgap.InvalidConfigurationException;
import org.jgap.impl.IntegerGene;

public abstract class CompareImpl extends IntegerGene implements Gene {

  public CompareImpl(Configuration a_config) throws InvalidConfigurationException {
    super(a_config);
  }

  protected abstract Integer getIdentifyId();

  @Override
  public int compareTo(Object a_otherGroupGene) {
    if (a_otherGroupGene == null)
      return 1;
    if (getIdentifyId() == null) {
      if (((CompareImpl) a_otherGroupGene).getIdentifyId() == null)
        return 0;
      else
        return -1;
    }
    return getIdentifyId().compareTo(((CompareImpl) a_otherGroupGene).getIdentifyId());
  }

  @Override
  public boolean equals(Object a_otherGroupGene) {
    return a_otherGroupGene instanceof CompareImpl && compareTo(a_otherGroupGene) == 0;
  }

  @Override
  public int hashCode() {
    return getIdentifyId();
  }

  @Override
  public Object getInternalValue() {
    return getIdentifyId();
  }
}
