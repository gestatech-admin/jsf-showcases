package hu.palkonyves.jsfshowcase;

import java.util.ArrayList;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIOutput;
import javax.faces.component.UIParameter;
import javax.inject.Named;

@Named
@RequestScoped
public class AttributeParam {
    
    /**
     * For bindind h:outputText
     */
    private UIOutput someOutputText;
    
    /**
     * Just an ordinary serializable object
     */
    private ArrayList<String> arrayList;

    public ArrayList<String> getArrayList() {
        
        // We init the list here upon calling the getter
        if (arrayList == null) {
            arrayList = new ArrayList<String>();
            arrayList.add("one");
            arrayList.add("two");
        }
        
        return arrayList;
    }

    public UIOutput getSomeOutputText() {
        return someOutputText;
    }

    public void setSomeOutputText(UIOutput someOutputText) {
        this.someOutputText = someOutputText;
    }

    // snippet=1
    public void testComponent() {
        Map<String, Object> attributes = someOutputText.getAttributes();
        ArrayList<String> arrayList = (ArrayList<String>) attributes.get("attribute1");
        
        StringBuilder outputValueBuilder = new StringBuilder();
        if (getArrayList().equals(arrayList)) {
            outputValueBuilder.append("attribute1=").append(arrayList).append(" ");
        }
        else {
            throw new IllegalStateException("attribute1 was not set properly");            
        }
        
        UIParameter param1 = (UIParameter) someOutputText.findComponent("param1");
        
        if (param1 != null) {
            outputValueBuilder.append("param1=").append(param1.getValue());
        }
        else {
            throw new IllegalStateException("param1 was not set properly");            
        }
        
        someOutputText.setValue(outputValueBuilder.toString());
    }
    // endsnippet

}
