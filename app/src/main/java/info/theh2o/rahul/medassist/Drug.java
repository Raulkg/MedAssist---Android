package info.theh2o.rahul.medassist;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)

@JsonPropertyOrder({
        "suggestionGroup"
})
public class Drug {

    @JsonProperty("suggestionGroup")
    private SuggestionGroup suggestionGroup;

    /**
     *
     * @return
     * The suggestionGroup
     */
    @JsonProperty("suggestionGroup")
    public SuggestionGroup getSuggestionGroup() {
        return suggestionGroup;
    }

    /**
     *
     * @param suggestionGroup
     * The suggestionGroup
     */
    @JsonProperty("suggestionGroup")
    public void setSuggestionGroup(SuggestionGroup suggestionGroup) {
        this.suggestionGroup = suggestionGroup;
    }

}