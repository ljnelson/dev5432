/* -*- mode: Java; c-basic-offset: 2; indent-tabs-mode: nil; coding: utf-8-unix -*-
 *
 * Copyright © 2018 Laird Nelson.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.  See the License for the specific language governing
 * permissions and limitations under the License.
 */
package ljnelson.dev5432.configmap.controller;

import javax.enterprise.inject.se.SeContainer;
import javax.enterprise.inject.se.SeContainerInitializer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assume.assumeTrue;

public class TestCaseController {

  private SeContainer container;
  
  @Before
  public void startCdi() {
    this.stopCdi();
    assumeTrue("Cluster tests explicitly disabled", Boolean.getBoolean("enableClusterTests"));
    this.container = SeContainerInitializer.newInstance().initialize();
  }  

  @After
  public void stopCdi() {
    if (this.container != null) {
      this.container.close();
    }
  }
  
  @Test
  public void testController() throws Exception {

  }
  
}
