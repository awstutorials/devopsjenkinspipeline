package com.in28minutes.springboot.web;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.Ignore;
import org.junit.runner.RunWith;
@RunWith(Cucumber.class)
@CucumberOptions(monochrome = true, features = "src/test/resources", plugin = { "pretty" })
@Ignore
public class RunCucumberIntegrationTestCase {
}