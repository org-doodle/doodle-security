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
package org.doodle.security.server;

import lombok.RequiredArgsConstructor;
import org.doodle.design.security.SecurityOperation;
import org.doodle.design.security.UserDto;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

@Controller
@MessageMapping("security")
@RequiredArgsConstructor
public class SecurityServerController implements SecurityOperation {
  private final SecurityServerMapper mapper;
  private final SecurityServerUserService userService;

  @MessageMapping("pull")
  @Override
  public Mono<UserDto> pull(String username) {
    return this.userService
        .findByUsername(username)
        .cast(SecurityServerUserEntity.class)
        .map(mapper::toProto)
        .onErrorMap(error -> new UsernameNotFoundException(username, error));
  }
}
