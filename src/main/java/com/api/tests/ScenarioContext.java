package com.api.tests;

import java.util.HashMap;
import java.util.Map;

public class ScenarioContext {
	
	// To share data between different steps

	private Map<Object, Object> scenarioContext;

	public ScenarioContext() {
		scenarioContext = new HashMap<>();
	}

	public void setContext(Object key, Object value) {
		scenarioContext.put(key.toString(), value);
	}

	public Object getContext(Object key) {
		return scenarioContext.get(key.toString());
	}

	public Boolean isContains(Object key) {
		return scenarioContext.containsKey(key.toString());
	}
}
