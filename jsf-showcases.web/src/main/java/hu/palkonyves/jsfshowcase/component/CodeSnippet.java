package hu.palkonyves.jsfshowcase.component;

import hu.palkonyves.jsfshowcase.SourceCodeReader;

import java.io.IOException;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import javax.faces.application.Application;
import javax.faces.component.FacesComponent;
import javax.faces.component.StateHelper;
import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;

/**
 * Code snippet component shows the content of a file or part of a file syntax
 * highlighted.
 * 
 * <p>
 * Depends on {@link SourceCodeReader}
 * 
 * @author Pali
 */
@FacesComponent(CodeSnippet.COMPONENT_TYPE)
public class CodeSnippet extends UIComponentBase {

	/*
	 * Attribute name declarations and default values
	 */
	public static final String ATTR_SNIPPET = "snippet";
	public static final String ATTR_SNIPPET_DEFAULT = null;

	public static final String ATTR_FILE = "file";
	public static final String ATTR_FILE_DEFAULT = null;

	public static final Boolean ATTR_SHOW_SOURCE_DEFAULT = false;
	public static final String ATTR_SHOW_SOURCE = "showSource";

	/*
	 * Component family and component type (name) declaration
	 */
	public static final String COMPONENT_FAMILY = "jsf-showcases";
	public static final String COMPONENT_TYPE = "CodeSnippet";

	/**
	 * EL expression to get the {@link SourceCodeReader} bean
	 */
	private static final String SOURCE_CODE_READER_BEAN = "#{sourceCodeReader}";

	@Override
	public String getFamily() {
		return COMPONENT_FAMILY;
	}

	/**
	 * @return the <i>showSource</i> attribute
	 */
	public Boolean getShowSource() {
		StateHelper stateHelper = getStateHelper();
		/*
		 * always use the StateHelper class to store component state
		 */
		return (Boolean) stateHelper.eval(ATTR_SHOW_SOURCE,
				ATTR_SHOW_SOURCE_DEFAULT);
	}

	/**
	 * @param showSource
	 *            attribute
	 */
	public void setShowSource(Boolean showSource) {
		StateHelper stateHelper = getStateHelper();
		stateHelper.put(ATTR_SHOW_SOURCE, showSource);
	}

	/**
	 * @return the <i>file</i> attribute
	 */
	public String getFile() {
		StateHelper stateHelper = getStateHelper();
		return (String) stateHelper.eval(ATTR_FILE, ATTR_FILE_DEFAULT);
	}

	/**
	 * @param file
	 *            attribute
	 */
	public void setFile(String file) {
		StateHelper stateHelper = getStateHelper();
		stateHelper.put(ATTR_FILE, file);
	}

	/**
	 * @return the <i>snippet</i> attribute
	 */
	public String getSnippet() {
		StateHelper stateHelper = getStateHelper();
		return (String) stateHelper.eval(ATTR_SNIPPET, ATTR_SNIPPET_DEFAULT);
	}

	/**
	 * @param snippet
	 *            attribute
	 */
	public void setSnippet(String snippet) {
		StateHelper stateHelper = getStateHelper();
		stateHelper.put(ATTR_SNIPPET, snippet);
	}

	/**
	 * @return the file content defined by the <i>file</i> attribute , or the
	 *         <i>snippet</i> within the <i>file</i> if defined
	 * @throws IOException
	 */
	public String getValue() throws IOException {
		SourceCodeReader codeReader = getSourceCodeReader();
		String file = getFile();
		String number = getSnippet();

		if (file == null) {
			throw new NullPointerException(ATTR_FILE + " attribute for "
					+ COMPONENT_TYPE + " component is null");
		}

		// if number is null, it means we want the whole file
		String snippet = codeReader.getSnippet(file, number);
		return snippet;
	}

	/**
	 * @return the {@link SourceCodeReader} object via ELExpression lookup
	 */
	protected SourceCodeReader getSourceCodeReader() {
		/*
		 * This is the formal way how we resolve EL expressions programmatically
		 * from JSF
		 */

		FacesContext facesContext = getFacesContext();
		Application application = facesContext.getApplication();
		ELContext elContext = facesContext.getELContext();
		ExpressionFactory expressionFactory = application
				.getExpressionFactory();

		ValueExpression valueExpression = expressionFactory
				.createValueExpression(elContext, SOURCE_CODE_READER_BEAN,
						SourceCodeReader.class);

		SourceCodeReader codeReader = (SourceCodeReader) valueExpression
				.getValue(elContext);

		return codeReader;
	}

}
