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
package io.github.ljnelson.dev5432.configmap.controller;

import java.io.Closeable; // for javadoc only

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;

import javax.enterprise.event.Observes;

import javax.enterprise.inject.Produces;

import javax.inject.Qualifier;

import io.fabric8.kubernetes.api.model.ConfigMap;
import io.fabric8.kubernetes.api.model.ConfigMapList;
import io.fabric8.kubernetes.api.model.DoneableConfigMap;
import io.fabric8.kubernetes.api.model.HasMetadata; // for javadoc only
import io.fabric8.kubernetes.api.model.KubernetesResourceList; // for javadoc only

import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.Watcher; // for javadoc only

import io.fabric8.kubernetes.client.dsl.Listable; // for javadoc only
import io.fabric8.kubernetes.client.dsl.Namespaceable; // for javadoc only
import io.fabric8.kubernetes.client.dsl.NonNamespaceOperation;
import io.fabric8.kubernetes.client.dsl.Resource;
import io.fabric8.kubernetes.client.dsl.VersionWatchable; // for javadoc only
import io.fabric8.kubernetes.client.dsl.Watchable; // for javadoc only

import org.microbean.kubernetes.controller.cdi.annotation.Added;
import org.microbean.kubernetes.controller.cdi.annotation.Deleted; // for javadoc only
import org.microbean.kubernetes.controller.cdi.annotation.KubernetesEventSelector;
import org.microbean.kubernetes.controller.cdi.annotation.Modified;
import org.microbean.kubernetes.controller.cdi.annotation.Prior;

/**
 * A <em>controller</em> that {@linkplain
 * #onConfigMapAddition(ConfigMap) watches for the addition of
 * <code>ConfigMap</code>}s.
 *
 * @author <a href="https://about.me/lairdnelson"
 * target="_parent">Laird Nelson</a>
 *
 * @see #onConfigMapAddition(ConfigMap)
 *
 * @see #produceAllDefaultConfigMaps(KubernetesClient)
 */
@ApplicationScoped
final class AllDefaultConfigMapsController {

  /**
   * A {@link Logger} for logging messages.
   *
   * <p>This field is never {@code null}.</p>
   *
   * <p>This {@link Logger}'s {@linkplain Logger#getName() name} is
   * {@code
   * ljnelson.dev5432.configmap.controller.AllDefaultConfigMapsController}.</p>
   */
  private static final Logger logger = Logger.getLogger(AllDefaultConfigMapsController.class.getName());

  /**
   * Creates a new {@link AllDefaultConfigMapsController}.
   */
  private AllDefaultConfigMapsController() {
    super();
  }

  /**
   * A <a
   * href="http://docs.jboss.org/cdi/spec/2.0/cdi-spec.html#producer_method"
   * target="_parent">producer method</a> which is a <em>Kubernetes
   * event selector</em> that produces a {@link NonNamespaceOperation}
   * whose {@link Listable#list() list()} and {@link
   * Watchable#watch(Object) watch(Watcher)} methods will eventually
   * produce events that will be processed by the {@link
   * #onConfigMapAddition(ConfigMap)} Kubernetes event observer
   * method.
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
   * @see #onConfigMapAddition(ConfigMap)
   */
  @Produces
  @ApplicationScoped
  @AllDefaultConfigMaps
  private static final NonNamespaceOperation<ConfigMap, ConfigMapList, DoneableConfigMap, Resource<ConfigMap, DoneableConfigMap>> produceAllDefaultConfigMaps(final KubernetesClient client) {
    return client.configMaps().inNamespace("default");
  }

  /**
   * A <em>Kubernetes event observer method</em>
   * that will be notified with {@link ConfigMap} "events" that
   * describe the {@linkplain Added addition} of a {@code ConfigMap}
   * to a Kubernetes cluster.
   *
   * <p>The production of these events is governed and filtered by (in
   * this application) the recipe manifested by the {@link
   * #produceAllDefaultConfigMaps(KubernetesClient)} producer method.
   * The events that result indirectly from that Kubernetes event
   * selector method are "linked" with this Kubernetes event observer
   * method by way of the {@link AllDefaultConfigMaps} Kubernetes
   * event selector annotation.</p>
   *
   * @param newConfigMap the {@link ConfigMap} that was added; will
   * not be {@code null}
   *
   * @see AllDefaultConfigMaps
   *
   * @see #produceAllDefaultConfigMaps(KubernetesClient)
   *
   * @see Added
   */
  private final void onConfigMapAddition(@Observes
                                         @AllDefaultConfigMaps
                                         @Added
                                         final ConfigMap newConfigMap) {
    logger.log(Level.INFO, "New ConfigMap added: {0}", newConfigMap);
  }

  private final void onConfigMapModification(@Observes
                                             @AllDefaultConfigMaps
                                             @Modified
                                             final ConfigMap modifiedConfigMap,
                                             @Prior
                                             final Optional<ConfigMap> priorConfigMap) {
    logger.log(Level.INFO, "ConfigMap modified.\n  Old ConfigMap:\n{0}\n  New ConfigMap:\n{1}\n", new Object[] { priorConfigMap.get(), modifiedConfigMap });
  }

  /**
   * A <a
   * href="http://docs.jboss.org/cdi/spec/2.0/cdi-spec.html#producer_method"
   * target="_parent">producer method</a> that returns an {@linkplain
   * ApplicationScoped application-scoped} {@link Map} of {@link
   * ConfigMap}s indexed by the keys that Kubernetes assigns to them.
   *
   * <p>This method never returns {@code null}.</p>
   *
   * <h2>Thread Safety</h2>
   *
   * <p>This method is safe for concurrent use by multiple
   * threads.</p>
   *
   * <p><strong>The return value that results from invoking this
   * method is <em>not</em> safe for concurrent use by multiple
   * threads.</strong> Threads must {@code synchronize} on it
   * appropriately for all retrieval, traversal and mutation
   * operations.</p>
   *
   * @return a non-{@code null} {@link Map} that will be used to house
   * prior states of Kubernetes resources
   */
  @Produces
  @ApplicationScoped
  @AllDefaultConfigMaps
  private static final Map<Object, ConfigMap> produceKubernetesResourceCache() {
    return new HashMap<>();
  }
  
  /**
   * A {@linkplain Qualifier qualifier annotation} that itself is
   * qualified with {@link KubernetesEventSelector} allowing it to pair
   * relevant producer and observer methods so that certain {@code
   * ConfigMap} resources can be observed.
   *
   * <h2>Learning Notes</h2>
   *
   * <p>Note that this annotation is annotated with both {@link
   * Qualifier}&mdash;making it a <a
   * href="http://docs.jboss.org/cdi/spec/2.0/cdi-spec.html#qualifiers"
   * target="_parent">CDI qualifier</a>&mdash;and {@link
   * KubernetesEventSelector}&mdash;allowing it to be recognized by the
   * <a
   * href="https://microbean.github.io/microbean-kubernetes-controller-cdi/"
   * target="_parent">microBean Kubernetes Controller CDI</a>
   * framework.</p>
   *
   * <p>Because it "is a" {@link KubernetesEventSelector}, you apply
   * this annotation to two things:</p>
   *
   * <ol>
   *
   * <li>To a bean&mdash;most commonly to a <a
   * href="http://docs.jboss.org/cdi/spec/2.0/cdi-spec.html#producer_method"
   * target="_parent">producer method</a>&mdash;that can be assigned to
   * a reference that can be described by the following type
   * declaration: {@code <X extends }{@link Listable}{@code <?  extends
   * }{@link KubernetesResourceList}{@code > & }{@link
   * VersionWatchable}{@code <? extends }{@link Closeable}{@code ,
   * }{@link Watcher}{@code <? extends }{@link HasMetadata}{@code >>>}.
   * This bean is known as a <em>Kubernetes event selector</em>: it is
   * something whose {@link Listable#list() list()} method and {@link
   * Watchable#watch(Object) watch(W)} method will be called to select
   * what Kubernetes events will get delivered to your Kubernetes event
   * observer method.  While that type declaration looks complicated, it
   * conveniently describes the return types of most of the methods on
   * {@link KubernetesClient}.</li>
   *
   * <li>To an <a
   * href="http://docs.jboss.org/cdi/spec/2.0/cdi-spec.html#observer_methods"
   * target="_parent">observer method</a>'s <a
   * href="http://docs.jboss.org/cdi/spec/2.0/cdi-spec.html#observer_method_event_parameter"
   * target="_parent">event parameter</a> whose type is identical to the
   * "primary type" in the matching bean or producer method.  For
   * example, given a bean type&mdash;usually the return value of a
   * producer method&mdash;of {@link NonNamespaceOperation}{@code
   * <}{@link ConfigMap}{@code , }{@link ConfigMapList}{@code , }{@link
   * DoneableConfigMap}{@code , }{@link Resource}{@code <}{@link
   * ConfigMap}{@code , }{@link DoneableConfigMap}{@code >>} the
   * "primary type" is {@link ConfigMap}.  This identifies your observer
   * method to the <a
   * href="https://microbean.github.io/microbean-kubernetes-controller-cdi/"
   * target="_parent">microBean Kubernetes Controller CDI</a> framework
   * and allows it to deliver Kubernetes events to it using the
   * similarly-annotated bean or producer method (described above).  An
   * observer method whose event parameter is annotated in this
   * way&mdash;together with {@link Added}, {@link Modified} or {@link
   * Deleted} qualifier annotations&mdash;constitutes a <em>Kubernetes
   * event observer method</em>.</li>
   *
   * </ol>
   *
   * <p>When you "connect" a Kubernetes event selector with a Kubernetes
   * event observer method using this annotation, then events produced
   * by the Kubernetes event selector will be delivered to the
   * Kubernetes event observer method.  If you "connect" a different
   * Kubernetes event selector with a different Kubernetes event
   * observer method using another {@link
   * KubernetesEventSelector}-annotated qualifier annotation, then
   * <em>that</em> Kubernetes event selector will be used to deliver
   * Kubernetes events to <em>that</em> Kubernetes event observer
   * method.</p>
   *
   * @see KubernetesEventSelector
   */
  @Documented
  @KubernetesEventSelector
  @Qualifier
  @Retention(value = RetentionPolicy.RUNTIME)
  @Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.TYPE })
  public @interface AllDefaultConfigMaps {

  }
  
}
