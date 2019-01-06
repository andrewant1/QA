package utilities;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class File_IO {

    public static Properties loadProps(String file) throws Exception {
        Properties props = new Properties();
        props.load(new FileInputStream(file));
        return  props;
    }

    public  static String lookUPError(String propFilePath, String code) throws  Exception{
        Properties exceptionProps = new Properties();
        exceptionProps.load(new FileInputStream(propFilePath));
        return  exceptionProps.getProperty(code);
    }

    public  static List<String> extractData(String csvFile, String rowID) throws Exception{

        List<String> rows = new ArrayList<String >();
        String line ="";
        BufferedReader reader = new BufferedReader(new FileReader(csvFile));

        while ((line = reader.readLine()) != null){
            if (line.startsWith(rowID)){
                rows.add(line);
            }
        }
        reader.close();
        return rows;
    }

    public  static List<String> extractDataLog(String logFile) throws Exception{

        List<String> rows = new ArrayList<String >();
        String line ="";
        BufferedReader reader = new BufferedReader(new FileReader(logFile));

        while ((line = reader.readLine()) != null){
            rows.add(line);
        }
        reader.close();
        return rows;
    }

    public  static  void writeFile(String file, String rowData) throws Exception{

        String getLine = "";
        BufferedReader reader = new BufferedReader(new FileReader(file));
        Boolean bFound= false;

        while((getLine = reader.readLine()) != null){
            if(getLine.contains(rowData)){
                bFound = true;
                break;
            }
        }
        reader.close();

        if  (bFound != true){
            BufferedWriter writer = new BufferedWriter(new FileWriter(file,true));
            writer.append(rowData);
            writer.newLine();
            writer.close();
        }
    }

}
