package hu.palkonyves.jsfshowcase;

import java.util.Collection;

import javax.el.ELContext;
import javax.el.MethodExpression;
import javax.faces.component.FacesComponent;
import javax.faces.component.UIInput;
import javax.faces.component.UINamingContainer;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

@FacesComponent("autoCompleteInput")
public class AutoCompleteInputComponent extends UINamingContainer {

	private static final String AUTO_COMPLETE_METHOD = "autoCompleteMethod";
	private UIInput value;
	private Collection<String> hints;

	@SuppressWarnings("unchecked")
	public void autoCompleteListener(AjaxBehaviorEvent event) {
		FacesContext context = FacesContext.getCurrentInstance();
		ELContext elContext = context.getELContext();

		Object params[] = new Object[] { value.getValue() };

		MethodExpression me = (MethodExpression) this.getAttributes().get(
				AUTO_COMPLETE_METHOD);
		hints = (Collection<String>) me.invoke(elContext, params);
	}

	public UIInput getValue() {
		return value;
	}

	public void setValue(UIInput value) {
		this.value = value;
	}

	public Collection<String> getHints() {
		return hints;
	}
}
