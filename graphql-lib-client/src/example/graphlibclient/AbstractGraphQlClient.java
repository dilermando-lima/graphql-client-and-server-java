package example.graphlibclient;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

public abstract class AbstractGraphQlClient implements BaseDomainGraphQlClient {

    static final Logger LOGGER = LoggerFactory.getLogger(AbstractGraphQlClient.class);
    
    private final String urlQraphQl;
    private static final HttpMethod DEFAULT_HTTP_METHOD = HttpMethod.POST;
    private RestTemplate restTemplate;
    

    private void configRestTemplate(List<ClientHttpRequestInterceptor> listClientHttpRequestInterceptor , ResponseErrorHandler responseErrorHandler ){

        restTemplate = new RestTemplate(new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()));

        if( listClientHttpRequestInterceptor != null ){
            restTemplate.getInterceptors().addAll(listClientHttpRequestInterceptor);
        }else{
            restTemplate.getInterceptors().add(new ClientHttpRequestInterceptorGraphQLDefault());
        }
        
        restTemplate.setErrorHandler(responseErrorHandler == null ?  new ResponseErrorHandlerGraphQLDefault() : responseErrorHandler);

       
    }

 
    protected AbstractGraphQlClient(String urlQraphQl, List<ClientHttpRequestInterceptor> listClientHttpRequestInterceptor , ResponseErrorHandler responseErrorHandler){
        if( urlQraphQl == null  ) throw new NullPointerException("urlQraphQl cannot be null");
        this.urlQraphQl = urlQraphQl;
        configRestTemplate(listClientHttpRequestInterceptor,responseErrorHandler);
    }

    protected AbstractGraphQlClient(String urlQraphQl){
        this(urlQraphQl, null, null);
    }

    protected AbstractGraphQlClient(String urlQraphQl, List<ClientHttpRequestInterceptor> listClientHttpRequestInterceptor){
        this(urlQraphQl, listClientHttpRequestInterceptor, null);
    }
    
    protected AbstractGraphQlClient(String urlQraphQl, ResponseErrorHandler responseErrorHandler){
        this(urlQraphQl, null, responseErrorHandler);
    }
    

    private RequestBody buildRequestBody(String queryCommand, String queryArg, String queryFields){
        final String queryRequestGraphQL = 
                    queryArg != null ?
                    String.format("query { %s ( %s ) { %s }}", queryCommand, queryArg, queryFields ) :
                    String.format("query { %s { %s }}", queryCommand, queryFields );

        LOGGER.debug("GraphQL query = {}" , queryRequestGraphQL);

        return new RequestBody(queryRequestGraphQL);
    }


    public <T> T query(String queryCommand , String queryFields , ParameterizedTypeReference<ResponseBody<T>> typeReference ){
        return query(queryCommand, null , queryFields, typeReference);
    }

    public <T> T query(String queryCommand, String queryArg , String queryFields , ParameterizedTypeReference<ResponseBody<T>> typeReference){

        if( queryCommand == null ) throw new NullPointerException();
        if( queryFields == null ) throw new NullPointerException();
        if( typeReference == null ) throw new NullPointerException();

        LOGGER.debug("GraphQL url = {}" , urlQraphQl);


        ResponseBody<T> responseEntity = restTemplate.exchange(
                                        urlQraphQl, 
                                        DEFAULT_HTTP_METHOD , 
                                        new HttpEntity<>(buildRequestBody(queryCommand, queryArg, queryFields)), 
                                        typeReference ).getBody();


        return   responseEntity != null && responseEntity.getData() != null ? responseEntity.getData().get(queryCommand) : null;
      
                        
    }

}
