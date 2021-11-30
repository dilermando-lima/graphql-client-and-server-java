package example.graphqllibquery.builder;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import example.graphqllibquery.exception.GraphQLQueryException;

public class QueryBuilder<T> {

    static final Logger LOGGER = LoggerFactory.getLogger(QueryBuilder.class);

    private final Class<T> typeClass;

    private final EntityManager entityManager;
    private final String queryJpql;
    private String addAfterQueryJpql;
    private String addBeforeQueryJpql;
    private Map<String,Object[]> mapFilterAndQuery;
    private TypedQuery<T> query;
    private Integer maxResult = null;
    private Integer offSet = null;

    private QueryBuilder(EntityManager entityManager, String queryJpql, Class<T> typeReturn){
        this.entityManager = entityManager;
        this.queryJpql = queryJpql;
        mapFilterAndQuery = new LinkedHashMap<>();
        typeClass = typeReturn;
    }

    public static <T> QueryBuilder<T> query(EntityManager entityManager , String queryJpql, Class<T> typeReturn){
        return new QueryBuilder<>(entityManager, queryJpql, typeReturn);
    }

    public QueryBuilder<T> addFilterAnd(String jpqlWhereToAddIntoQuery, Object... value ){
        if( jpqlWhereToAddIntoQuery == null || value == null )
            throw new GraphQLQueryException("%s.addFilterAnd(...) requires non-null arguments");

        mapFilterAndQuery.put(jpqlWhereToAddIntoQuery, value);
        return this;
    }

    public QueryBuilder<T> addFilterAnd(Map<String, Object[]> mapStatementAndValues){
        if( mapStatementAndValues == null ) return this;
        mapFilterAndQuery.putAll(mapStatementAndValues);
        return this;
    }

    public QueryBuilder<T> addAfterQuery(String addAfterQueryJpql){
        if( addAfterQueryJpql == null ) return this;

        this.addAfterQueryJpql = addAfterQueryJpql;
        return this;
    }

    public QueryBuilder<T> addBeforeQuery(String addBeforeQueryJpql){
        if( addBeforeQueryJpql == null ) return this;
        this.addBeforeQueryJpql = addBeforeQueryJpql;
        return this;
    }

    public QueryBuilder<T> maxResult(Integer maxResult){
        if( maxResult == null) return this;
        if( maxResult <= 0 ) maxResult = 1;
        this.maxResult = maxResult;
        return this;
    }

    public QueryBuilder<T> offSet(Integer offSet){
        if( offSet == null) return this;
        if( offSet < 0 ) offSet = 0;
        this.offSet = offSet;
        return this;
    }

    public String getSelectQueryBuilt(){

        String queryJpqlToHandleBuilt = "";

        if( addBeforeQueryJpql != null ) queryJpqlToHandleBuilt+= " " + addBeforeQueryJpql + " ";
        if( queryJpql != null ) queryJpqlToHandleBuilt+= " " + queryJpql + " ";

        if( !mapFilterAndQuery.isEmpty()){
            queryJpqlToHandleBuilt+=  " where ";
            queryJpqlToHandleBuilt+= " " + mapFilterAndQuery.keySet().stream().collect(Collectors.joining(" and ")) + " ";
        }

        if( addAfterQueryJpql != null ) queryJpqlToHandleBuilt+= " " + addAfterQueryJpql + " ";

        return getStringWithNumberedQuestionMark(queryJpqlToHandleBuilt);

    }

    private String getStringWithNumberedQuestionMark(String stringWithSimpleQuestionMark){

        final String keyToMarkFinalString = "#.F.M.S";

        stringWithSimpleQuestionMark+= keyToMarkFinalString;

        String[] stringSplitedByQuestionMark = stringWithSimpleQuestionMark.split("\\?");

        StringBuilder stringToNumberQuestionmark = new StringBuilder();
        stringToNumberQuestionmark.append(stringSplitedByQuestionMark[0]);

        for (int i = 1; i < stringSplitedByQuestionMark.length; i++) {
            stringToNumberQuestionmark.append(String.format(" ?%d %s ", i ,  stringSplitedByQuestionMark[i] ));
        }

        return stringToNumberQuestionmark.toString().replace(keyToMarkFinalString,"");
    }

    public List<Object> getFilterValues(){

        return new LinkedList<>(mapFilterAndQuery.values())
                    .stream()
                    .flatMap(Arrays::stream)
                    .collect(Collectors.toList());
    }

    private void build(){
        LOGGER.debug("calling build() : building query, filters and param values");

        final String  queryJpqlBuilt = getSelectQueryBuilt();

        LOGGER.debug("queryJpqlBuilt = {}", queryJpqlBuilt);
        query = entityManager.createQuery(queryJpqlBuilt, typeClass);

        List<Object> values = getFilterValues();
        
        for (int indexParameter = 0; indexParameter < values.size(); indexParameter++) {
            query.setParameter(indexParameter + 1, values.get(indexParameter));
            LOGGER.debug("param {} = {}", indexParameter + 1 ,  values.get(indexParameter));
        }

        if( maxResult != null ) query.setMaxResults(maxResult);

        if( offSet != null ) query.setFirstResult(offSet);

        LOGGER.debug("maxResult = {}, offSet = {} ", maxResult, offSet);
    }

    public List<T> getResultList(){
        LOGGER.debug("calling getResults()");
        try{  
            build();
            return query.getResultList();
        }catch(RuntimeException e ){
            throw new GraphQLQueryException(e);
        }   
    }

    public T getSingleValue(){
        LOGGER.debug("calling getSingleValue()");
        try{
            build();
            return query.getSingleResult();
        }catch(RuntimeException e ){
            throw new GraphQLQueryException(e);
        }
    }
    

    
}
