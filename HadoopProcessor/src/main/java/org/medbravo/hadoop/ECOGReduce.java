package org.medbravo.hadoop;

import java.io.IOException;
import java.util.Iterator;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;

/**
 *
 * @author cristian ionitoiu
 */
public class ECOGReduce extends MapReduceBase implements Reducer<Text, EPFSet, Text, EPFSet> {

    @Override
    public void reduce(Text k2, Iterator<EPFSet> itrtr, OutputCollector<Text, EPFSet> oc, Reporter rprtr) throws IOException {
        EPFSet collectingSet = new EPFSet(); //starting empty set
        //we keep the inclusion ECOGs and eliminate the exclusion ECOGs
        while (itrtr.hasNext()) {
            EPFSet currentSet = itrtr.next();
            for (EcogPerformanceStatus record : currentSet.getEpfRecords()) {
                if ("I".equals(record.getType())) {
                    collectingSet.add(record);
                } else {
                    //exclude any Inclusion record with same ecog level
                    record.setType("I");
                    collectingSet.exclude(record);
                }
            }
        }
        
        oc.collect(k2, collectingSet);
    }
    
}
