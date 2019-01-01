package utilities;

import java.io.File;

public class Global_VARS {
    public static  final String  BROWSER = "Firefox";
    public  static  final  String PLATFORM = "Ubuntu";
    public  static  final String  ENVIRONMENT = "local";
    public  static String DEF_BROWSER = null;
    public  static  String DEF_PLATFORM = null;
    public  static String DEF_ENVIRONMENT = null;
    public static  String PROPS_PATH = null;
    public  static  String propFile = "user.dir"+"Properties/selenium.props";
    public  static  final String PROPS_SE = new File(propFile).getAbsolutePath();
    public  static final String TEST_OUTPUT_PATH = "user.dir"+"testOutput/";
    public  static  final  String LOGS_OUTPUT_PATH = TEST_OUTPUT_PATH +"Logs/";
    public  static  final  String REPORT_OUTPUT_PATH = TEST_OUTPUT_PATH +"Reports/";
    public  static  final  String BITMAP_OUTPUT_PATH = TEST_OUTPUT_PATH +"Bitmaps/";

    public  static final  int TIMEOUT_MINUTE = 60;
    public  static final  int TIMEOUT_SECOND = 1;
    public  static final  int TIMEOUT_ZERO = 0;



}
