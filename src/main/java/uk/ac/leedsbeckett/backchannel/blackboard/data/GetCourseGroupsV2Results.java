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

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a response from Blackboard Learn API with results of course
 * search or list.
 * 
 * @author maber01
 */
public class GetCourseGroupsV2Results implements Serializable
{
  ArrayList<GroupV2> results;
  PagingInfo pagingInfo;

  public GetCourseGroupsV2Results( 
          @JsonProperty( "results" )    ArrayList<GroupV2> results,
          @JsonProperty( "pagingInfo" ) PagingInfo pagingInfo )
  {
    this.results = results;
  }

  public List<GroupV2> getResults()
  {
    return results;
  }

  public PagingInfo getPagingInfo()
  {
    return pagingInfo;
  }
}
