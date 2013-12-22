package hu.palkonyves.jsfshowcase;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class Index {

    @Inject
    SourceCodeReader sourceCodeReader;

    /**
     * @return list of xhtml pages within the war
     */
    public List<String> getPages() {
        return sourceCodeReader.getPages();
    }

}
