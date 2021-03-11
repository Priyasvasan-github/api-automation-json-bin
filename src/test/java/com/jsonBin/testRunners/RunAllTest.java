package com.jsonBin.testRunners;

import cucumber.api.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

/**
 * Integration test using cucumber runner
 */
@RunWith(CucumberWithSerenity.class)
@CucumberOptions(features = "src/test/resources/features/testFeatures/", glue = "com.jsonBin.stepDefinitions")
public class RunAllTest {
}
