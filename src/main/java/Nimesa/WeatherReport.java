package Nimesa;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.InputMismatchException;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

public class WeatherReport {

	private static final String APPID = "b6907d289e10d714a6e88b30761fae22";
	private static final String URL = "https://samples.openweathermap.org/data/2.5/forecast/hourly?q=London,us&appid="
			+ APPID;

	public static void main(String[] args) {
		System.setProperty("javax.net.debug", "all");
		try {
			Scanner scanner = new Scanner(System.in);
			int selectedOption;
			do {
				System.out.println("Please select an option for the weather forecast - ");
				System.out.println("1. Get Weather");
				System.out.println("2. Get Wind Speed");
				System.out.println("3. Get Pressure");
				System.out.println("0. Exit");

				selectedOption = scanner.nextInt();
				System.out.println("Selected Option - " + selectedOption);

				switch (selectedOption) {
				case 1:
					getWeatherData(scanner);
					break;
				case 2:
					getWindSpeedData(scanner);
					break;
				case 3:
					getPressureData(scanner);
					break;
				case 0:
					System.out.println("Exiting program.");
					break;
				default:
					System.out.println("Invalid option. Please try again.");
				}
			} while (selectedOption != 0);
		} catch (InputMismatchException e) {
			System.err.println("Please verify the entered input option");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void getPressureData(Scanner scanner) {
		try {
			System.out.println("Enter date (yyyy-MM-dd HH:mm:ss):");
			scanner.nextLine();
			String enteredDate = scanner.nextLine();
			String response = makeAPIRequest(URL);
			JSONObject jObject = new JSONObject(response);
			JSONArray list = (JSONArray) jObject.get("list");
			for (int i = 0; i < list.length(); i++) {
				JSONObject wether = (JSONObject) list.get(i);
				String date = (String) wether.get("dt_txt");
				if (enteredDate.equals(date)) {
					System.out.println("Pressure for the date " + date + " is - "
							+ ((JSONObject) wether.get("main")).get("pressure"));
					break;
				} else {
					System.out.println("Please verify the date input");
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void getWindSpeedData(Scanner scanner) {
		try {
			System.out.println("Enter date (yyyy-MM-dd HH:mm:ss):");
			scanner.nextLine();
			String enteredDate = scanner.nextLine();
			String response = makeAPIRequest(URL);
			JSONObject jObject = new JSONObject(response);
			JSONArray list = (JSONArray) jObject.get("list");
			for (int i = 0; i < list.length(); i++) {
				JSONObject wether = (JSONObject) list.get(i);
				String date = (String) wether.get("dt_txt");
				if (enteredDate.equals(date)) {
					System.out.println("Wind Speed for the date " + date + " is - "
							+ ((JSONObject) wether.get("wind")).get("speed"));
					break;
				} else {
					System.out.println("Please verify the date input");
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void getWeatherData(Scanner scanner) {
		try {
			System.out.println("Enter date (yyyy-MM-dd HH:mm:ss):");
			scanner.nextLine();
			String enteredDate = scanner.nextLine();
			String response = makeAPIRequest(URL);
			JSONObject jObject = new JSONObject(response);
			JSONArray list = (JSONArray) jObject.get("list");
			for (int i = 0; i < list.length(); i++) {
				JSONObject wether = (JSONObject) list.get(i);
				String date = (String) wether.get("dt_txt");
				if (enteredDate.equals(date)) {
					System.out.println("Temperature for the date " + date + " is - "
							+ ((JSONObject) wether.get("main")).get("temp"));
					break;
				} else {
					System.out.println("Please verify the date input");
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static String makeAPIRequest(String url) {
		try {
			URL obj = new URL(URL);
			System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2");
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("GET");
			int responseCode = con.getResponseCode();
			System.out.println("GET Response Code :: " + responseCode);
			if (responseCode == HttpURLConnection.HTTP_OK) { // success
				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();
				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();
				return response.toString();
			} else {
				System.out.println("GET request did not work.");
			}
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}

}
