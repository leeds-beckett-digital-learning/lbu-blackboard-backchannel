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
package uk.ac.leedsbeckett.backchannel.blackboard;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import uk.ac.leedsbeckett.backchannel.Backchannel;
import uk.ac.leedsbeckett.backchannel.JsonResult;
import uk.ac.leedsbeckett.backchannel.OAuth2Token;
import uk.ac.leedsbeckett.backchannel.Parameter;
import uk.ac.leedsbeckett.backchannel.blackboard.data.Availability;
import uk.ac.leedsbeckett.backchannel.blackboard.data.CourseMembershipV1;
import uk.ac.leedsbeckett.backchannel.blackboard.data.CourseMembershipV1Input;
import uk.ac.leedsbeckett.backchannel.blackboard.data.GetCourseGroupUsersV2Results;
import uk.ac.leedsbeckett.backchannel.blackboard.data.GetCourseGroupsV2Results;
import uk.ac.leedsbeckett.backchannel.blackboard.data.GetCoursesV3Results;
import uk.ac.leedsbeckett.backchannel.blackboard.data.RestExceptionMessage;
import uk.ac.leedsbeckett.backchannel.blackboard.data.UserV1;

/**
 * One instance in the web application for each blackboard learn platform
 * we communicate with.
 * 
 * @author maber01
 */
public class BlackboardBackchannel extends Backchannel
{
  static final Logger logger = Logger.getLogger(BlackboardBackchannel.class.getName() );
  
  
  String platform;
  String username;
  String password;
  
  OAuth2Token blackboardAuthToken;

  public BlackboardBackchannel( BlackboardBackchannelKey key, String username, String password )
  {
    this.platform = key.getPlatform();
    this.username = username;
    this.password = password;
  }
  
  
  public synchronized OAuth2Token getAuthToken()
  {
    if ( blackboardAuthToken != null )
      return blackboardAuthToken;
        
    try
    {
      String url = "https://" + platform + "/learn/api/public/v1/oauth2/token";
      JsonResult jresult = this.postBlackboardRestTokenRequest( url, username, password );
      
      if ( jresult.isSuccessful() )
      {
        blackboardAuthToken = (OAuth2Token)jresult.getResult();
        return blackboardAuthToken;
      }
      return null;
    }
    catch ( IOException ex )
    {
      logger.log( Level.SEVERE, "IOException while trying to fetch auth token.", ex );
      return null;
    }
  }
 
  
  public JsonResult getV1Users( String userId )
  {
    if ( StringUtils.isBlank( userId ) )
      return null;

    OAuth2Token t = getAuthToken();
    String token = t.getAccessToken();
    String target = "https://" + platform + "/learn/api/public/v1/users/" + userId;
    ArrayList<NameValuePair> params = new ArrayList<>();

    try
    {
      return getBlackboardRest( target, token, params, UserV1.class, RestExceptionMessage.class );
    }
    catch ( IOException ex )
    {
    }
    return null;
  }
  
  public JsonResult getV3Courses( ArrayList<Parameter> params )
  {
    OAuth2Token t = getAuthToken();
    String token = t.getAccessToken();
    String target = "https://" + platform + "/learn/api/public/v3/courses";

    // Not sure if this is necessary?
    ArrayList<NameValuePair> immutableparams = new ArrayList<>();
    if ( params != null )
      for ( Parameter p : params )
        immutableparams.add( new BasicNameValuePair( p.getName(), p.getValue() ) );
        
    try
    {
      return getBlackboardRest( target, token, immutableparams, GetCoursesV3Results.class, RestExceptionMessage.class );
    }
    catch ( IOException ex )
    {
    }
    return null;
  }
  
  public JsonResult getV2CourseGroupSets( String courseId )
  {
    if ( StringUtils.isBlank( courseId ) )
      return null;
    
    OAuth2Token t = getAuthToken();
    String token = t.getAccessToken();
    String target = "https://" + platform + "/learn/api/public/v2/courses/uuid:" + courseId + "/groups/sets";
    ArrayList<NameValuePair> params = new ArrayList<>();
        
    try
    {
      return getBlackboardRest( target, token, params, GetCourseGroupsV2Results.class, RestExceptionMessage.class );
    }
    catch ( IOException ex )
    {
    }
    return null;
  }
  
  public JsonResult getV2CourseGroups( String courseId )
  {
    if ( StringUtils.isBlank( courseId ) )
      return null;
    
    OAuth2Token t = getAuthToken();
    String token = t.getAccessToken();
    String target = "https://" + platform + "/learn/api/public/v2/courses/uuid:" + courseId + "/groups";
    ArrayList<NameValuePair> params = new ArrayList<>();
        
    try
    {
      return getBlackboardRest( target, token, params, GetCourseGroupsV2Results.class, RestExceptionMessage.class );
    }
    catch ( IOException ex )
    {
    }
    return null;
  }
  
  public JsonResult getV2CourseGroupSetGroups( String courseId, String groupId )
  {
    if ( StringUtils.isBlank( courseId ) || StringUtils.isBlank( groupId ) )
      return null;
    
    OAuth2Token t = getAuthToken();
    String token = t.getAccessToken();
    String target = "https://" + platform + "/learn/api/public/v2/courses/uuid:" + courseId + 
            "/groups/sets/" + groupId + "/groups";
    ArrayList<NameValuePair> params = new ArrayList<>();
        
    try
    {
      return getBlackboardRest( target, token, params, GetCourseGroupsV2Results.class, RestExceptionMessage.class );
    }
    catch ( IOException ex )
    {
    }
    return null;
  }
  
  public JsonResult getV2CourseGroupUsers( String courseId, String groupId )
  {
    if ( StringUtils.isBlank( courseId ) || StringUtils.isBlank( groupId ) )
      return null;
    
    OAuth2Token t = getAuthToken();
    String token = t.getAccessToken();
    String target = "https://" + platform + "/learn/api/public/v2/courses/uuid:" + courseId + 
            "/groups/" + groupId + "/users";
    ArrayList<NameValuePair> params = new ArrayList<>();
        
    try
    {
      return getBlackboardRest( target, token, params, GetCourseGroupUsersV2Results.class, RestExceptionMessage.class );
    }
    catch ( IOException ex )
    {
    }
    return null;
  }
  
  public JsonResult putV1CourseMemberships( String courseId, String userId, CourseMembershipV1Input cmi )
  {
    if ( StringUtils.isBlank( courseId ) )
      return null;
    
    OAuth2Token t = getAuthToken();
    String token = t.getAccessToken();
    String target = "https://" + platform + 
            "/learn/api/public/v1/courses/externalId:" + courseId + "/users/uuid:" + userId;

    ObjectMapper mapper = new ObjectMapper();
    try
    {
      String json = mapper.writeValueAsString( cmi );
      logger.log( Level.INFO, json );
      return putBlackboardRest( target, token, json, CourseMembershipV1.class, RestExceptionMessage.class );
    }
    catch ( IOException ex )
    {
    }
    return null;
  }

}
