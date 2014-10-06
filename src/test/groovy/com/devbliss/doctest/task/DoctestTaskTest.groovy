package com.devbliss.doctest.task

import com.devbliss.doctest.Configuration
import junit.framework.TestCase
import org.gradle.api.Project
import org.gradle.api.internal.AbstractTask
import org.junit.Test
import org.junit.runner.RunWith
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Assert

/**
 * Test for {@link DoctestTask}.
 *
 * @author Dennis Schumann <dennis.schumann@devbliss.com>
 */
public class DoctestTaskTest {
  private static final String DOC_HTML_DIR = "/myDoc/";

  @Test
  public void setReportDir() {
    Project project = ProjectBuilder.builder().build();
    def task  = project.task('doctest', type: DoctestTask);
    Assert.assertTrue(task instanceof DoctestTask);

    File testFile = new File("./blaaa/");
    task.setReportDir(testFile);
    Assert.assertEquals(project.getProjectDir().getPath()+"/blaaa", task.getReports().getHtml().getDestination().getPath());
  }

  @Test
  public void setDocHtmlDir() {
    Project project = ProjectBuilder.builder().build();
    def task  = project.task('doctest', type: DoctestTask);
    Assert.assertTrue(task instanceof DoctestTask);

    task.setDocHtmlDir(DOC_HTML_DIR);
    Assert.assertEquals(DOC_HTML_DIR, task.docHtmlDir);
    String htmlDir = task.getSystemProperties().get(Configuration.PROPERTY_HTML_OUTPUT_DIR);
    Assert.assertEquals(DOC_HTML_DIR, htmlDir);
  }
}

