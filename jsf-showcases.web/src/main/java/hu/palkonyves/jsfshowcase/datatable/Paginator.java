package hu.palkonyves.jsfshowcase.datatable;

import java.util.ArrayList;
import java.util.List;

public class Paginator {

    private int recordsPerPage = 10;
    private int currentPage = 1;
    private int maxPage = 1;

    public Paginator(int recordsPerPage, int currentPage, int maxPage) {
	super();
	this.recordsPerPage = recordsPerPage;
	this.currentPage = currentPage;
	this.maxPage = maxPage;
    }

    public int getRecordsPerPage() {
	return recordsPerPage;
    }

    public void setRecordsPerPage(int recordsPerPage) {
	this.recordsPerPage = recordsPerPage;
    }

    public int getCurrentPage() {
	return currentPage;
    }

    public void setCurrentPage(int currentPage) {
	if (currentPage > maxPage) {
	    this.currentPage = maxPage;
	    return;
	}
	if (currentPage < 1) {
	    this.currentPage = 1;
	    return;
	}
	this.currentPage = currentPage;
    }

    public int getMaxPage() {
	return maxPage;
    }

    public void setMaxPage(int maxPage) {
	this.maxPage = maxPage;
    }

    public boolean isHasNext() {
	return currentPage < maxPage;
    }

    public boolean isHasPrevious() {
	return currentPage > 1;
    }

    public int next() {
	return currentPage = getNextPageNum();
    }

    public int previous() {
	return currentPage = getPreviousPageNum();
    }

    public int getPreviousPageNum() {
	return isHasPrevious() ? currentPage - 1 : currentPage;
    }

    public int getNextPageNum() {
	return isHasPrevious() ? currentPage + 1 : currentPage;
    }

    public List<Integer> getPages() {
	List<Integer> result = new ArrayList<Integer>(maxPage);
	for (int i = 1; i <= maxPage; i++) {
	    result.add(i);
	}
	return result;
    }

}
