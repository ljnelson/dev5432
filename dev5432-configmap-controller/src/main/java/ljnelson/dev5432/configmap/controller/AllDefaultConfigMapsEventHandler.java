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

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;

import javax.enterprise.event.Observes;

import io.fabric8.kubernetes.api.model.ConfigMap;

import ljnelson.dev5432.configmap.controller.annotation.AllDefaultConfigMaps;

import org.microbean.kubernetes.controller.cdi.annotation.Added;

@ApplicationScoped
final class AllDefaultConfigMapsEventHandler {

  private final Logger logger;
  
  private AllDefaultConfigMapsEventHandler() {
    super();
    this.logger = Logger.getLogger(this.getClass().getName());
  }

  private final void onStartup(@Observes @Initialized(ApplicationScoped.class) final Object event) {
    final String cn = this.getClass().getName();
    final String mn = "onStartup";
    if (this.logger.isLoggable(Level.FINE)) {
      logger.logp(Level.FINE, cn, mn, "CDI is up");
    }
  }
  
  private final void onConfigMapAddition(@Observes @Added @AllDefaultConfigMaps final ConfigMap newConfigMap) {
    final String cn = this.getClass().getName();
    final String mn = "onConfigMapAddition";
    if (this.logger.isLoggable(Level.INFO)) {
      logger.logp(Level.INFO, cn, mn, "New ConfigMap added: {0}", newConfigMap);
    }
  }

}
