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

import java.util.stream.Collectors;
import org.doodle.design.security.SecurityMapper;
import org.doodle.design.security.UserDetailsInfo;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public final class SecurityServerMapper extends SecurityMapper {

  public UserDetailsInfo toProto(SecurityServerUserEntity userEntity) {
    return UserDetailsInfo.newBuilder()
        .setUsername(userEntity.getUsername())
        .setPassword(userEntity.getPassword())
        .setEnable(userEntity.isEnabled())
        .addAllAuthorities(
            userEntity.getAuthorities().stream()
                .map(SimpleGrantedAuthority::getAuthority)
                .collect(Collectors.toList()))
        .build();
  }
}
