package org.medbravo.hadoop;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;

/**
 * Hadoop Mapping - we analyze input files line by, parse actual content into list of ECOG objects associated to a study Id.
 * Currently we use regex parsing, but parsing could be extended to NLP processing for a more precise analysis of the English text.
 * @author cristian ionitoiu
 */
public class ECOGMap extends MapReduceBase implements Mapper<LongWritable, Text, Text, EPFSet> { 
    
    private Text getStudyId(String line) {
        String[] tokens = line.split(",");
        return new Text(tokens[0]);
    }
    
    /**
     * Main build method
     * @param ecogType
     * @param operator
     * @param ecogLevel
     * @return 
     */
    private EPFSet buildValues(String ecogType, String operator, int ecogLevel) {
        EPFSet values = new EPFSet();
        
        if (operator.contains("EQ")) {
            values.add(EcogPerformanceStatus.createECOGByLevel(ecogLevel, ecogType));
        } else  if (operator.contains("LT")) {
            for (int i = 0; i < ecogLevel; i++) {
                values.add(EcogPerformanceStatus.createECOGByLevel(ecogLevel, ecogType));
            }
        } else  if (operator.contains("LTE")) {
            for (int i = 0; i <= ecogLevel; i++) {
                values.add(EcogPerformanceStatus.createECOGByLevel(ecogLevel, ecogType));
            }
        } else  if (operator.contains("GT")) {
            for (int i = ecogLevel + 1; i <= 5; i++) {
                values.add(EcogPerformanceStatus.createECOGByLevel(ecogLevel, ecogType));
            }
        } else {
            //GTE
            for (int i = ecogLevel; i <= 5; i++) {
                values.add(EcogPerformanceStatus.createECOGByLevel(ecogLevel, ecogType));
            }
        }
        
        return values;
    }
    
    /**
     * Main parsing method.
     * @param line
     * @return 
     */
    private EPFSet parseForEcogs(String line) {
        EPFSet values = null;
        Properties prop = new Properties();
        
        String[] tokens = line.split(",");
        InputStream in = getClass().getClassLoader().getResourceAsStream("regex.properties");
        try {
            if (in != null) {
                prop.load(in);
            }
            Set<Object> keys = prop.keySet();
            Iterator<Object> keyIterator = keys.iterator();
            while (keyIterator.hasNext()) {
                String currentKey = (String)keyIterator.next();
                String regex = prop.getProperty(currentKey);
                Pattern p = Pattern.compile(regex);
                Matcher m = p.matcher(tokens[2]);
                if (m.find()) {
                    int ecogLevel = Integer.parseInt(m.group(3));
                    values = buildValues(tokens[1], currentKey, ecogLevel);
                    break;
                }
            }
        } catch (IOException ioe) {
           System.err.println(ioe.getMessage()); 
        }
        
        return values;
    }
    
    @Override
    public void map(LongWritable k1, Text v1, OutputCollector<Text, EPFSet> oc, Reporter rprtr) throws IOException {
         String line = v1.toString();
         oc.collect(getStudyId(line), parseForEcogs(line));
    }
    
}
