package com.devbliss.doctest.task

import com.devbliss.doctest.Configuration
import javax.inject.Inject
import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.testing.Test

/**
 * Doctest test run task.
 * For more information look at {@link Test}.
 * This class offers the same possiblities as the {@Test} but for doctest.
 *
 * Example configuration for custom report directories.
 * <pre>
 * doctest {
 *   docHtmlDir = "/some/doctest/html/dir/"
 *   reportDir = new File("some/report/dir/")
 * }
 * </pre>
 *
 * @author Dennis Schumann <dennis.schumann@devbliss.com>
 */
class DoctestTask extends Test {
  public static final String DOCTEST_RESULT_DIR_NAME = "doctests-result";
  public static final String DOCTEST_REPORT_DIR_NAME = "doctests";
  
  File reportDir;
  
  String docHtmlDir;
  
  public DoctestTask() {
    super();
    reportDir = new File(getProject().getBuildDir(), "reports/" + DOCTEST_REPORT_DIR_NAME);
    getReports().getHtml().setDestination(reportDir);
    File junitXmlDir = new File(getProject().getBuildDir(), DOCTEST_RESULT_DIR_NAME);
    getReports().getJunitXml().setDestination(junitXmlDir);
    File doctestBinResultDir = new File(getProject().getBuildDir(), DOCTEST_RESULT_DIR_NAME + "/binary/" + DOCTEST_REPORT_DIR_NAME);
    setBinResultsDir(doctestBinResultDir);
  }
  
  public void setReportDir(File reportDir) {
    this.reportDir = reportDir;
    getReports().getHtml().setDestination(this.reportDir);
  }
  
  public void setDocHtmlDir(String docHtmlDir) {
    this.docHtmlDir = docHtmlDir;
    this.systemProperty(Configuration.PROPERTY_HTML_OUTPUT_DIR, this.docHtmlDir)
  }
}
