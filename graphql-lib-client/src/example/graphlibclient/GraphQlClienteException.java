package example.graphlibclient;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;

public class GraphQlClienteException extends RuntimeException {

    private static final Pattern PATTERN_BAD_REQUEST = Pattern.compile("bad.*?request");
    private static final Pattern PATTERN_NOT_FOUND = Pattern.compile("not.*?found");
    private static final Pattern PATTERN_UNAUTHORIZED = Pattern.compile("(not|un).*?auth");
    private static final Pattern PATTERN_FORBIDDEN = Pattern.compile("(allow|forbid|proihib)");


    private final Map<String,Object> extensions = new HashMap<>(1);
    private final HttpStatus httpStatusCauculated;
  
    public GraphQlClienteException(String message, Map<String,Object> extensions , HttpStatus httpStatus ){
        super(message);
        if( extensions != null ) this.extensions.putAll(extensions);
        if( httpStatus != null ){
            this.httpStatusCauculated = httpStatus;
        }else{
            this.httpStatusCauculated = processHttpStatus();
        }
        
    }

    public GraphQlClienteException(String message, Map<String,Object> extensions ){
        this(message, extensions, null);
    }

    public GraphQlClienteException(String message){
        this(message, null, null);
    }

    public GraphQlClienteException(String message, HttpStatus httpStatus ){
        this(message, null, httpStatus);
    }

    public Map<String, Object> getExtensions() {
        return extensions;
    }


    public HttpStatus getHttpStatusCauculated() {
        return httpStatusCauculated;
    }

    private HttpStatus processHttpStatus(){

        final String allExtensions = extensions.values().stream().map(Object::toString).collect(Collectors.joining()).toLowerCase();
        if(  PATTERN_BAD_REQUEST.matcher(allExtensions).find())  
            return HttpStatus.BAD_REQUEST;

        if(  PATTERN_NOT_FOUND.matcher(allExtensions).find())
            return HttpStatus.NOT_FOUND;

        if(  PATTERN_UNAUTHORIZED.matcher(allExtensions).find())
            return HttpStatus.UNAUTHORIZED;

        if(  PATTERN_FORBIDDEN.matcher(allExtensions).find())
            return HttpStatus.FORBIDDEN;
    
        return  HttpStatus.INTERNAL_SERVER_ERROR;


    }
    

}
