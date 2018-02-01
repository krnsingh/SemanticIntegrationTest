package pagination;

import java.util.List;

public class Page {

    private List<Result> resultsPerPage;
    private Page next;
    private Page prev;
    private int number;


    public List<Result> getResultsPerPage() {
        return resultsPerPage;
    }

    public void setResultsPerPage(List<Result> resultsPerPage) {
        this.resultsPerPage = resultsPerPage;
    }

    public Page getNext() {
        return next;
    }

    public void setNext(Page next) {
        this.next = next;
    }

    public Page getPrev() {
        return prev;
    }

    public void setPrev(Page prev) {
        this.prev = prev;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
