package datamining;
import java.util.*;
import representation.*;


public interface ItemsetMiner {

    public BooleanDatabase getDatabase();
    public Set<Itemset> extract(float frequence);

}