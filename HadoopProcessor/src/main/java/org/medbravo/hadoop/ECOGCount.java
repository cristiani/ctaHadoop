package org.medbravo.hadoop;


import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.TextInputFormat;
import org.apache.hadoop.mapred.TextOutputFormat;

/**
 *
 * @author cristian ionitoiu
 */
public class ECOGCount {
     public static void main(String[] args) throws Exception {
 	     JobConf conf = new JobConf(ECOGCount.class);
 	     conf.setJobName("ecogcount");
 	
 	     conf.setOutputKeyClass(Text.class);
 	     conf.setOutputValueClass(EPFSet.class);
 	
 	     conf.setMapperClass(ECOGMap.class);
 	     conf.setCombinerClass(ECOGReduce.class);
 	     conf.setReducerClass(ECOGReduce.class);
 	
 	     conf.setInputFormat(TextInputFormat.class);
 	     conf.setOutputFormat(TextOutputFormat.class);
 	
 	     FileInputFormat.setInputPaths(conf, new Path(args[0]));
 	     FileOutputFormat.setOutputPath(conf, new Path(args[1]));
 	
 	     JobClient.runJob(conf);
 	   }
}
