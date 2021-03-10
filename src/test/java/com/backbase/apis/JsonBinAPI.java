package com.backbase.apis;

import com.fasterxml.jackson.core.type.TypeReference;
import com.json.model.bin.Bin;
import com.json.model.bin.Record;
import helper.GetTestProperties;
import helper.ReadDataFromFile;
import io.restassured.response.Response;
import net.serenitybdd.core.Serenity;
import net.thucydides.core.annotations.Step;
import org.json.JSONObject;
import java.io.IOException;

import static net.serenitybdd.rest.SerenityRest.given;

public class JsonBinAPI extends BasePage {

    GetTestProperties testProperties =new GetTestProperties();

    /**
     * Create bin using below parameters
     * @param fileName
     * @return Bin
     */
    @Step
    public Bin createBin(String fileName){
        String requestBody= ReadDataFromFile.fetch(fileName+".json");
        Response response = given()
                .header("Content-Type","application/json")
                .header("X-Master-Key",Serenity.sessionVariableCalled("TokenForSession").toString())
                .when().log().all().body(requestBody).post("/b");
        JSONObject rawResponse = new JSONObject(response.getBody().asString());
        Bin createdBin=getBin(rawResponse.toString());
        int responseStatusCode=response.getStatusCode();
        if(responseStatusCode==200){
            testProperties.setValue(fileName+"Bin",createdBin.getMetadata().getId());
            Serenity.setSessionVariable("IdForSession").to(createdBin.getMetadata().getId());

        }
        Serenity.setSessionVariable("CreateBinResponseCode").to(responseStatusCode);
        Serenity.setSessionVariable("CreateBinResponse").to(rawResponse);
        return createdBin;
    }

    /**
     * Update bin using the id
     * @param oldFileName to fetch Id
     * @param newFileName to update the data
     * @return Bin
     */
    @Step
    public Bin updateBin(String oldFileName,String newFileName){
        String id=testProperties.getValue(oldFileName+"Bin");
        Serenity.setSessionVariable("IdForSession").to(id);
        String requestBody= ReadDataFromFile.fetch(newFileName+".json");
        Response response = given()
                .header("Content-Type","application/json")
                .header("X-Master-Key",Serenity.sessionVariableCalled("TokenForSession").toString())
                .when().log().all().body(requestBody).put("/b/"+id);
        JSONObject rawResponse = new JSONObject(response.getBody().asString());
        Bin updatedBin=getBin(rawResponse.toString());
        int responseStatusCode=response.getStatusCode();
        Serenity.setSessionVariable("UpdateBinResponseCode").to(responseStatusCode);
        Serenity.setSessionVariable("UpdateBinResponse").to(rawResponse);
        return updatedBin;
    }

    /**
     * Delete Bin using id
     * @param id
     */
    @Step
    public void deleteBin(String id){
        Response response = given()
                .header("Content-Type","application/json")
                .header("X-Master-Key",Serenity.sessionVariableCalled("TokenForSession").toString())
                .when().log().all().delete("/b/"+id);
        int responseStatusCode=response.getStatusCode();
        Serenity.setSessionVariable("DeleteBinResponseCode").to(responseStatusCode);
    }

    /**
     * Retrieve an Bin using the id
     * @param id
     * @return
     */
    @Step
    public Bin getBinWithId(String id){
        Response response = given()
                .header("Content-Type","application/json")
                .header("X-Master-Key",Serenity.sessionVariableCalled("TokenForSession").toString())
                .when().log().all().get("/b/"+id);
        int responseStatusCode=response.getStatusCode();
        Serenity.setSessionVariable("GetBinResponseCode").to(responseStatusCode);
        if(responseStatusCode==200){
            JSONObject rawResponse = new JSONObject(response.getBody().asString());
            Serenity.setSessionVariable("GetArticleUserResponse").to(rawResponse);
            return getBin(rawResponse.toString());
        }else{
            return new Bin();
        }
    }

    /**
     * Data verification of input file with API response body
     * @param fileName
     * @return
     */
    public boolean verifyTheData(String fileName){
        Record dataFromFile= getRecord(ReadDataFromFile.fetch(fileName+".json"));
        return getBinWithId(Serenity.sessionVariableCalled("IdForSession")).getRecord().equals(dataFromFile);
    }

    /**
     * Contract validation of a Bin
     * @param response
     * @return
     */
    private Bin getBin(String response){
        try {
            return mapper.readValue(response, new TypeReference<Bin>() {
            });
        } catch (IOException e) {
            throw new IllegalArgumentException("Profile is not as per the Contract" + e.getMessage());
        }
    }

    /**
     * Contract validation of a record
     * @param response
     * @return
     */
    private Record getRecord(String response){
        try {
            return mapper.readValue(response, new TypeReference<Record>() {
            });
        } catch (IOException e) {
            throw new IllegalArgumentException("Profile is not as per the Contract" + e.getMessage());
        }
    }

    /**
     * Set token for the session
     * @param tokenType
     */
    public void setTokenForSession(String tokenType){
        Serenity.setSessionVariable("TokenForSession").to(testProperties.getValue(tokenType.toLowerCase()+"Token"));
    }

    /**
     * Set Id for session to read, update or delete a record
     * @param filename
     */
    public void setIdForSession(String filename){
        String id=testProperties.getValue(filename+"Bin");
        Serenity.setSessionVariable("IdForSession").to(id);
    }


}
