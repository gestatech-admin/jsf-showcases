package hu.palkonyves.jsfshowcase.datatable;

import hu.palkonyves.jsfshowcase.business.Banana;
import hu.palkonyves.jsfshowcase.business.BananaStore;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@RequestScoped
@Named
public class SimpleDatatable {

    @Inject
    BananaStore store;

    public List<Banana> getBananas() {
	return store.getAllBanana();
    }
}
