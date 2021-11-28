package example.graphqlserver.exception;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;

import graphql.ErrorClassification;
import graphql.ErrorType;
import graphql.GraphQLError;
import graphql.language.SourceLocation;

public class ApiGraphQlException extends RuntimeException implements GraphQLError{

    private final HttpStatus httpStatus;

    public ApiGraphQlException(HttpStatus httpStatus, Throwable throwable) {
        super(throwable);
        this.httpStatus = httpStatus;
    }

    public ApiGraphQlException(Throwable throwable) {
        super(throwable);
        this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public ApiGraphQlException(HttpStatus httpStatus, String message) {
        super(new Throwable(message) );
        this.httpStatus = httpStatus;
    }

    @Override
    public Map<String, Object> getExtensions() {
        return Map.of(ErrorType.ValidationError.name(),httpStatus.name());
    }

    @Override
    public ErrorClassification getErrorType() {
        return ErrorType.ValidationError;
    }

    @Override
    public List<SourceLocation> getLocations() {
        return Collections.emptyList();
    }
    
}
