package com.devbliss.doctest

import com.devbliss.doctest.task.DoctestTask
import org.gradle.api.Action
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.Configuration
import org.gradle.api.artifacts.ConfigurationContainer
import org.gradle.api.artifacts.Dependency
import org.gradle.api.plugins.JavaPlugin
import org.gradle.api.tasks.SourceSet
import org.gradle.api.tasks.SourceSetContainer
import org.gradle.api.plugins.JavaBasePlugin
import org.gradle.api.plugins.JavaPluginConvention
import java.util.concurrent.Callable

/**
 * 
 *
 * @author Dennis Schumann <dennis.schumann@devbliss.com>
 */
class DoctestPlugin implements Plugin<Project> {
  public static final String DOCTEST_SOURCE_SET_NAME = "doctest";
  
  public static final String DOCTEST_TASK_NAME = "doctest";
  
  public static final String DOCTEST_RUNTIME_CONFIGURATION_NAME = "doctestRuntime";
  public static final String DOCTEST_COMPILE_CONFIGURATION_NAME = "doctestCompile";
  
  def void apply(Project project) {
    project.getPlugins().apply(JavaPlugin.class);
    
    JavaBasePlugin javaBasePlugin = project.getPlugins().apply(JavaBasePlugin.class);
    JavaPluginConvention javaConvention = project.getConvention().getPlugin(JavaPluginConvention.class);
    
    configureSourceSets(javaBasePlugin, project);
    configureConfigurations(project);
    
    configureTest(javaConvention, project);
  }
  
  private void configureSourceSets(final JavaBasePlugin javaBasePlugin, Project project) {
    SourceSetContainer sourceSetContainer = project.getConvention().getPlugin(JavaPluginConvention.class).getSourceSets();
    SourceSet main = sourceSetContainer.getByName(SourceSet.MAIN_SOURCE_SET_NAME);
    SourceSet doctest = sourceSetContainer.create(DOCTEST_SOURCE_SET_NAME);
    
    doctest.setCompileClasspath(project.files(main.getOutput(), project.getConfigurations().getByName(DOCTEST_COMPILE_CONFIGURATION_NAME)));
    doctest.setRuntimeClasspath(project.files(doctest.getOutput(), main.getOutput(), project.getConfigurations().getByName(DOCTEST_RUNTIME_CONFIGURATION_NAME)));
  }

  private void configureConfigurations(Project project) {
    ConfigurationContainer configurations = project.getConfigurations();
    Configuration compileConfiguration = configurations.getByName(JavaPlugin.COMPILE_CONFIGURATION_NAME);
    Configuration runtimeConfiguration = configurations.getByName(JavaPlugin.RUNTIME_CONFIGURATION_NAME);

    Configuration compileTestsConfiguration = configurations.getByName(DOCTEST_COMPILE_CONFIGURATION_NAME);
    compileTestsConfiguration.extendsFrom(compileConfiguration);

    configurations.getByName(DOCTEST_RUNTIME_CONFIGURATION_NAME).extendsFrom(runtimeConfiguration, compileTestsConfiguration);

    configurations.getByName(Dependency.DEFAULT_CONFIGURATION).extendsFrom(runtimeConfiguration);
  }
  
  private void configureTest(JavaPluginConvention javaConvention, Project project) {
    project.getTasks().withType(DoctestTask.class, new Action<DoctestTask>() {
        public void execute(final DoctestTask doctest) {
          doctest.getConventionMapping().map("testClassesDir", new Callable<Object>() {
              public Object call() throws Exception {
                return javaConvention.getSourceSets().getByName(DOCTEST_SOURCE_SET_NAME).getOutput().getClassesDir();
              }
            });
          doctest.getConventionMapping().map("classpath", new Callable<Object>() {
              public Object call() throws Exception {
                return javaConvention.getSourceSets().getByName(DOCTEST_SOURCE_SET_NAME).getRuntimeClasspath();
              }
            });
          doctest.getConventionMapping().map("testSrcDirs", new Callable<Object>() {
              public Object call() throws Exception {
                return new ArrayList<File>(javaConvention.getSourceSets().getByName(DOCTEST_SOURCE_SET_NAME)
                  .getJava().getSrcDirs());
              }
            });
        }
      });
    DoctestTask doctest = project.getTasks().create(DOCTEST_TASK_NAME, DoctestTask.class);
    project.getTasks().getByName(JavaBasePlugin.CHECK_TASK_NAME).dependsOn(doctest);
    doctest.setDescription("Runs your doctests test files and generates test and documentation results of them.");
    doctest.setGroup(JavaBasePlugin.VERIFICATION_GROUP);
  }
}
