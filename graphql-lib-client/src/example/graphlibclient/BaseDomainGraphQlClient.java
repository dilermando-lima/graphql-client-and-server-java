package example.graphlibclient;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.ResponseErrorHandler;

public interface BaseDomainGraphQlClient {

    static final Logger LOGGER = LoggerFactory.getLogger(BaseDomainGraphQlClient.class);

    public class ClientHttpRequestInterceptorGraphQLDefault implements ClientHttpRequestInterceptor{

        @Override
        public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {

      
            LOGGER.debug("GraphQL request body = {} ", body == null ? null : new String(body, StandardCharsets.UTF_8));
            LOGGER.debug("GraphQL request uri = {} - {} ", request.getMethodValue() , request.getURI() );

            HttpHeaders headers = request.getHeaders();
            headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
            ClientHttpResponse response = execution.execute(request, body == null ? new byte[]{} : body );
            
            LOGGER.debug("GraphQL response code = {} ", response.getStatusCode().value());
            String responseAsString =  StreamUtils.copyToString(response.getBody(),  StandardCharsets.UTF_8);
            LOGGER.debug("GraphQL response body = {} ", responseAsString);

            return response;
        }

    }


    public class ResponseErrorHandlerGraphQLDefault implements ResponseErrorHandler{


        private <T> T convertJsonToObj(String json, Class<T> classReturn) throws JsonProcessingException{
            return new ObjectMapper()
                    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                    .registerModule(new JavaTimeModule())
                    .readValue(json, classReturn);
        }

        private ErrorResponseQraphQL checkHasErrorResponseQraphQL(String responseAsString ){
            boolean hasErrorListInResponse = responseAsString.replaceAll("[\\s\"\\\\]","").contains("errors:[");
            if( hasErrorListInResponse ){
                try {
                    return convertJsonToObj(responseAsString, ErrorResponseQraphQL.class);
                } catch (Exception e) {
                    return null;
                }   
            }else{
                return null;
            }
        }

        private void throwGraphQlClienteExceptionOnError(String responseAsString) {

            ErrorResponseQraphQL errorResponseQraphQL = checkHasErrorResponseQraphQL(responseAsString);

            if( errorResponseQraphQL != null ){

                LOGGER.debug("GraphQL errors[0].extensions = {}" , errorResponseQraphQL.getErrors().get(0).getExtensions());
                LOGGER.debug("GraphQL errors[0].message = {}" , errorResponseQraphQL.getErrors().get(0).getMessage());

                throw new GraphQlClienteException(
                                    errorResponseQraphQL.getErrors().get(0).getMessage() , 
                                    errorResponseQraphQL.getErrors().get(0).getExtensions() );
            }else{
                throw new GraphQlClienteException("GraphQL in throwGraphQlClienteExceptionOnError on response = " + responseAsString);
            }
        }

        @Override
        public boolean hasError(ClientHttpResponse response) throws IOException {
            return !response.getStatusCode().is2xxSuccessful() || checkHasErrorResponseQraphQL(StreamUtils.copyToString(response.getBody(), StandardCharsets.UTF_8)) != null;
        }

        @Override
        public void handleError(ClientHttpResponse response) throws IOException {

            String responseAsString = StreamUtils.copyToString(response.getBody(), StandardCharsets.UTF_8);

            if( !response.getStatusCode().is2xxSuccessful()  ){
                throw new GraphQlClienteException(
                                "GraphQl response error = " + responseAsString , 
                                response.getStatusCode()    
                            );
            }else{
                throwGraphQlClienteExceptionOnError(responseAsString);
            }
    
        }
    }

    public class ErrorResponseQraphQL{
        private List<ErrorDetailQraphQL> errors;

        public List<ErrorDetailQraphQL> getErrors() {
            return errors == null ? new ArrayList<>() : errors;
        }

        public void setErrors(List<ErrorDetailQraphQL> errors) {
            this.errors = errors;
        }
  
    }

    public class ErrorDetailQraphQL{
        private String message;
        private List<Map<String,Object>>  locations;
        private Map<String,Object>  extensions;
        private List<Object>  path;

        public String getMessage() {
            return message;
        }
        public void setMessage(String message) {
            this.message = message;
        }
        public List<Map<String, Object>> getLocations() {
            return locations == null ? new ArrayList<>() : locations;
        }
        public void setLocations(List<Map<String, Object>> locations) {
            this.locations = locations;
        }
        public Map<String, Object> getExtensions() {
            return extensions == null ? new HashMap<>() : extensions;
        }
        public void setExtensions(Map<String, Object> extensions) {
            this.extensions = extensions;
        }
        public List<Object> getPath() {
            return path;
        }
        public void setPath(List<Object> path) {
            this.path = path;
        }
  
    }

    public class RequestBody {
        private final String query;
        public RequestBody(String query) {
            this.query = query;
        }
        public String getQuery() { return query; }
    }


    public class ResponseBody<T>{

        @Override
        public String toString() {
            return "ResponseBody [data=" + data + "]";
        }
        private Map<String, T> data;
        
        public Map<String, T> getData() {
            return data;
        }
        public void setData(Map<String, T> data) {
            this.data = data;
        }
    }


}
