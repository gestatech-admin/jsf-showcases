package hu.palkonyves.jsfshowcase;

import hu.palkonyves.jsfshowcase.business.Banana;
import hu.palkonyves.jsfshowcase.business.BananaStore;

import java.util.List;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class PassingParamsExample {

	public static enum Action {
		EDIT, DELETE, NEW, SHOW
	}

	BananaStore bananaStore;
	private Long bananaId;
	private Action action;

	protected PassingParamsExample() {
	}

	@Inject
	public PassingParamsExample(BananaStore bananaStore) {
		this.bananaStore = bananaStore;
	}

	public void showEdit(ActionEvent event) {
		Map<String, String> requestParameterMap = FacesContext
				.getCurrentInstance().getExternalContext()
				.getRequestParameterMap();
		String strAction = requestParameterMap.get("action");
		String strBananaId = requestParameterMap.get("bananaId");

		action = Action.valueOf(strAction);
		bananaId = Long.getLong(strBananaId);
	}

	public void showDelete(ActionEvent event) {
		Map<String, Object> attributes = event.getComponent().getAttributes();

		String strAction = (String) attributes.get("bananaAction");
		bananaId = (Long) attributes.get("bananaId");
		action = Action.valueOf(strAction);
	}

	public Banana getCurrentBanana() {
		return bananaStore.findBanana(bananaId);
	}

	public List<Banana> getAllBananas() {
		return bananaStore.getAllBanana();
	}

	public Long getBananaId() {
		return bananaId;
	}

	public void setBananaId(Long bananaId) {
		this.bananaId = bananaId;
	}

	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
	}

	public void setActionStr(String action) {
		this.action = Action.valueOf(action);
	}

}
