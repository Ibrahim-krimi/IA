package representation;
import java.lang.*;
import java.util.*;
import java.awt.im.spi.*;


public interface Constraint{
    public Set<Variable> getScope();
    public boolean isSatisfiedBy(Map<Variable,Object>verifMap);


}