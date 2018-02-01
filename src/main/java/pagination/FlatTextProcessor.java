package pagination;

import java.util.List;

public interface FlatTextProcessor {

    List<Page> processData(String data, int pageSize);
}
