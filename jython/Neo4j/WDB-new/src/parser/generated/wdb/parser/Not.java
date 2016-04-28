/* Generated By:JJTree: Do not edit this line. Not.java */

package wdb.parser;

import java.util.ArrayList;

import wdb.SleepyCatDataAdapter;
import wdb.metadata.IndexSelectResult;
import wdb.metadata.WDBObject;

public class Not extends SimpleNode {
  public Not(int id) {
    super(id);
  }

  public Not(QueryParser p, int id) {
    super(p, id);
  }
  public IndexSelectResult filterObjectsWithIndexes(SleepyCatDataAdapter da, ArrayList indexes) throws Exception
  {
	  IndexSelectResult isr = new IndexSelectResult();
	  //These conditions are not supported so return a "scan" or "can't help" result
	  return isr;
  }
  public boolean eval(SleepyCatDataAdapter da, WDBObject wdbO) throws Exception
  {
  	SimpleNode n = (SimpleNode)children[0];
  	return !n.eval(da, wdbO);
  }

}
