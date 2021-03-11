package com.jsonBin.stepDefinitions;

import com.jsonBin.apis.BasePage;
import com.jsonBin.apis.JsonBinAPI;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.RestAssured;
import net.serenitybdd.core.Serenity;
import net.thucydides.core.annotations.Steps;

import static org.assertj.core.api.Assertions.assertThat;

public class JsonBinSteps {

    @Steps
    JsonBinAPI jsonBinAPI;

    @Steps
    BasePage basePage;

    @When("^he create a Bin for data of file (.*)$")
    public void createBin(String fileName) {
        jsonBinAPI.createBin(fileName);
    }

    @When("^A (.*) user is accessing the Json portal$")
    public void setJsonBinAPI(String userType) {
        RestAssured.baseURI = basePage.getBaseURL(userType);
        jsonBinAPI.setTokenForSession(userType);

    }

    @Then("^Bin (.*) crated successfully as expected for the (.*)$")
    public void verifyBinCreation(String status,String fileName) {
        if("is".equalsIgnoreCase(status)){
            assertThat(Serenity.sessionVariableCalled("CreateBinResponseCode").toString()).isEqualTo("200");
            assertThat(Serenity.sessionVariableCalled("IdForSession").equals(null)).isFalse();
        }else{
            assertThat(Serenity.sessionVariableCalled("CreateBinResponseCode").toString().equalsIgnoreCase("200")).isFalse();
        }
    }

    @Then("^Bin (.*) updated successfully as expected for the (.*)$")
    public void verifyBinUpdated(String status,String fileName) {
        if("is".equalsIgnoreCase(status)){
            assertThat(Serenity.sessionVariableCalled("UpdateBinResponseCode").toString()).isEqualTo("200");
        }else{
            assertThat(Serenity.sessionVariableCalled("UpdateBinResponseCode").toString().equalsIgnoreCase("200")).isFalse();
        }
    }

    @And("^(.*) is displayed as expected$")
    public void verifyErrorMessage(String errorMessage){
        assertThat(Serenity.sessionVariableCalled("CreateBinResponse").toString().contains(errorMessage.trim())).isTrue();
    }

    @And("^Retrieved record (.*) matching the data from (.*)$")
    public void retrieveTheRecord(String status, String fileName){
        assertThat(jsonBinAPI.verifyTheData(fileName)).describedAs("Data Supplied and data retrieved comparision").isTrue();
    }

    @When("^he update a Bin from its previous value (.*) to new value (.*)$")
    public void updateBin(String oldFileName, String newFileName) {
        jsonBinAPI.updateBin(oldFileName,newFileName);
    }

    @When("^he read a Bin for data of file (.*)$")
    public void readBin(String fileName) {
        jsonBinAPI.setIdForSession(fileName);
        jsonBinAPI.getBinWithId(Serenity.sessionVariableCalled("IdForSession").toString());
    }

    @When("^he delete a Bin for data of file (.*)$")
    public void deleteBin(String fileName) {
        jsonBinAPI.setIdForSession(fileName);
        jsonBinAPI.deleteBin(Serenity.sessionVariableCalled("IdForSession").toString());
        jsonBinAPI.getBinWithId(Serenity.sessionVariableCalled("IdForSession").toString());
    }

    @Then("^Bin (.*) read successfully$")
    public void verifyRead(String status) {
        if("is".equalsIgnoreCase(status)){
            assertThat(Serenity.sessionVariableCalled("GetBinResponseCode").toString()).isEqualTo("200");
        }else{
            assertThat(Serenity.sessionVariableCalled("GetBinResponseCode").toString().equalsIgnoreCase("200")).isFalse();
        }
    }

    @Then("^Bin (.*) deleted successfully$")
    public void verifyDelete(String status) {
        if("is".equalsIgnoreCase(status)){
            assertThat(Serenity.sessionVariableCalled("DeleteBinResponseCode").toString()).isEqualTo("200");
        }else{
            assertThat(Serenity.sessionVariableCalled("DeleteBinResponseCode").toString().equalsIgnoreCase("200")).isFalse();
        }
    }

}
