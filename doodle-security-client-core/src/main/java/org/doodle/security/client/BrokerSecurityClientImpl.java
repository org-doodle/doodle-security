/*
 * Copyright (c) 2022-present Doodle. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.doodle.security.client;

import org.doodle.broker.client.BrokerClientRSocketRequester;
import org.doodle.design.broker.frame.BrokerFrame;
import org.doodle.design.broker.frame.BrokerFrameMimeTypes;
import org.doodle.design.broker.frame.BrokerFrameUtils;
import org.doodle.design.security.SecurityPullReply;
import org.doodle.design.security.SecurityPullRequest;
import org.springframework.messaging.rsocket.RSocketRequester;
import reactor.core.publisher.Mono;

public class BrokerSecurityClientImpl implements SecurityClient {
  private final BrokerClientRSocketRequester requester;
  private final BrokerFrame brokerFrame;

  public BrokerSecurityClientImpl(
      BrokerClientRSocketRequester requester, SecurityClientProperties properties) {
    this.requester = requester;
    this.brokerFrame = BrokerFrameUtils.unicast(properties.getServer().getTags());
  }

  @Override
  public Mono<SecurityPullReply> pull(SecurityPullRequest request) {
    return route("security.pull").data(request).retrieveMono(SecurityPullReply.class);
  }

  protected RSocketRequester.RequestSpec route(String route) {
    return this.requester
        .route(route)
        .metadata(this.brokerFrame, BrokerFrameMimeTypes.BROKER_FRAME_MIME_TYPE);
  }
}
