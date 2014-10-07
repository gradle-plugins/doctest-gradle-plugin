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

import org.gradle.api.Plugin
import org.gradle.api.internal.project.ProjectInternal
import org.gradle.api.plugins.JavaBasePlugin

/**
 * BasePlugin for doctest plugin.
 * Applies {@link JavaBasePlugin} and adds it to the source set defaults.
 *
 * @author Dennis Schumann <dennis.schumann@devbliss.com>
 */
class DoctestBasePlugin implements Plugin<ProjectInternal> {

  private ProjectInternal project;

  public void apply(ProjectInternal project) {
    this.project = project

    JavaBasePlugin javaBasePlugin = project.getPlugins().apply(JavaBasePlugin.class)

    configureSourceSetDefaults(javaBasePlugin)
  }
}

