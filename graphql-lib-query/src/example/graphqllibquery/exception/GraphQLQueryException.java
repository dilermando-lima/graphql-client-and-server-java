package example.graphqllibquery.exception;

public class GraphQLQueryException extends RuntimeException{

    public GraphQLQueryException(String message) {
        super(message);
    }
    public GraphQLQueryException(Throwable throwable) {
        super(throwable);
    }

    public GraphQLQueryException(Throwable throwable, String message) {
        super(message ,throwable);
    }

}
