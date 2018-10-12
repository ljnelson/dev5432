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

import javax.enterprise.context.ApplicationScoped;

import javax.enterprise.inject.Produces;

import io.fabric8.kubernetes.api.model.ConfigMap;
import io.fabric8.kubernetes.api.model.ConfigMapList;
import io.fabric8.kubernetes.api.model.DoneableConfigMap;

import io.fabric8.kubernetes.client.KubernetesClient;

import io.fabric8.kubernetes.client.dsl.NonNamespaceOperation;
import io.fabric8.kubernetes.client.dsl.Resource;

import ljnelson.dev5432.configmap.controller.annotation.AllDefaultConfigMaps;

@ApplicationScoped
final class AllDefaultConfigMapsProducer {

  private AllDefaultConfigMapsProducer() {
    super();
  }

  @Produces
  @ApplicationScoped
  @AllDefaultConfigMaps
  private final NonNamespaceOperation<ConfigMap, ConfigMapList, DoneableConfigMap, Resource<ConfigMap, DoneableConfigMap>> produceAllDefaultConfigMaps(final KubernetesClient client) {
    return client.configMaps().inNamespace("default");
  }
  
}
