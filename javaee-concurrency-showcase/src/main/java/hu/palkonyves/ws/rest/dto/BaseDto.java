package hu.palkonyves.ws.rest.dto;

import java.util.ArrayList;
import java.util.List;

public class BaseDto {

    private List<String> infos = new ArrayList<String>();
    private List<String> warnings = new ArrayList<String>();
    private List<String> errors = new ArrayList<String>();

    public List<String> getInfos() {
        return infos;
    }

    public List<String> getWarnings() {
        return warnings;
    }

    public List<String> getErrors() {
        return errors;
    }

}
