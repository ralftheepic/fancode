package com.api.tests;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.api.pojo.Todo;
import com.api.pojo.Users;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class TodoSteps {

	
	// Test File
	TestContext testContext;
	Gson gson;

	public TodoSteps(TestContext context) {
		testContext = context;
		gson = new Gson();
	}

	@Given("User has the todo tasks")
	public void user_has_the_todo_tasks() throws IOException {
		List<Todo> list = null;
		list = gson.fromJson(
				new BufferedReader(
						new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream("todo.json"))),
				new TypeToken<List<Todo>>() {
				}.getType());
		Map<Object, List<Todo>> todolist = list.stream().collect(Collectors.groupingBy(m -> m.getUserId()));
		testContext.scenarioContext.setContext("todolist", todolist);
	}

	@And("User belongs to the city FanCode")
	public void user_belongs_to_the_city_fan_code() {
		List<Users> fancodeusers = getFancodeUsers(getFancodeCity());
		testContext.scenarioContext.setContext("fancodeusers", fancodeusers);
	}

	@Then("User Completed task percentage should be greater than {int}%")
	public void user_completed_task_percentage_should_be_greater_than(Integer int1) {
		@SuppressWarnings("unchecked")
		Map<Object, List<Todo>> todolist = (Map<Object, List<Todo>>) testContext.scenarioContext.getContext("todolist");
		@SuppressWarnings("unchecked")
		List<Users> fancodeusers = (List<Users>) testContext.scenarioContext.getContext("fancodeusers");
		System.out.println("USERS WITH GREATER THEN 50% TODO'S TASK COMPLETED AND BELONGING TO CITY FANCODE \n");
		todolist.forEach((k, v) -> {
			if (fancodeusers.stream().map(Users::getId).collect(Collectors.toList()).contains((int) k)) {
				int count = getTodoCountCompleted(v);
				float percentage = (count * 100) / v.stream().count();
				if (percentage > 50) {
					System.out.println("" + k + " percentage " + percentage + " user "
							+ fancodeusers.stream().filter(id -> id.getId() == (int) k).collect(Collectors.toList()) + "\n");
				}
			}
		});
	}

	public int getTodoCountCompleted(List<Todo> v) {
		int count = 0;
		for (int todo = 0; todo < v.size(); todo++) {
			if (v.get(todo).isCompleted()) {
				count++;
			}
		}
		return count;
	}

	public List<Users> getFancodeUsers(List<Users> users) {
		List<Users> fancodeusers = new ArrayList<>();
		for (int user = 0; user < users.size(); user++) {
			float lat = Float.parseFloat(users.get(user).getAddress().getGeo().getLat());
			float lng = Float.parseFloat(users.get(user).getAddress().getGeo().getLng());
			if (lat <= 5 && lat >= -40 && lng >= 5 && lng <= 100) {
				// System.out.println(" Latitude " + lat + " Longitude " + lng);
				fancodeusers.add(users.get(user));
			}
		}
		return fancodeusers;
	}

	public List<Users> getFancodeCity() {
		List<Users> userlist = null;
		try {
			userlist = gson.fromJson(
					new BufferedReader(
							new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream("users.json"))),
					new TypeToken<List<Users>>() {
					}.getType());
		} catch (JsonIOException | JsonSyntaxException e) {
			e.printStackTrace();
		}
		return getFancodeUsers(userlist);
	}
}
