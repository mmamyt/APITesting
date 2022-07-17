package api.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;
@JsonInclude(JsonInclude.Include.NON_DEFAULT)

@Data
public class ResponseBody {
    List<Record> records;
}