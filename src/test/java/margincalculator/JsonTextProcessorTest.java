package margincalculator;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import pagination.JsonTextProcessor;
import pagination.Page;
import pagination.Results;
import pagination.exception.InvalidPageSizeException;
import pagination.exception.JsonDataFormatException;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.fail;

public class JsonTextProcessorTest {

    @Test
    public void jsonTextProcessorValidFormatTest() {
        String fileContents = getTestFileAsStr("JsonTestDataFile.json");
        JsonTextProcessor jsonTextProcessor = new JsonTextProcessor();
        jsonTextProcessor.processData(fileContents, 3);
        assertTrue("No Exception should occur for valid json", true);
    }

    @Test
    public void jsonTextProcessorValidFormatEmptyDataTest() {
        String fileContents = getTestFileAsStr("JsonTestDataFileEmpty.json");
        JsonTextProcessor jsonTextProcessor = new JsonTextProcessor();
        List<Page> pages = jsonTextProcessor.processData(fileContents, 3);
        assertTrue("Pages should be zero for empty json", pages.size() == 0);
    }

    @Test(expected = JsonDataFormatException.class)
    public void jsonTextProcessorInvalidFormatTest() {
        String fileContents = getTestFileAsStr("JsonTestDataFileInvalid.json");
        JsonTextProcessor jsonTextProcessor = new JsonTextProcessor();
        jsonTextProcessor.processData(fileContents, 3);
        fail("Expected JsonDataFormatException for invalid json data");
    }

    @Test(expected = InvalidPageSizeException.class)
    public void jsonTextProcessorNoOfPagesForSize0Test() throws IOException {
        int pageSize = 0;
        compareNoOfPageForSize(pageSize);
    }

    @Test
    public void jsonTextProcessorNoOfPagesForSize1Test() throws IOException {
        int pageSize = 1;
        compareNoOfPageForSize(pageSize);
    }

    @Test
    public void jsonTextProcessorNoOfPagesForSize2Test() throws IOException {
        int pageSize = 2;
        compareNoOfPageForSize(pageSize);
    }

    @Test
    public void jsonTextProcessorNoOfPagesForSize3Test() throws IOException {
        int pageSize = 3;
        compareNoOfPageForSize(pageSize);
    }

    @Test
    public void jsonTextProcessorNoOfPagesForSize6Test() throws IOException {
        int pageSize = 6;
        compareNoOfPageForSize(pageSize);
    }

    @Test
    public void pageContentAndNextPrevTest() throws IOException {
        int pageSize = 3;
        String fileContents = getTestFileAsStr("JsonTestDataFile.json");
        ObjectMapper objectMapper = new ObjectMapper();
        Results results = objectMapper.readValue(fileContents, Results.class);

        JsonTextProcessor jsonTextProcessor = new JsonTextProcessor();
        List<Page> pages = jsonTextProcessor.processData(fileContents, pageSize);

        assertTrue("Previous of first page should be null", pages.get(0).getPrev() == null);
        assertTrue("Next of last page should be null", pages.get(pages.size() - 1).getNext() == null);
        assertTrue("Next of first page should be second page", pages.get(0).getNext().equals(pages.get(1)));

        assertTrue("Next of first page should be second page", pages.get(0).getNext().equals(pages.get(1)));
        assertTrue("Summary from page and results not matching",
                pages.get(0).getResultsPerPage().get(0).getSummary().equals(results.getResults().get(0).getSummary()));
    }

    private void compareNoOfPageForSize(int pageSize) throws IOException {
        String fileContents = getTestFileAsStr("JsonTestDataFile.json");
        ObjectMapper objectMapper = new ObjectMapper();
        Results results = objectMapper.readValue(fileContents, Results.class);
        JsonTextProcessor jsonTextProcessor = new JsonTextProcessor();
        List<Page> pages = jsonTextProcessor.processData(fileContents, pageSize);
        int noOfPages = results.getResults().size() / pageSize;
        if (pageSize > 1)
            assertTrue("Excepted no. of pages - " + noOfPages, pages.size() - 1 == noOfPages);
        else
            assertTrue("Excepted no. of pages - " + noOfPages, pages.size() == noOfPages);
    }

    private String getTestFileAsStr(final String fileName) {
        StringBuilder result = new StringBuilder("");
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(fileName).getFile());
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                result.append(line).append("\n");
            }
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.toString();
    }
}
