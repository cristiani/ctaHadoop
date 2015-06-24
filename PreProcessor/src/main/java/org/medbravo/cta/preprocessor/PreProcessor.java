package org.medbravo.cta.preprocessor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URL;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;
import org.medbravo.cta.model.ClinicalStudy;
import org.medbravo.cta.model.EligibilityStruct;
import org.medbravo.cta.model.TextblockStruct;
import org.xml.sax.SAXException;
import org.apache.commons.cli.*;

/**
 * Takes care of the pre-processing phase of the CTA. Uses JAXB and CTA model to extract and process from CTA XML file the eligibility criteria into a CSV file;
 * It parses the eligibility criteria line by line and creates a csv file with the following format
 * CTA id, Type(Inclusion/Exclusion), line
 * NCT02217865, I, Able to undergo a 30 to 60-minute interview or focus group
 * NCT02217865, E, N/A
 * @author cristian ionitoiu
 */
public class PreProcessor {
    private static String xmlDir;  //assumed absolute path
    private static String csvDir;
   
    /**
     * Expects two arguments: -xmldir directory that holds xml files
     *                        -csvdir output directory for csv file - optional, if missing csv files will be placed in xmldir
     * @param args 
     */
    public static void main(String[] args) {
        Options options = new Options();
        
        //process arguments
        Option xmldir = Option.builder("xmldir")
                            .hasArg()
                            .required(true)
                            .desc("directory that holds xml files")
                            .build();
        
        Option csvdir = Option.builder("csvdir")
                            .hasArg()
                            .required(false)
                            .desc("directory that holds csv files")
                            .build();

        options.addOption(xmldir);
        options.addOption(csvdir);
        
        try {
            CommandLineParser parser = new DefaultParser();
            CommandLine cmd = parser.parse(options, args);
            
            if (cmd.hasOption("xmldir")) {
                xmlDir = cmd.getOptionValue("xmldir");
            } else {
                //show usage and get out
                System.exit(0);
            }
            if (cmd.hasOption("csvdir")) {
                csvDir = cmd.getOptionValue("csvdir");
            } else {
                csvDir = xmlDir;
            }
        } catch (ParseException pe) {
            System.err.println(pe.getMessage());
        }
        //unmarshal XML files
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String packageName = ClinicalStudy.class.getPackage().getName();
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(packageName, classLoader);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            
            URL resource = classLoader.getResource("public.xsd");
            unmarshaller.setSchema(schemaFactory.newSchema(resource));
            
            //get files from directory
            File xmlRepository = new File(xmlDir);
            for (File file : xmlRepository.listFiles()) {
                String name = file.getName();
                //consider only xml files - just in case
                int position = name.lastIndexOf(".");
                if (position > 0) {
                    String extension = name.substring(position + 1);
                    if ("xml".equals(extension)) {
                        StreamSource streamSource = new StreamSource(file);
                        JAXBElement<ClinicalStudy> element = unmarshaller.unmarshal(streamSource, ClinicalStudy.class);
                        ClinicalStudy study = element.getValue();

                        if (study != null) {
                            String studyId = study.getIdInfo().getNctId();
                            EligibilityStruct eligibility = study.getEligibility();
                            TextblockStruct tbs = eligibility.getCriteria();
                            String[] lines = tbs.getTextblock().split("\\n");
                            if (lines.length > 0) {
                                //create output csv file
                                File outputDirectory = xmlRepository;
                                if (csvDir != null) {
                                    outputDirectory = new File(csvDir);
                                }
                                File outputFile = new File(outputDirectory.getAbsolutePath() + File.separator + studyId + ".csv");
                                
                                try {
                                    FileOutputStream fout = new FileOutputStream(outputFile);
                                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fout));
                                    String lineType = "I"; //default inclusion
                                    for (String line : lines) {
                                        line = line.trim();
                                        if (!"".equals(line)) {
                                            if (line.contains("Inclusion Criteria")) {
                                                //ignore this line
                                                continue;
                                            }
                                            if (line.contains("Exclusion Criteria")) {
                                                //change type for next lines and ignore this line
                                                lineType = "E";
                                                continue;
                                            }
                                            bw.write(studyId + "," + lineType + "," + line);
                                            bw.newLine();
                                        }
                                    }
                                    //cascade close of all I/O decoration
                                    bw.close();
                                } catch (IOException ioe) {
                                    System.err.println(ioe.getMessage());
                                }
                            }
                        }
                    }
                }
            }
        } catch (JAXBException | SAXException e) {
			System.err.println(e.getMessage());
		}
    }
}
