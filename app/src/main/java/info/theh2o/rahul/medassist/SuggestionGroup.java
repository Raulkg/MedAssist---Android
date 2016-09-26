package info.theh2o.rahul.medassist;

/**
 * Created by Rahulkumat on 4/28/2016.
 */

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)

@JsonPropertyOrder({
        "name",
        "suggestionList"
})
public class SuggestionGroup {

    @JsonProperty("name")
    private String name;
    @JsonProperty("suggestionList")
    private SuggestionList suggestionList;

    /**
     *
     * @return
     * The name
     */
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     * The name
     */
    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     * The suggestionList
     */
    @JsonProperty("suggestionList")
    public SuggestionList getSuggestionList() {
        return suggestionList;
    }

    /**
     *
     * @param suggestionList
     * The suggestionList
     */
    @JsonProperty("suggestionList")
    public void setSuggestionList(SuggestionList suggestionList) {
        this.suggestionList = suggestionList;
    }

}