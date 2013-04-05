package utils;

import java.io.IOException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpResponseException;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.util.EntityUtils;
 
public class ResponseHandlerTr extends BasicResponseHandler {
 @Override
 public String handleResponse(HttpResponse response) throws HttpResponseException, IOException      {
    StatusLine statusLine = response.getStatusLine();
         if (statusLine.getStatusCode() >= 300) {
             throw new HttpResponseException(statusLine.getStatusCode(),
                     statusLine.getReasonPhrase());
         }
 
         HttpEntity entity = response.getEntity();
         return entity == null ? null : EntityUtils.toString(entity, "iso-8859-9");
 }
  
}