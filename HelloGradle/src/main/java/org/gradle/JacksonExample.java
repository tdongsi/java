package org.gradle;

import java.io.File;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JacksonExample {

	public static void main(String[] args) {
		ObjectMapper mapper = new ObjectMapper();
		Config conn = new Config();
		conn.type = "hive";
		conn.host = "192.168.5.184";
		conn.user = "cloudera";
		conn.password = "password";
		conn.url = "jdbc:hive2://192.168.5.184:10000/DWH";

		// POJO to JSON in String
		String jsonInString = mapper.writeValueAsString(conn);
		System.out.println(jsonInString);

	}

}
