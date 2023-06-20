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
package org.doodle.security.vaadin.views;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.doodle.boot.vaadin.views.MainLayout;
import org.doodle.security.client.SecurityClient;

@PermitAll
@Route(value = "security", layout = MainLayout.class)
@PageTitle("Doodle | Security")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SecurityView extends VerticalLayout {
  SecurityClient securityClient;

  public SecurityView(SecurityClient securityClient) {
    this.securityClient = securityClient;
  }
}
