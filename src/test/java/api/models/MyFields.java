package api.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
@JsonInclude(JsonInclude.Include.NON_DEFAULT)

@Data
public class MyFields {
    String firstName;
    String lastName;
    String address;
    String city;
    String email;
    String phoneNumber;
    String student;
}