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

import org.doodle.design.common.exception.InvalidResultException;
import org.doodle.design.common.util.ProtoUtils;
import org.doodle.design.security.SecurityMapper;
import org.doodle.design.security.SecurityPullReply;
import org.doodle.design.security.SecurityPullRequest;

public class SecurityClientMapper extends SecurityMapper {

  public SecurityClientUserDetails fromProtoOrThrow(SecurityPullReply reply) {
    if (reply.getResultCase() == SecurityPullReply.ResultCase.ERROR) {
      throw new InvalidResultException(ProtoUtils.fromProto(reply.getError()));
    }
    return new SecurityClientUserDetails(reply.getReply());
  }

  public SecurityPullRequest toRequest(String username) {
    return SecurityPullRequest.newBuilder().setUsername(username).build();
  }
}
