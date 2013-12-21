package hu.palkonyves.jsfshowcase.datatable;

import hu.palkonyves.jsfshowcase.business.Banana;
import hu.palkonyves.jsfshowcase.business.BananaStore;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@RequestScoped
@Named
public class EditableDatatable {

    @Inject
    BananaStore store;

    public String saveAction(Banana banana) {
	store.changeBanana(banana);
	return "editableDatatable.xhtml?faces-redirect=true";
    }
}
