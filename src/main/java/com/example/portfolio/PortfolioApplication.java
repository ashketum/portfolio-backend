package com.example.portfolio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Arrays;

@SpringBootApplication
public class PortfolioApplication {

	private static final String HOST =
			"aws-0-ap-southeast-1.pooler.supabase.com";

	public static void main(String[] args) {

		runDatabaseDiagnostics();

		SpringApplication.run(PortfolioApplication.class, args);
	}

	private static void runDatabaseDiagnostics() {

		System.out.println();
		System.out.println("==============================================");
		System.out.println("       SUPABASE / RENDER DB DIAGNOSTIC");
		System.out.println("==============================================");

		// 1. DNS
		InetAddress[] addresses;

		try {
			addresses = InetAddress.getAllByName(HOST);

			System.out.println("\n[1] DNS RESOLUTION");
			System.out.println("Host: " + HOST);

			for (InetAddress address : addresses) {
				System.out.println("IP: " + address.getHostAddress());
			}

			System.out.println("DNS: SUCCESS");

		} catch (Exception e) {
			System.out.println("DNS: FAILED");
			e.printStackTrace();
			return;
		}

		// 2. TCP + PostgreSQL SSL negotiation
		System.out.println("\n[2] TESTING SUPABASE IPS");

		for (InetAddress address : addresses) {

			String ip = address.getHostAddress();

			System.out.println("\n----------------------------------------------");
			System.out.println("IP: " + ip);

			testPostgresConnection(ip, 5432);
			testPostgresConnection(ip, 6543);
		}

		// 3. Actual JDBC connection using Render environment variables
		System.out.println("\n[3] ACTUAL JDBC CONNECTION");

		String url = System.getenv("JDBC_DATABASE_URL");
		String username = System.getenv("JDBC_DATABASE_USERNAME");
		String password = System.getenv("JDBC_DATABASE_PASSWORD");

		System.out.println("JDBC URL: " + url);
		System.out.println("Username present: " + (username != null));
		System.out.println("Password present: " + (password != null));

		if (url != null && username != null && password != null) {

			try (Connection connection =
						 DriverManager.getConnection(url, username, password)) {

				System.out.println("JDBC CONNECTION: SUCCESS");
				System.out.println("Connection valid: "
						+ connection.isValid(5));

			} catch (Exception e) {

				System.out.println("JDBC CONNECTION: FAILED");
				System.out.println("Exception: "
						+ e.getClass().getName());
				System.out.println("Message: "
						+ e.getMessage());

				e.printStackTrace();
			}
		}

		System.out.println("\n==============================================");
		System.out.println("       END OF DATABASE DIAGNOSTIC");
		System.out.println("==============================================");
		System.out.println();
	}

	private static void testPostgresConnection(String ip, int port) {

		System.out.println("\nTesting " + ip + ":" + port);

		try (Socket socket = new Socket()) {

			socket.connect(
					new InetSocketAddress(ip, port),
					5000
			);

			socket.setSoTimeout(5000);

			System.out.println("TCP: SUCCESS");

			// PostgreSQL SSLRequest
			DataOutputStream output =
					new DataOutputStream(socket.getOutputStream());

			DataInputStream input =
					new DataInputStream(socket.getInputStream());

			output.writeInt(8);
			output.writeInt(80877103);
			output.flush();

			int response = input.read();

			if (response == 'S') {

				System.out.println(
						"PostgreSQL SSL negotiation: SUCCESS"
				);

			} else if (response == 'N') {

				System.out.println(
						"PostgreSQL SSL negotiation: SERVER REJECTED SSL"
				);

			} else if (response == -1) {

				System.out.println(
						"PostgreSQL SSL negotiation: CONNECTION CLOSED"
				);

			} else {

				System.out.println(
						"PostgreSQL SSL negotiation: UNKNOWN RESPONSE = "
								+ response
				);
			}

		} catch (Exception e) {

			System.out.println("TCP / SSL TEST: FAILED");
			System.out.println("Exception: "
					+ e.getClass().getName());
			System.out.println("Message: "
					+ e.getMessage());
		}
	}
}