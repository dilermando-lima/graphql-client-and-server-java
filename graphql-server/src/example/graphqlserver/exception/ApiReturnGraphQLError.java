package example.graphqlserver.exception;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;

import graphql.ErrorClassification;
import graphql.ErrorType;
import graphql.GraphQLError;
import graphql.language.SourceLocation;

public class ApiReturnGraphQLError implements GraphQLError{

    private final HttpStatus httpStatus;
    private final String message;

    public ApiReturnGraphQLError(ApiGraphQLException apiGraphQlException) {
        this.httpStatus = apiGraphQlException.getHttpStatus();
        this.message = apiGraphQlException.getMessage();
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

    @Override
    public String getMessage() {
        return this.message;
    }
    
}
