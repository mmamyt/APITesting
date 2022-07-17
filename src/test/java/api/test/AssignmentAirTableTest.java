package api.test;

import api.models.MyFields;
import api.models.Record;
import api.models.RequestBody;
import api.models.ResponseBody;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Assert;
import org.testng.annotations.Test;
import utilities.Config;

import java.util.ArrayList;
import java.util.List;

public class AssignmentAirTableTest {

    @Test (priority = 1)
    public void getRecords() throws Exception {

        Response response = RestAssured.given()
                .header("Authorization", "Bearer keyGdE3IZspgLZ5C3")
                .urlEncodingEnabled(false)
                .get(Config.getProperty("host"));
        System.out.println(response.getStatusCode());

        ObjectMapper obj = new ObjectMapper();
        ResponseBody rb = obj.readValue(response.asString(), ResponseBody.class);
        Assert.assertEquals(response.statusCode(), 200);

        for (Record elements : rb.getRecords()) {
            if (elements.getFields().getFirstName().startsWith("C")) {
                System.out.println(elements.getFields().getFirstName()
                        + " " + elements.getFields().getLastName()
                        + " " + elements.getFields().getAddress()
                        + " " + elements.getFields().getCity()
                        + " " + elements.getFields().getEmail()
                        + " " + elements.getFields().getPhoneNumber()
                        + " " + elements.getFields().getStudent()
                );
            }
        }
    }

    String newID;
    @Test (priority = 2)
    public void postRecords() throws JsonProcessingException {
        //JCheckBox checkbox = new JCheckBox();
        Faker faker = new Faker();
        MyFields fields = new MyFields();
        fields.setFirstName(faker.name().firstName());
        fields.setLastName(faker.name().lastName());
        fields.setAddress(faker.address().streetAddress());
        fields.setCity(faker.address().city());
        fields.setEmail(faker.internet().emailAddress());
        fields.setPhoneNumber(faker.phoneNumber().cellPhone());
        fields.setStudent(null);

        Record record = new Record();
        record.setFields(fields);

        List<Record> records = new ArrayList<>();
        records.add(record);

        RequestBody requestBody = new RequestBody();
        requestBody.setRecords(records);

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonValue = objectMapper.writeValueAsString(requestBody);
        System.out.println(jsonValue);

        Response response = RestAssured.given()
                .header("Authorization", "Bearer keyGdE3IZspgLZ5C3")
                .urlEncodingEnabled(false)
                .contentType("application/json")
                .body(jsonValue)
                .post(Config.getProperty("host"));
        System.out.println(response.getStatusCode());
        System.out.println(response.asString());

        ResponseBody rb = objectMapper.readValue(response.asString(), ResponseBody.class);

        newID = rb.getRecords().get(0).getId();
        Assert.assertEquals(response.statusCode(), 200);
    }

    @Test (priority = 3)
    public void updateRecords() throws JsonProcessingException {
        MyFields fields = new MyFields();
        fields.setCity("Chicago");

        Record record = new Record();
        record.setFields(fields);
        record.setId(newID);

        List<Record> records = new ArrayList<>();
        records.add(record);

        RequestBody requestBody = new RequestBody();
        requestBody.setRecords(records);

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonValue = objectMapper.writeValueAsString(requestBody);
        System.out.println(jsonValue);

        Response response = RestAssured.given()
                .header("Authorization", "Bearer keyGdE3IZspgLZ5C3")
                .urlEncodingEnabled(false)
                .contentType("application/json")
                .body(jsonValue)
                .patch(Config.getProperty("host"));
        System.out.println(response.statusCode());
        Assert.assertEquals(response.statusCode(), 200);

    }

    @Test (priority = 4)
    public void deleteRecords() throws Exception {
        String queryParam = "records[]";

        Response response = RestAssured.given()
                .header("Authorization", "Bearer keyGdE3IZspgLZ5C3")
                .urlEncodingEnabled(false)
                .queryParam(queryParam, newID)
                .delete(Config.getProperty("host"));

        System.out.println(response.statusCode());
        System.out.println(response.asString());
        Assert.assertEquals(response.statusCode(), 200);
    }

}
