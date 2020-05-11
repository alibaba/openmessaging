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

package io.openmessaging.samples.producer;

import io.openmessaging.api.MessagingAccessPoint;
import io.openmessaging.api.OMS;
import io.openmessaging.api.OMSBuiltinKeys;
import io.openmessaging.api.OMSProducer;
import io.openmessaging.api.OnExceptionContext;
import io.openmessaging.api.SendCallback;
import io.openmessaging.api.SendResult;
import io.openmessaging.samples.User;
import java.util.Properties;

public class SchemaProducerApp {
    public static void main(String[] args) {
        final MessagingAccessPoint messagingAccessPoint =
            OMS.builder()
                .region("shanghai,shenzhen")
                .endpoint("127.0.0.1:9876")
                .driver("rocketmq")
                .withCredentials(new Properties())
                .build();

        Properties properties = new Properties();
        properties.setProperty(OMSBuiltinKeys.SERIALIZER, "io.openmessaging.openmeta.impl.Serializer");
        properties.setProperty(OMSBuiltinKeys.OPEN_META_URL, "http://localhost:1234");
        final OMSProducer<User> producer = messagingAccessPoint.createOMSProducer(properties);

        producer.start();

        //Register a shutdown hook to close the opened endpoints.
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                producer.shutdown();
            }
        }));

        User user = new User();

        SendResult sendResult = producer.send("testTopic", user);
        System.out.println("SendResult: " + sendResult);

        //Sends a message to the specified destination async.
        producer.sendAsync("testTopic", user, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                System.out.println("SendResult: " + sendResult);
            }

            @Override
            public void onException(OnExceptionContext context) {

            }
        });

        //Sends a message to the specified destination in one way mode.
        producer.sendOneway("testTopic", user);

        producer.shutdown();
    }
}
