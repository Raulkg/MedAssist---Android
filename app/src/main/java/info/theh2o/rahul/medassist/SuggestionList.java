package info.theh2o.rahul.medassist;

/**
 * Created by Rahulkumat on 4/28/2016.
 */
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)

@JsonPropertyOrder({
        "suggestion"
})
public class SuggestionList {

    @JsonProperty("suggestion")
    private List<String> suggestion = new ArrayList<String>();

    /**
     *
     * @return
     * The suggestion
     */
    @JsonProperty("suggestion")
    public List<String> getSuggestion() {
        return suggestion;
    }

    /**
     *
     * @param suggestion
     * The suggestion
     */
    @JsonProperty("suggestion")
    public void setSuggestion(List<String> suggestion) {
        this.suggestion = suggestion;
    }

}