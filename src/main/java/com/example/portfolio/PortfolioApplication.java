package com.example.portfolio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.Connection;
import java.sql.DriverManager;

@SpringBootApplication
public class PortfolioApplication {

	public static void main(String[] args) {

		testSupabaseConnection();

		SpringApplication.run(PortfolioApplication.class, args);
	}

	private static void testSupabaseConnection() {

		String url = System.getenv("JDBC_DATABASE_URL");
		String username = System.getenv("JDBC_DATABASE_USERNAME");
		String password = System.getenv("JDBC_DATABASE_PASSWORD");

		System.out.println("========== SUPABASE JDBC RETRY TEST ==========");

		for (int attempt = 1; attempt <= 3; attempt++) {

			System.out.println("Attempt " + attempt + " of 3...");

			try (Connection connection =
						 DriverManager.getConnection(url, username, password)) {

				System.out.println("Attempt " + attempt + ": SUCCESS");
				System.out.println("Connection valid: " + connection.isValid(5));

				break;

			} catch (Exception e) {

				System.out.println("Attempt " + attempt + ": FAILED");
				e.printStackTrace();

				if (attempt < 3) {
					try {
						System.out.println("Waiting 3 seconds before retry...");
						Thread.sleep(3000);
					} catch (InterruptedException ignored) {
					}
				}
			}
		}

		System.out.println("==============================================");
	}
}