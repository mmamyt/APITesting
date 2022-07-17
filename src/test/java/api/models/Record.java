package api.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
@JsonInclude(JsonInclude.Include.NON_DEFAULT)

@Data
public class Record {
    private String id;
    private String createdTime;
    private MyFields fields;
}