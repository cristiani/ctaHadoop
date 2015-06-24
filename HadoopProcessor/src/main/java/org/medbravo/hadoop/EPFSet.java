package org.medbravo.hadoop;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.apache.hadoop.io.ArrayWritable;
import org.apache.hadoop.io.Writable;


/**
 * A list of ECOG performance statuses.
 * @author cristian ionitoiu
 */
public class EPFSet implements Writable {
    Set<EcogPerformanceStatus> epfRecords;
    
    public EPFSet() {
        epfRecords = new HashSet<>();
    }
    
    public EPFSet(EcogPerformanceStatus e) {
        this();
        epfRecords.add(e);
    }
    
    public EPFSet(Set<EcogPerformanceStatus> s) {
        this();
        epfRecords.addAll(s);
    }
    
    public void add (EcogPerformanceStatus e) {
        epfRecords.add(e);
    }
    
    public void exclude(EcogPerformanceStatus e) {
        if (epfRecords.contains(e)) {
            epfRecords.remove(e);
        }
    }

    public Set<EcogPerformanceStatus> getEpfRecords() {
        return epfRecords;
    }

    public void setEpfRecords(Set<EcogPerformanceStatus> epfRecords) {
        this.epfRecords = epfRecords;
    }
    
    @Override
    public void write(DataOutput d) throws IOException {
        if (epfRecords != null && epfRecords.size() > 0) {
            ArrayWritable aw = new ArrayWritable(EcogPerformanceStatus.class, epfRecords.toArray(new EcogPerformanceStatus[0]));
            aw.write(d);
        }
    }

    @Override
    public void readFields(DataInput di) throws IOException {
        ArrayWritable aw = new ArrayWritable(EcogPerformanceStatus.class);
        aw.readFields(di);
        EcogPerformanceStatus[] ecogs = (EcogPerformanceStatus[])aw.get();
        epfRecords = new HashSet<>(Arrays.asList(ecogs));
    }
    
}
