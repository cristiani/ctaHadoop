package org.medbravo.hadoop;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.hadoop.io.Writable;

/**
 * Representation of ECOG Performance status with inclusion/exclusion type. 
 * @author cristian ionitoiu
 */
public class EcogPerformanceStatus implements Writable {
    private int grade;       //ecog level
    private String status;   //ecog desciptive status
    //the type records the section of the eligibility where this object was extracted from
    private String type;     //inclusion or exclusion
    public static final String ECOG0 = "Fully active, able to carry on all pre-disease performance without restriction.";
    public static final String ECOG1 = "Restricted in physicall strenuous activity but ambulatory and able to carry out work of a light or sedentary nature, e.g., light house work, office work.";
    public static final String ECOG2 = "Ambulatory and capable of all selfcare but unable to carry out any work activities; up and about more than 50% of walking hours.";
    public static final String ECOG3 = "Capable of only limited selfcare; confined to bed or chair more than 50% of waking hours";
    public static final String ECOG4 = "Completely disabled; cannot carry on any selfcare; totally confined to bed or chair";
    public static final String ECOG5 = "Dead";

    public EcogPerformanceStatus(int grade, String status, String type) {
        this.grade = grade;
        this.status = status;
        this.type = type;
    }
    
    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public void write(DataOutput d) throws IOException {
        d.writeInt(grade);
        d.writeChars(status);
        d.writeChars(type);
    }

    @Override
    public void readFields(DataInput di) throws IOException {
        grade = di.readInt();
        status = di.readUTF();
        type = di.readUTF();
    }
    
    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(grade)
            .append(status)
            .append(type)
            .toHashCode();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof EcogPerformanceStatus == false)  
        {  
            return false;  
        }  
        if (this == obj)  
        {  
            return true;  
        } 
        EcogPerformanceStatus e = (EcogPerformanceStatus)obj;
        return new EqualsBuilder()
            .append(this.grade, e.getGrade())
            .append(this.status, e.getStatus())
            .append(this.type, e.getType())
            .isEquals();
    }
    
    public static EcogPerformanceStatus createECOGByLevel(int level, String type) {
        switch (level) {
            case 0 : return new EcogPerformanceStatus(0, EcogPerformanceStatus.ECOG0, type);
            case 1 : return new EcogPerformanceStatus(1, EcogPerformanceStatus.ECOG1, type);
            case 2 : return new EcogPerformanceStatus(2, EcogPerformanceStatus.ECOG2, type);
            case 3 : return new EcogPerformanceStatus(3, EcogPerformanceStatus.ECOG3, type);
            case 4 : return new EcogPerformanceStatus(4, EcogPerformanceStatus.ECOG4, type);
            case 5 : return new EcogPerformanceStatus(5, EcogPerformanceStatus.ECOG5, type);
        }
        
        return null;
    }
}
