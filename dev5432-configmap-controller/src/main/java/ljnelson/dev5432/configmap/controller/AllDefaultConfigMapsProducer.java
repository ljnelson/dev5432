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
import io.fabric8.kubernetes.api.model.HasMetadata; // for javadoc only

import io.fabric8.kubernetes.client.KubernetesClient;

import io.fabric8.kubernetes.client.dsl.Listable; // for javadoc only
import io.fabric8.kubernetes.client.dsl.Namespaceable; // for javadoc only
import io.fabric8.kubernetes.client.dsl.NonNamespaceOperation;
import io.fabric8.kubernetes.client.dsl.Resource;
import io.fabric8.kubernetes.client.dsl.Watchable; // for javadoc only

import ljnelson.dev5432.configmap.controller.annotation.AllDefaultConfigMaps;

import org.microbean.kubernetes.controller.cdi.annotation.Added; // for javadoc only
import org.microbean.kubernetes.controller.cdi.annotation.Deleted; // for javadoc only
import org.microbean.kubernetes.controller.cdi.annotation.KubernetesEventSelector; // for javadoc only
import org.microbean.kubernetes.controller.cdi.annotation.Modified; // for javadoc only

/**
 * A class housing a {@linkplain
 * #produceAllDefaultConfigMaps(KubernetesClient) Kubernetes event
 * selector method}.
 *
 * <h2>Learning Notes</h2>
 *
 * <p>This class houses a <a
 * href="http://docs.jboss.org/cdi/spec/2.0/cdi-spec.html#producer_method"
 * target="_parent">producer method</a> that has been annotated with
 * {@link AllDefaultConfigMaps @AllDefaultConfigMaps}.  The presence
 * of this annotation indicates that the <a
 * href="https://microbean.github.io/microbean-kubernetes-controller-cdi/"
 * target="_parent">microBean Kubernetes Controller CDI</a> framework
 * should look for one or more <a
 * href="http://docs.jboss.org/cdi/spec/2.0/cdi-spec.html#observer_methods"
 * target="_parent">observer methods</a> whose <a
 * href="http://docs.jboss.org/cdi/spec/2.0/cdi-spec.html#observer_method_event_parameter"
 * target="_parent">event parameter</a> meets certain criteria:</p>
 *
 * <ol>
 *
 * <li>It is an instance of the "primary type" in the return type of
 * the producer method.  For example, in the case of this class'
 * {@link #produceAllDefaultConfigMaps(KubernetesClient)} method, you
 * can see that the "primary type" is {@link ConfigMap}.</li>
 *
 * <li>It is also annotated with {@link AllDefaultConfigMaps}.</li>
 *
 * <li>It has one of the following qualifier annotations on it: {@link
 * Added}, {@link Deleted} or {@link Modified}.</li>
 *
 * </ol>
 *
 * <p>An example of just such a Kubernetes event observer method may
 * be found {@linkplain
 * AllDefaultConfigMapsEventHandler#onConfigMapAddition(ConfigMap) in
 * the <code>AllDefaultConfigMapsEventHandler</code> class}.</p>
 *
 * @see #produceAllDefaultConfigMaps(KubernetesClient)
 *
 * @see
 * AllDefaultConfigMapsEventHandler#onConfigMapAddition(ConfigMap)
 *
 * @see AllDefaultConfigMaps
 */
@ApplicationScoped
final class AllDefaultConfigMapsProducer {


  /*
   * Constructors.
   */


  /**
   * Creates a new {@link AllDefaultConfigMapsProducer}.
   */
  private AllDefaultConfigMapsProducer() {
    super();
  }


  /*
   * Static methods.
   */
  

  /**
   * A <a
   * href="http://docs.jboss.org/cdi/spec/2.0/cdi-spec.html#producer_method"
   * target="_parent">producer method</a> which is a <em>Kubernetes
   * event selector</em> that produces a {@link NonNamespaceOperation}
   * whose {@link Listable#list() list()} and {@link
   * Watchable#watch(Object) watch(Watcher)} methods will eventually
   * produce events that will be processed by the Kubernetes event
   * observer method present in the {@link
   * AllDefaultConfigMapsEventHandler} class.
   *
   * <p>This method never returns {@code null}.</p>
   *
   * @param client a {@link KubernetesClient} injected by the CDI ecosystem;
   * will not be {@code null}; in this example application it is
   * supplied to CDI by the <a
   * href="https://microbean.github.io/microbean-kubernetes-client-cdi/"
   * target="_parent">microBean Kubernetes Client CDI</a> project
   *
   * @return the result of invoking {@link
   * Namespaceable#inNamespace(String)
   * client.configMaps().inNamespace("default")}; never {@code null}
   *
   * @see KubernetesClient
   *
   * @see KubernetesClient#configMaps()
   *
   * @see Namespaceable#inNamespace(String)
   *
   * @see AllDefaultConfigMaps
   *
   * @see AllDefaultConfigMapsEventHandler
   *
   * @see AllDefaultConfigMapsEventHandler#onConfigMapAddition(ConfigMap)
   */
  @Produces
  @ApplicationScoped
  @AllDefaultConfigMaps
  private static final NonNamespaceOperation<ConfigMap, ConfigMapList, DoneableConfigMap, Resource<ConfigMap, DoneableConfigMap>> produceAllDefaultConfigMaps(final KubernetesClient client) {
    return client.configMaps().inNamespace("default");
  }
  
}
