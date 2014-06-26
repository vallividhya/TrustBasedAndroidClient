package edu.sjsu.trustbasedreco.pojo;

import java.util.List;

public class User {

	public String name;
	public String email;
	public String city;
	public int zip;
	public String password;
	public List<String> friendsList;
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public int getZip() {
		return zip;
	}

	public void setZip(int zip) {
		this.zip = zip;
	}

	
	public List<String> getFriendsList() {
		return friendsList;
	}

	public void setFriendsList(List<String> friendsList) {
		this.friendsList = friendsList;
	}

	@Override
	public String toString()
	{
		return new StringBuffer("Name : ").append(this.name)
				.append("Email: ").append(this.email)
				.append("City : ").append(this.city)
				.append("Zip: ").append(this.zip).toString();
	}

	
}
