/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package io.openmessaging;

import io.openmessaging.exception.OMSResourceNotExistException;
import java.util.List;

/**
 * The {@code ResourceManager} is to provide a unified interface of resource management,
 * allowing developers to manage the namespace, queue and routing resources.
 * <p>
 * Create, set, get and delete are the four basic operations of {@code ResourceManager}.
 * <p>
 * {@code ResourceManager} also supports dynamic fetch and update of resource attributes.
 * <p>
 * {@link MessagingAccessPoint#resourceManager()} ()} is the unique method to obtain a {@code ResourceManager}
 * instance. Changes made through this instance will immediately apply to the message-oriented middleware (MOM) behind
 * {@code MessagingAccessPoint}.
 * <p>
 * All operations conducted via this instance are confined to the configured namespace,
 * with default namespace derived from the OMS driver url of {@code MessagingAccessPoint}.Change namespace
 * by {@link ResourceManager#switchNamespace(String)} whenever necessary.
 *
 * @version OMS 1.0.0
 * @since OMS 1.0.0
 */
public interface ResourceManager {
    /**
     * Creates a {@code Namespace} resource with some preset attributes.
     *
     * @param nsName the name of the new namespace
     * @param attributes the preset attributes
     */
    void createNamespace(String nsName, KeyValue attributes);

    /**
     * Sets the attributes of the configured namespace, the old attributes will be replaced
     * by the provided attributes, only the provided key will be updated.
     *
     * @param attributes the new attributes
     * @throws OMSResourceNotExistException if the configured namespace does not exist
     */
    void setNamespaceAttributes(KeyValue attributes) throws OMSResourceNotExistException;

    /**
     * Gets the attributes of the configured namespace.
     *
     * @return the attributes of namespace
     * @throws OMSResourceNotExistException if the configured namespace does not exist
     */
    KeyValue getNamespaceAttributes() throws OMSResourceNotExistException;

    /**
     * Deletes an existing namespace resource.
     *
     * @param nsName the namespace needs to be deleted
     * @throws OMSResourceNotExistException if the specified namespace does not exist
     */
    void deleteNamespace(String nsName) throws OMSResourceNotExistException;

    /**
     * Gets the namespace list in the current {@code MessagingAccessPoint}.
     *
     * @return the list of all namespaces
     */
    List<String> listNamespaces();

    /**
     * Switches the default namespace to the new one, and all the operations will reflect to
     * the new namespace after the method returns successfully.
     *
     * @param nsName the target namespace to switch
     * @throws OMSResourceNotExistException if the new namespace does not exist
     */
    void switchNamespace(String nsName) throws OMSResourceNotExistException;

    /**
     * Creates a {@code Queue} resource in the configured namespace with some preset attributes.
     *
     * @param queueName the name of the new queue
     * @param attributes the preset attributes
     * @throws OMSResourceNotExistException if the configured namespace does not exist or specified queue name is
     * not available
     */
    void createQueue(String queueName, KeyValue attributes) throws OMSResourceNotExistException;

    /**
     * Sets the attributes of the specified queue, the old attributes will be replaced
     * by the provided attributes, only the provided key will be updated.
     *
     * @param queueName the queue name
     * @param attributes the new attributes
     * @throws OMSResourceNotExistException if the specified queue or namespace does not exist
     */
    void setQueueAttributes(String queueName, KeyValue attributes) throws OMSResourceNotExistException;

    /**
     * Gets the attributes of the specified queue.
     *
     * @param queueName the queue name
     * @return the attributes of namespace
     * @throws OMSResourceNotExistException if the specified queue or namespace does not exist
     */
    KeyValue getQueueAttributes(String queueName) throws OMSResourceNotExistException;

    /**
     * Deletes an existing queue resource.
     *
     * @param queueName the queue needs to be deleted
     * @throws OMSResourceNotExistException if the specified queue or namespace does not exist
     */
    void deleteQueue(String queueName) throws OMSResourceNotExistException;

    /**
     * Gets the queue list in the configured namespace.
     *
     * @return the list of all queues
     * @throws OMSResourceNotExistException if the configured namespace does not exist
     */
    List<String> listQueues() throws OMSResourceNotExistException;

    /**
     * Creates a {@code Routing} resource in the configured namespace with some preset attributes.
     *
     * @param routingName the name of the new routing
     * @param attributes the preset attributes
     * @throws OMSResourceNotExistException if the configured namespace does not exist or specified routing name is not
     * available
     */
    void createRouting(String routingName, KeyValue attributes) throws OMSResourceNotExistException;

    /**
     * Sets the attributes of the specified routing, the old attributes will be replaced
     * by the provided attributes, only the provided key will be updated.
     *
     * @param routingName the routing name
     * @param attributes the new attributes
     * @throws OMSResourceNotExistException if the specified routing or namespace does not exist
     */
    void setRoutingAttributes(String routingName, KeyValue attributes) throws OMSResourceNotExistException;

    /**
     * Gets the attributes of the specified routing.
     *
     * @param routingName the routing name
     * @return the attributes of routing
     * @throws OMSResourceNotExistException if the specified routing or namespace does not exist
     */
    KeyValue getRoutingAttributes(String routingName) throws OMSResourceNotExistException;

    /**
     * Deletes an existing routing resource.
     *
     * @param routingName the routing needs to be deleted
     * @throws OMSResourceNotExistException if the specified routing or namespace does not exist
     */
    void deleteRouting(String routingName) throws OMSResourceNotExistException;

    /**
     * Gets the routing list in the configured namespace.
     *
     * @return the list of all routings
     * @throws OMSResourceNotExistException if the configured namespace does not exist
     */
    List<String> listRoutings() throws OMSResourceNotExistException;

    /**
     * Gets the stream list behind the specified queue.
     *
     * @param queueName the queue name
     * @return the list of all streams
     */
    List<String> listStreams(String queueName);

    /**
     * Updates some system headers of a message in the configured namespace.
     * <p>
     * Below system headers are allowed to be changed dynamically:
     * <ul>
     * <li>{@link Message.BuiltinKeys#START_TIME}</li>
     * <li>{@link Message.BuiltinKeys#STOP_TIME}</li>
     * <li>{@link Message.BuiltinKeys#TIMEOUT}</li>
     * <li>{@link Message.BuiltinKeys#PRIORITY}</li>
     * <li>{@link Message.BuiltinKeys#SCHEDULE_EXPRESSION}</li>
     * </ul>
     *
     * @param messageId the id of message
     * @param headers the new headers
     */
    void updateMessage(String messageId, KeyValue headers);
}
