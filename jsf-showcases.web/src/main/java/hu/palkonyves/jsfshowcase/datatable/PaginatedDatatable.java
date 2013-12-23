package hu.palkonyves.jsfshowcase.datatable;

import hu.palkonyves.jsfshowcase.business.Banana;
import hu.palkonyves.jsfshowcase.business.BananaStore;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@RequestScoped
@Named
public class PaginatedDatatable {

    @Inject
    BananaStore store;
    private int currentPage = 1;
    private int recordsPerPage = 2;

    private Paginator paginator = new Paginator(currentPage, recordsPerPage, 0);

    public List<Banana> getBananas() {
        paginator.setCurrentPage(currentPage);
        paginator.setRecordsPerPage(recordsPerPage);

        int countBananas = store.countBananas();
        int recordsPerPage = paginator.getRecordsPerPage();
        paginator.setRecords(countBananas);
        int offset = (paginator.getCurrentPage() - 1) * recordsPerPage;
        return store.getBananas(offset, recordsPerPage);
    }

    public Paginator getP() {
        return paginator;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getRecordsPerPage() {
        return recordsPerPage;
    }

    public void setRecordsPerPage(int recordsPerPage) {
        this.recordsPerPage = recordsPerPage;
    }
}
