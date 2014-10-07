/*
 * Copyright 2014, devbliss GmbH
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.devbliss.doctest

import com.devbliss.doctest.task.DoctestTask
import org.gradle.api.Project
import org.junit.Rule
import org.junit.Test

/**
 * Test for {@link DoctestPlugin}.
 *
 * @author Dennis Schumann <dennis.schumann@devbliss.com>
 */
class DoctestPluginTest {
  private Project project;
  private final DoctestPlugin doctestPlugin = new DoctestPlugin();
  
  @Test public void applyStandardTask() {
    project = ProjectBuilder.builder().build();
    doctestPlugin.apply(project)

    def task = project.tasks[DoctestPlugin.DOCTEST_TASK_NAME]
    assertThat(task, instanceOf(DoctestTask))
  }

  @Test
  public void createsStandardSourceSetsAndAppliesMappings() {
    doctestPlugin.apply(project);

    def set = project.sourceSets[DoctestPlugin.DOCTEST_SOURCE_SET_NAME];
    assertThat(set.java.srcDirs, equalTo(toLinkedSet(project.file('src/doctest/java'))));
    assertThat(set.resources.srcDirs, equalTo(toLinkedSet(project.file('src/doctest/resources'))));
    assertThat(set.compileClasspath, sameInstance(project.configurations.doctestCompile));
    assertThat(set.output.classesDir, equalTo(new File(project.buildDir, 'classes/doctest')));
    assertThat(set.output.resourcesDir, equalTo(new File(project.buildDir, 'resources/doctest')));
    assertThat(set.runtimeClasspath.sourceCollections, hasItem(project.configurations.doctestRuntime));
    assertThat(set.runtimeClasspath, hasItem(new File(project.buildDir, 'classes/doctest')));
  }
}

