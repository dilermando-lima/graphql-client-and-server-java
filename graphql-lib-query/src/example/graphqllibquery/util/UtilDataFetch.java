package example.graphqllibquery.util;

import java.util.LinkedList;
import java.util.List;

import graphql.schema.DataFetchingEnvironment;
import graphql.schema.SelectedField;

public class UtilDataFetch {

    public List<String> getListNameSelection(DataFetchingEnvironment dataFetchingEnvironment){
        List<String> listAttr = new LinkedList<>();        
        String previousName = null;
        String currentName = null;
        for ( SelectedField s  :  dataFetchingEnvironment.getSelectionSet().getFields() ) {
            if( previousName == null ){
                previousName = s.getQualifiedName().replace("/",".");
            }else if( currentName == null){
                currentName = s.getQualifiedName().replace("/",".");
            }else{
                if( !s.getQualifiedName().replace("/",".").startsWith(previousName + ".")){
                    listAttr.add(previousName);
                    currentName = previousName;
                    previousName = s.getQualifiedName().replace("/",".");
                }
            }
        }
        listAttr.add(previousName);
        return listAttr;
    }


    
}
