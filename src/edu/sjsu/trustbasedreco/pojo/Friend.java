package edu.sjsu.trustbasedreco.pojo;

import java.util.ArrayList;
import java.util.List;

public class Friend {

	String name;
	String user;
	String friendEmail;
	
	public String getFriendEmail() {
		return friendEmail;
	}

	public void setFriendEmail(String friendEmail) {
		this.friendEmail = friendEmail;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	List<Category> categories = new ArrayList<Category>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}

	public void addScore(String categoryName, int score) {
		Category c = new Category();
		c.setName(categoryName);
		c.setScore(score);
		getCategories().add(c);
	}
}
