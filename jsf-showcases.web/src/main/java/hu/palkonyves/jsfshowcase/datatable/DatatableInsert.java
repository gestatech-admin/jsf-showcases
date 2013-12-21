package hu.palkonyves.jsfshowcase.datatable;

import hu.palkonyves.jsfshowcase.business.Banana;
import hu.palkonyves.jsfshowcase.business.BananaStore;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@RequestScoped
@Named
public class DatatableInsert {

    @Inject
    BananaStore store;

    private Banana newBanana = new Banana("", 0, 100);

    public Banana getNewBanana() {
	return newBanana;
    }

    public void setNewBanana(Banana newBanana) {
	this.newBanana = newBanana;
    }

    public String createBananaAction() {
	store.createBanana(newBanana);
	newBanana.setName("");
	newBanana.setBrowness(100);
	newBanana.setYellowness(0);
	return "dataTableInsert.xhtml?faces-redirect=true";
    }
}
