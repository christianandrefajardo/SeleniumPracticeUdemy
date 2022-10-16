package udemymaven.practice.Resources;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReporterNG {

    public static ExtentReports getReportObject() {
        String path = System.getProperty("user.dir")+"\\reports\\index.html";
        ExtentSparkReporter reporter = new ExtentSparkReporter(path);
        ExtentReports extent = new ExtentReports();
        
        reporter.config().setReportName("Web Automation Results");
        reporter.config().setDocumentTitle("Test Results");
        
        extent.attachReporter(reporter);
        extent.setSystemInfo("Tester", "Andre");
        
        return extent;
    }
    
}
