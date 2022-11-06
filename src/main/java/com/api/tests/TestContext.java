package com.api.tests;

public class TestContext {

	public ScenarioContext scenarioContext;

	// Maintain object across test file
	public TestContext() {
		scenarioContext = new ScenarioContext();
	}
}
