package com.jsonBin.apis;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import helper.GetTestProperties;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.core.pages.PageObject;
import utils.ForceStringDeserializer;

public class BasePage extends PageObject {

    GetTestProperties getTestProperties =new GetTestProperties();


    /**
     * ObjectMapper to make contract validations
     */
    public final ObjectMapper mapper = new ObjectMapper().registerModule(
            new SimpleModule().addDeserializer(String.class, new ForceStringDeserializer())
    );

    /**
     * Method to pick baseURL based on environment setup
     * @return
     */
    public String getBaseURL(String userType) {
        if(userType.equalsIgnoreCase("VALID")){
            Serenity.setSessionVariable("Token").to(getTestProperties.getValue("validToken"));
        }else{
            Serenity.setSessionVariable("Token").to(getTestProperties.getValue("inValidToken"));
        }

        String env = System.getenv("ENV");
        if (env == null || env.isEmpty()) {
            return "https://api.jsonbin.io/v3";
        } else {
            switch (env){
                case "localhost":
                    return "https://localhost:8080";
                case "test":
                    return "URL FOR TEST";
                case "test-staging":
                    return "URL FOR STAGING";
                case "prod-live":
                    return "https://api.jsonbin.io/v3";
                case "mock":
                    return "URL FOR MOCK";
                default:
                    return "default URL";
            }
        }
    }

}
