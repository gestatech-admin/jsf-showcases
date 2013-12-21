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

    private Paginator p = new Paginator(2, 1, 1);

    public List<Banana> getBananas() {
	int countBananas = store.countBananas();
	int recordsPerPage = p.getRecordsPerPage();
	p.setMaxPage(countBananas / recordsPerPage + 1);
	int offset = (p.getCurrentPage() - 1) * recordsPerPage;
	return store.getBananas(offset, recordsPerPage);
    }

    public Paginator getP() {
	return p;
    }

    public void setP(Paginator p) {
	this.p = p;
    }

}
