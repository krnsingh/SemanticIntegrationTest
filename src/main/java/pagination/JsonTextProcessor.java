package pagination;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import pagination.exception.InvalidPageSizeException;
import pagination.exception.JsonDataFormatException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonTextProcessor implements FlatTextProcessor {

    public List<Page> processData(String data, int pageSize) {
        if(pageSize <= 0) {
            throw new InvalidPageSizeException("The page size cannot be <=0 : " + pageSize);
        }
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            final Results results = objectMapper.readValue(data, Results.class);
            List<Page> resultPages = doPagination(pageSize, results);
            setPrevNextOnPages(resultPages);
            return resultPages;
        } catch (IOException e) {
            throw new JsonDataFormatException();
        }
    }

    private List<Page> doPagination(int pageSize, Results results) {
        final List<List<Result>> splittedResults =
                Lists.partition(results.getResults(), pageSize);
        int pageNumber = 1;
        List<Page> resultPages = new ArrayList<>();
        for(List<Result> eachPageResults : splittedResults) {
            Page page = new Page();
            page.setResultsPerPage(eachPageResults);
            page.setNumber(pageNumber);
            resultPages.add(page);
            pageNumber++;
        }
        return resultPages;
    }

    private void setPrevNextOnPages(List<Page> resultPages) {
        for(int i = 0; i< resultPages.size(); i++ ) {
            Page page = resultPages.get(i);
            if( i == 0) {
                page.setPrev(null);
                page.setNext(resultPages.get(i+1));
            } else if( i == resultPages.size() -1 ) {
                page.setPrev(resultPages.get(i-1));
                page.setNext(null);
            } else {
                page.setPrev(resultPages.get(i-1));
                page.setNext(resultPages.get(i+1));
            }
        }
    }

}
