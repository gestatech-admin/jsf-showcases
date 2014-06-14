package hu.palkonyves.jsfshowcase;

import hu.palkonyves.jsfshowcase.business.Banana;
import hu.palkonyves.jsfshowcase.business.BananaStore;

import javax.enterprise.context.RequestScoped;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class ViewParamExample {

	BananaStore bananaStore;

	private Banana banana;
	private Long bananaId;

	protected ViewParamExample() {
		// to be CDI proxyable
	}

	@Inject
	public ViewParamExample(BananaStore bananaStore) {
		this.bananaStore = bananaStore;
	}

	public void load(ValueChangeEvent e) {
		/*
		 * ValueChangeListeners are called before setting the new value so the
		 * new value can be retrieved from the event instead of the bananaId
		 * field
		 */
		Long newId = (Long) e.getNewValue();
		if (newId == null) {
			throw new IllegalArgumentException("bananaId must not be null");
		}
		banana = bananaStore.findBanana(newId);
	}

	public Long getBananaId() {
		return bananaId;
	}

	public void setBananaId(Long bananaId) {
		this.bananaId = bananaId;
	}

	public Banana getBanana() {
		return banana;
	}

	public void setBanana(Banana banana) {
		this.banana = banana;
	}

}
