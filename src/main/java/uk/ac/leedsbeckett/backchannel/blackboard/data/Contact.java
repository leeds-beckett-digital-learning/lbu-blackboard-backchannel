/*
 * Copyright 2023 maber01.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package uk.ac.leedsbeckett.backchannel.blackboard.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author maber01
 */
@JsonIgnoreProperties({ "homePhone", "mobilePhone", "businessPhone", 
                        "businessFax", "webPage"
                      })
public class Contact
{
  private final String email;
  private final String institutionalEmail;

  public Contact( 
          @JsonProperty( "email" )                String email, 
          @JsonProperty( "institutionalEmail" )   String institutionalEmail )
  {
    this.email = email;
    this.institutionalEmail = institutionalEmail;
  }

  public String getEmail()
  {
    return email;
  }

  public String getInstitutionalEmail()
  {
    return institutionalEmail;
  }  
}
