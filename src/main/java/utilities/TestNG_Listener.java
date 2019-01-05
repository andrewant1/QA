package utilities;


import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class TestNG_Listener extends TestListenerAdapter {

    private static String logFile = null;

    @Override
    public void onStart(ITestContext testContext) {

        try {
            log("\nSuite start date:" + new SimpleDateFormat("MM.dd.YYYY.HH.mm.ss").format(new Date()) + "log");

        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onStart(testContext);

    }

    @Override
    public void  onFinish (ITestContext testContext){

        try {
            log("\nTotal Passed = " +
                   getPassedTests().size()+
                  ",Total Failed = " +
                    getFailedTests().size()+
                    ", Total Skipped =" +
                    getSkippedTests().size()+
                    "\n");
        }catch (Exception e){
            e.printStackTrace();
        }

        super.onFinish(testContext);
    }

    @Override
    public  void  onTestSuccess(ITestResult tr){

      try{
          log("*** Result = PASSED\n");
          log(tr.getEndMillis(),"Test ->"+
              tr.getInstanceName() +"."+
               tr.getName());
          log("\n");

      } catch (Exception e){
          e.printStackTrace();
      }

     super.onTestSuccess(tr);
    }




    public void log(long date, String dataLine) throws Exception {
        System.out.format("%s%n", String.valueOf(new Date(date)), dataLine);
        if (logFile != null) {
            writeLogFile(logFile, dataLine);
        }
    }

    public void log(String dataLine) throws Exception {
        System.out.format("%s%n", dataLine);
        if ( logFile != null ) {
            writeLogFile(logFile, dataLine);
        }
    }

    public void writeLogFile(String logFile, String line){

        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:MM:SS");
        Date date = new Date();
        File directory = new File(Global_VARS.LOGS_OUTPUT_PATH);
        File file = new File(logFile);
        try{
            if(!directory.exists()){
                directory.mkdir();
            }
           else if (!file.exists()){
                file.createNewFile();
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter(logFile, true));
           if (line.contains("START") || line.contains("END") ) {
               writer.append("[" +
                       dateFormat.format(date) +
                       "]" + line);
           }
           else  {
               writer.append(line);
           }
           writer.newLine();
           writer.close();

        } catch (IOException e) {
        }

    }


}



