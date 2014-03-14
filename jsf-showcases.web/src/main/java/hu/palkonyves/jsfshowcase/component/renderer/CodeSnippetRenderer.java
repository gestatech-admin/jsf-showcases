package hu.palkonyves.jsfshowcase.component.renderer;

import hu.palkonyves.jsfshowcase.component.CodeSnippet;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;
import javax.faces.render.Renderer;

/**
 * Renderer class for {@link CodeSnippet}
 * <p>
 * Rendering of a component is usually separated from the component itself. This
 * separation allows more control between component logic/behavior/state and
 * it's representation. More importantly, it allows application developers to
 * fix bugs in 3rd party components by overriding it's Renderer
 * <p>
 * The {@link FacesRenderer} annotation connects the renderer to it's component
 * 
 * @author Pali
 */
@FacesRenderer(componentFamily = CodeSnippet.COMPONENT_FAMILY, rendererType = CodeSnippetRenderer.RENDERER_TYPE)
public class CodeSnippetRenderer extends Renderer {

	/*
	 * JSF usually uses java.util.Logger, so we do the same
	 */
	private static final Logger LOG = Logger
			.getLogger(CodeSnippetRenderer.class.getName());

	public static final String RENDERER_TYPE = "hu.palkonyves.jsfshowcase.component.renderer.CodeSnippetRenderer";

	/*
	 * Rendering phases are: encodeBegin, encodeChildren, encodeEnd
	 * 
	 * When a component must not have child elements, the rendering is usually
	 * in the encodeEnd() method, which outputs plain text (html)
	 */
	@Override
	public void encodeEnd(FacesContext context, UIComponent component)
			throws IOException {
		CodeSnippet codeSnippetComponent = (CodeSnippet) component;
		String fileName = codeSnippetComponent.getFile();
		Boolean showFileName = codeSnippetComponent.getShowSource();
		String sourceCodeText = "";

		try {
			sourceCodeText = codeSnippetComponent.getValue();
		} catch (IOException e) {

			/*
			 * Make a meaningful error message in the log file, but still allow
			 * to render the page.
			 * 
			 * The sourceCodeText will be the error message, so the end user
			 * will see it. This could be a security issue, but not finding a
			 * file with a relative path to the context root, doesn't seem to be
			 * so horrifying
			 */

			sourceCodeText = e.getMessage();
			LOG.log(Level.SEVERE, "Could not get value for "
					+ CodeSnippet.COMPONENT_TYPE + " " + fileName, e);

		}

		// context provides the response writer, where
		// we can directly write our html
		ResponseWriter writer = context.getResponseWriter();

		// component must start with the startElement() method
		writer.startElement("div", codeSnippetComponent);

		if (showFileName) {
			if (fileName != null) {

				// render the file name part
				writer.write("<span class='filename'>");
				writer.write(fileName);
				writer.write("</span>\n");

			} else {

				/*
				 * Meaningful log message about the inconsistent attribute
				 * setting.
				 * 
				 * Not throwing an exception still allows to load the page.
				 */
				LOG.log(Level.WARNING, CodeSnippet.ATTR_FILE
						+ " attribute is null but "
						+ CodeSnippet.ATTR_SHOW_SOURCE
						+ "attribute is true for " + CodeSnippet.COMPONENT_TYPE);

			}
		}

		// render the actual source code
		writer.write("<pre class='prettyprint'>\n");

		// writeText replaces html escape characters
		writer.writeText(sourceCodeText, null);
		writer.write("\n</pre>\n");

		writer.endElement("div");
	}
}
