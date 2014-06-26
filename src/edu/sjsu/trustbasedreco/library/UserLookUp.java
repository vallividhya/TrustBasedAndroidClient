package edu.sjsu.trustbasedreco.library;

import java.util.HashMap;
import java.util.Map;

public class UserLookUp {
		public static Map<String, String> users = new HashMap<String, String>();

		static {
			users.put("vidhya@sjsu.edu", "Vidhya");
			users.put("vallividhyavenkatesan@gmail.com", "Valli");
			users.put("cmeena@gmail.com", "Meena");
			users.put("sow@gmail.com", "Sowbakya");
			users.put("som@gmail.com", "Sowmya");
			users.put("sowmister@gmail.com", "Sowmya Ganesan");
			users.put("lak@gmail.com", "Lak");
			users.put("umang@gmail.com", "Umang");
			users.put("subbu@gmail.com", "Subbu");
		}
		
		public static String getUser(String key) {
			return users.get(key);
		}
}
