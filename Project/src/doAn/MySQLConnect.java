package doAn;

import java.sql.*;
import java.sql.Date;
import java.util.*;

public class MySQLConnect {
	
	static void getFilmInfo (Scanner sc, Connection conn) {
		CallableStatement cStmt = null;
		ResultSetMetaData rsmd = null;
		int month, age;
		String genre, temp;
		String feat = "0";
		boolean valid = false;		
		//ask if user wanna get day or month revenue
		do {
			valid = true;
			try {
				System.out.println("In which category would you like to search by?");
				System.out.println("1. By genre");
				System.out.println("2. By release month");
				System.out.println("3. By age restriction");
				System.out.println("4. Exit");
				feat = sc.nextLine();
				if (!feat.contentEquals("1") && !feat.contentEquals("2") && 
						!feat.contentEquals("3") && !feat.contentEquals("4")) {
					throw new Exception("Invalid input!");
				}
			}
			catch (Exception e) {
				valid = false;
				System.out.println("Invalid input! Please try again");
			}
		} while (!valid);
				
		if (feat.contentEquals("1")) { //show film's info by genre
			try {
				//get input
				System.out.println("Enter the film genre you would like to see: ");
				genre = sc.nextLine();
				temp = genre.substring(0, 1).toUpperCase() + genre.substring(1);
				//call procedure and set parameters
				cStmt = conn.prepareCall("{call genre(?)}");
				cStmt.setString(1, "%" + temp + "%");
				//execute and notify the success of the procedure
				ResultSet rs = cStmt.executeQuery();
				if(rs != null) {
					rsmd = rs.getMetaData();
					int colNum = rsmd.getColumnCount();
					while (rs.next()) {			
						for (int i = 1; i <= colNum; i++) {
							if (i > i)
								System.out.println(", ");
							String colValue = rs.getString(i);
							System.out.println(rsmd.getColumnName(i) + ": " + colValue);
						}
						System.out.println("");
					}
				}		
				else {
					System.out.println("No data available! Please try again.");
				}
			} catch (SQLException e1) {
				System.out.println("SQL Exception: " + e1.getMessage());
			} finally {
				if (cStmt != null) {
					try {
						cStmt.close();
					} catch (SQLException e1) {
						System.out.println("SQL Exception: " + e1.getMessage());
					}
					cStmt = null;
				}
			}
		}
		else if (feat.contentEquals("2")) { //show film's info by release month
			try {
				//get input
				System.out.println("Enter a month (in number): ");
				month = sc.nextInt();
				sc.nextLine();
				//call procedure and set parameters
				cStmt = conn.prepareCall("{call rmonth(?)}");
				cStmt.setInt(1, month);
				//execute and notify the success of the procedure
				ResultSet rs = cStmt.executeQuery();
				if(rs != null) {
					rsmd = rs.getMetaData();
					int colNum = rsmd.getColumnCount();
					while (rs.next()) {			
						for (int i = 1; i <= colNum; i++) {
							if (i > i)
								System.out.println(", ");
							String colValue = rs.getString(i);
							System.out.println(rsmd.getColumnName(i) + ": " + colValue);
						}
						System.out.println("");
					}
				}	
				else {
					System.out.println("No data available! Please try again.");
				}
			} catch (SQLException e1) {
				System.out.println("SQL Exception: " + e1.getMessage());
			} finally {
				if (cStmt != null) {
					try {
						cStmt.close();
					} catch (SQLException e1) {
						System.out.println("SQL Exception: " + e1.getMessage());
					}
					
					cStmt = null;
				}
			}
		}
		else if (feat.contentEquals("3")) { //show films info by age limit
			try {
				//get input
				System.out.println("Enter the minimum age of your audience: ");
				age = sc.nextInt();
				sc.nextLine();
				//call procedure and set parameters
				cStmt = conn.prepareCall("{call agelim(?)}");
				cStmt.setInt(1, age);
				//execute and notify the success of the procedure
				ResultSet rs = cStmt.executeQuery();
				if(rs != null) {
					rsmd = rs.getMetaData();
					int colNum = rsmd.getColumnCount();
					while (rs.next()) {			
						for (int i = 1; i <= colNum; i++) {
							if (i > i)
								System.out.println(", ");
							String colValue = rs.getString(i);
							System.out.println(rsmd.getColumnName(i) + ": " + colValue);
						}
						System.out.println("");
					}
				}	
				else {
					System.out.println("No data available! Please try again.");
				}
			} catch (SQLException e1) {
				System.out.println("SQL Exception: " + e1.getMessage());
			} finally {
				if (cStmt != null) {
					try {
						cStmt.close();
					} catch (SQLException e1) {
						System.out.println("SQL Exception: " + e1.getMessage());
					}
					
					cStmt = null;
				}
			}
		}
		else if (feat.contentEquals("4")) {
			exitTicketer();
		}
		//Ask user what do they want to do next
		do {
			valid = true;
			try {
				System.out.println("What do you want to do next?");
				System.out.println("1. Continue as client");
				System.out.println("2. Continue as admin");
				System.out.println("3. Exit");
				feat = sc.nextLine();
				if (!feat.contentEquals("1") && !feat.contentEquals("2") && !feat.contentEquals("3")) {
					throw new Exception("Invalid input!");
				}
			}
			catch (Exception e) {
				valid = false;
				System.out.println("Invalid input! Please try again");
			}
		} while (!valid);
		
		switch (feat) {
			case "1":
				client(sc, conn);
				break;
			case "2":
				checkAdmin(sc, conn);
				break;
			case "3":
				exitTicketer();
				break;
			default:
				System.out.println("Invalid input! Please try again");
				break;
		}
	}
	
	static void getTicketInfo (Scanner sc, Connection conn) {
		CallableStatement cStmt = null;
		ResultSetMetaData rsmd = null;
		String ticketID;
		String feat = "0";
		boolean valid = false;		
		//ask if user wanna get day or month revenue
		try {
			//get input
			System.out.println("Please enter the ID of the ticket you wish to look up: ");
			ticketID = sc.nextLine();;
			//call procedure and set parameters
			cStmt = conn.prepareCall("{call tk_info(?)}");
			cStmt.setString(1, ticketID);
			//execute and notify the success of the procedure
			ResultSet rs = cStmt.executeQuery();
			if(rs != null) {
				rsmd = rs.getMetaData();
				int colNum = rsmd.getColumnCount();
				while (rs.next()) {			
					for (int i = 1; i <= colNum; i++) {
						if (i > i)
							System.out.println(", ");
						String colValue = rs.getString(i);
						System.out.println(rsmd.getColumnName(i) + ": " + colValue);
					}
					System.out.println("");
				}
			}		
			else {
				System.out.println("No data available! Please try again.");
			}
		} catch (SQLException e1) {
			System.out.println("SQL Exception: " + e1.getMessage());
		} finally {
			if (cStmt != null) {
				try {
					cStmt.close();
				} catch (SQLException e1) {
					System.out.println("SQL Exception: " + e1.getMessage());
				}
				cStmt = null;
			}
		}
		//Ask user what do they want to do next
		do {
			valid = true;
			try {
				System.out.println("What do you want to do next?");
				System.out.println("1. Continue as client");
				System.out.println("2. Continue as admin");
				System.out.println("3. Exit");
				feat = sc.nextLine();
				if (!feat.contentEquals("1") && !feat.contentEquals("2") && !feat.contentEquals("3")) {
					throw new Exception("Invalid input!");
				}
			}
			catch (Exception e) {
				valid = false;
				System.out.println("Invalid input! Please try again");
			}
		} while (!valid);
		
		switch (feat) {
			case "1":
				client(sc, conn);
				break;
			case "2":
				checkAdmin(sc, conn);
				break;
			case "3":
				exitTicketer();
				break;
			default:
				System.out.println("Invalid input! Please try again");
				break;
		}
	}
	
	static void getShowTime (Scanner sc, Connection conn) {
		CallableStatement cStmt = null;
		ResultSetMetaData rsmd = null;
		String theaterID, cityName;
		String feat = "0";
		boolean valid = false;		
		try {
			//get input
			System.out.println("Please enter the name of your city/province: ");
			cityName = sc.nextLine();
			//call procedure and set parameters
			cStmt = conn.prepareCall("{call city(?)}");
			cStmt.setString(1, cityName);
			//execute and notify the success of the procedure
			ResultSet rs = cStmt.executeQuery();
			if(rs != null) {
				rsmd = rs.getMetaData();
				int colNum = rsmd.getColumnCount();
				while (rs.next()) {			
					for (int i = 1; i <= colNum; i++) {
						if (i > i)
							System.out.println(", ");
						String colValue = rs.getString(i);
						System.out.println(rsmd.getColumnName(i) + ": " + colValue);
					}
					System.out.println("");
				}
			}	
			else {
				System.out.println("No data available! Please try again.");
			}
		} catch (SQLException e1) {
			System.out.println("SQL Exception: " + e1.getMessage());
		} finally {
			if (cStmt != null) {
				try {
					cStmt.close();
				} catch (SQLException e1) {
					System.out.println("SQL Exception: " + e1.getMessage());
				}
				cStmt = null;
			}
		}
		try {
			//get input
			System.out.println("Please enter the ID of the theater whose schedule you wish to look up: ");
			theaterID = sc.nextLine();
			//call procedure and set parameters
			cStmt = conn.prepareCall("{call showtime_info(?)}");
			cStmt.setString(1, theaterID);
			//execute and notify the success of the procedure
			ResultSet rs = cStmt.executeQuery();
			if(rs != null) {
				rsmd = rs.getMetaData();
				int colNum = rsmd.getColumnCount();
				while (rs.next()) {			
					for (int i = 1; i <= colNum; i++) {
						if (i > i)
							System.out.println(", ");
						String colValue = rs.getString(i);
						System.out.println(rsmd.getColumnName(i) + ": " + colValue);
					}
					System.out.println("");
				}
			}	
			else {
				System.out.println("No data available! Please try again.");
			}
		} catch (SQLException e1) {
			System.out.println("SQL Exception: " + e1.getMessage());
		} finally {
			if (cStmt != null) {
				try {
					cStmt.close();
				} catch (SQLException e1) {
					System.out.println("SQL Exception: " + e1.getMessage());
				}
				cStmt = null;
			}
		}
		//Ask user what do they want to do next
		do {
			valid = true;
			try {
				System.out.println("What do you want to do next?");
				System.out.println("1. Continue as client");
				System.out.println("2. Continue as admin");
				System.out.println("3. Exit");
				feat = sc.nextLine();
				if (!feat.contentEquals("1") && !feat.contentEquals("2") && !feat.contentEquals("3")) {
					throw new Exception("Invalid input!");
				}
			}
			catch (Exception e) {
				valid = false;
				System.out.println("Invalid input! Please try again");
			}
		} while (!valid);
		
		switch (feat) {
			case "1":
				client(sc, conn);
				break;
			case "2":
				checkAdmin(sc, conn);
				break;
			case "3":
				exitTicketer();
				break;
			default:
				System.out.println("Invalid input! Please try again");
				break;
		}
	}
	
	static void client(Scanner sc, Connection conn) {
		String feat = "0";
		boolean valid = false;
		System.out.println("Hello dear client! How may Ticketer help you today?");
		do {
			valid = true;
			try {
				System.out.println("Please choose a feature you wish to use: ");
				System.out.println("1. Look up ticket info");
				System.out.println("2. Look up theater's show time");
				System.out.println("3. Look up films' info");
				System.out.println("4. Exit");
				feat = sc.nextLine();
				if (!feat.contentEquals("1") && !feat.contentEquals("2") && 
						!feat.contentEquals("3")) 
				{
					throw new Exception("Invalid input!");
				}
			}
			catch (Exception e) {
				valid = false;
				System.out.println("Invalid input! Please try again");
			}
		} while (!valid);
		
		
		switch (feat) {
			case "1":
				getTicketInfo(sc, conn);
				break;
			case "2":
				getShowTime(sc, conn);
				break;
			case "3":
				getFilmInfo(sc, conn);
				break;
			case "4":
				exitTicketer();
				break;
			default:
				System.out.println("Invalid input! Please try again");
				break;
		}
		
	}
	
	static void checkAdmin(Scanner sc, Connection conn) { //Get password from admin user
		boolean valid = false;
		String password = new String();
		do {
			valid = true;
			try {
				System.out.println("To proceed in administrator mode, please enter the correct password");
				System.out.println("or enter 'exit' to exit");
				password = sc.nextLine();
				if (!password.contentEquals("123456") && !password.contentEquals("3")) {
					throw new Exception("Invalid input!");
				}
			}
			catch (Exception e) {
				valid = false;
				System.out.println("Wrong password! Please try again");
			}
		} while (!valid);
		
		if (password.contentEquals("123456")) {
			admin(sc, conn);
		}
		else if (password.contentEquals("exit")) {
			exitTicketer();
		}	
	}
	
	static void admin(Scanner sc, Connection conn) {
		String feat = "0";
		boolean valid = false;
		do {
			valid = true;
			try {
				System.out.println("Please choose a feature you wish to use: ");
				System.out.println("1. Add new film info");
				System.out.println("2. Add new theater info");
				System.out.println("3. Add new scheduling info");
				System.out.println("4. Add new customer info");
				System.out.println("5. Add new ticket info");
				System.out.println("6. Get revenue");
				System.out.println("7. Look up show time");
				System.out.println("8. Look up ticket info");
				System.out.println("9. Look up films info");
				System.out.println("10. Exit");
				feat = sc.nextLine();
				sc.nextLine();
				if (!feat.contentEquals("1") && !feat.contentEquals("2") && 
						!feat.contentEquals("3") && !feat.contentEquals("4") && 
						!feat.contentEquals("5") && !feat.contentEquals("6") && 
						!feat.contentEquals("7") && !feat.contentEquals("8") &&
						!feat.contentEquals("9") && !feat.contentEquals("10")) 
				{
					throw new Exception("Invalid input!");
				}
			}
			catch (Exception e) {
				valid = false;
				System.out.println("Invalid input! Please try again");
			}
		} while (!valid);
		
		switch (feat) {
			case "1":
				addFilm(sc, conn);
				break;
			case "2":
				addTheater(sc, conn);
				break;
			case "3":
				addSchedule(sc, conn);
				break;
			case "4":
				addCustomer(sc, conn);
				break;
			case "5":
				addTicket(sc, conn);
				break;
			case "6":
				getRevenue(sc, conn);
				break;
			case "7":
				getShowTime(sc, conn);
				break;
			case "8":
				getTicketInfo(sc, conn);
				break;
			case "9":
				getFilmInfo(sc, conn);
				break;	
			case "10":
				exitTicketer();
				break;
			default:
				System.out.println("Invalid input! Please try again");
		}
	}
	
	static void addFilm (Scanner sc, Connection conn) {
		Date releaseDate = null;
		CallableStatement cStmt = null;
		String id, name, genre, sReleaseDate, origin, lang, limit;
		int duration;
		String feat = "0";
		boolean valid = false;		
		//Get input
		System.out.println("Please insert new film infomation");
		System.out.println("Enter film's ID: ");
		id = sc.nextLine();
		System.out.println("Enter film's name: ");
		name = sc.nextLine();
		System.out.println("Enter film's genre: ");
		genre = sc.nextLine();
		System.out.println("Enter film's duration (minutes): ");
		duration = sc.nextInt();
		sc.nextLine();
		System.out.println("Enter film's release date (YYYY-MM-DD): ");
		sReleaseDate = sc.nextLine();
		releaseDate = Date.valueOf(sReleaseDate);
		System.out.println("Enter film's origin (country): ");
		origin = sc.nextLine();
		System.out.println("Enter film's language: ");
		lang = sc.nextLine();
		System.out.println("Enter film's age limit: ");
		limit = sc.nextLine();
		
		try {
			//call procedure and set parameters
			cStmt = conn.prepareCall("{call add_films(?, ?, ?, ?, ?, ?, ?, ?)}");
			cStmt.setString(1, id);
			cStmt.setString(2, name);
			cStmt.setString(3, genre);
			cStmt.setInt(4, duration);
			cStmt.setDate(5, releaseDate);
			cStmt.setString(6, origin);
			cStmt.setString(7, lang);
			cStmt.setString(8, limit);
			//execute and notify the success of the procedure
			if(cStmt.executeQuery() != null) {
				System.out.println("New film added successfully!");
			}	
		} catch (SQLException e1) {
			System.out.println("SQL Exception: " + e1.getMessage());
		} finally {
			if (cStmt != null) {
				try {
					cStmt.close();
				} catch (SQLException e1) {
					System.out.println("SQL Exception: " + e1.getMessage());
				}
				
				cStmt = null;
			}
		}
		//Ask user what do they want to do next
		do {
			valid = true;
			try {
				System.out.println("What do you want to do next?");
				System.out.println("1. Continue");
				System.out.println("2. Exit");
				feat = sc.nextLine();
				if (!feat.contentEquals("1") && !feat.contentEquals("2")) {
					throw new Exception("Invalid input!");
				}
			}
			catch (Exception e) {
				valid = false;
				System.out.println("Invalid input! Please try again");
			}
		} while (!valid);
		
		switch (feat) {
			case "1":
				admin(sc, conn);
				break;
			case "2":
				exitTicketer();
				break;
			default:
				System.out.println("Invalid input! Please try again");
				break;
		}
	}
	
	static void addTheater (Scanner sc, Connection conn) {
		CallableStatement cStmt = null;
		String id, name, location, opening;
		int hotline;
		String feat = "0";
		boolean valid = false;		
		//Get input
		System.out.println("Please insert new theater infomation");
		System.out.println("Enter theater's ID: ");
		id = sc.nextLine();
		System.out.println("Enter theater's name: ");
		name = sc.nextLine();
		System.out.println("Enter theater's location: ");
		location = sc.nextLine();
		System.out.println("Enter theater's hotline: ");
		hotline = sc.nextInt();
		sc.nextLine();
		System.out.println("Enter theater's opening time (opening hour-closing hour, using 24 hours time): ");
		opening = sc.nextLine();
		
		try {
			//call procedure and set parameters
			cStmt = conn.prepareCall("{call add_theaters(?, ?, ?, ?, ?)}");
			cStmt.setString(1, id);
			cStmt.setString(2, name);
			cStmt.setString(3, location);
			cStmt.setInt(4, hotline);
			cStmt.setString(5, opening);
			//execute and notify the success of the procedure
			if(cStmt.executeQuery() != null) {
				System.out.println("New theater added successfully!");
			}	
		} catch (SQLException e1) {
			System.out.println("SQL Exception: " + e1.getMessage());
		} finally {
			if (cStmt != null) {
				try {
					cStmt.close();
				} catch (SQLException e1) {
					System.out.println("SQL Exception: " + e1.getMessage());
				}
				
				cStmt = null;
			}
		}
		//Ask user what do they want to do next
		do {
			valid = true;
			try {
				System.out.println("What do you want to do next?");
				System.out.println("1. Continue");
				System.out.println("2. Exit");
				feat = sc.nextLine();
				if (!feat.contentEquals("1") && !feat.contentEquals("2")) {
					throw new Exception("Invalid input!");
				}
			}
			catch (Exception e) {
				valid = false;
				System.out.println("Invalid input! Please try again");
			}
		} while (!valid);
		
		switch (feat) {
			case "1":
				admin(sc, conn);
				break;
			case "2":
				exitTicketer();
				break;
			default:
				System.out.println("Invalid input! Please try again");
				break;
		}
	}
	
	static void addSchedule (Scanner sc, Connection conn) {
		Statement stmt = null;
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;
		CallableStatement cStmt = null;
		String sid, fid, tid, startTime;
		int totalSlot, availableSlot, screen, colNum;
		String feat = "0";
		boolean valid = false;
		//get id name of films and theaters
		try {
			System.out.println("Films' info");
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select FID, Fname from Films");
			rsmd = rs.getMetaData();
			colNum = rsmd.getColumnCount();
			//print out the data in result set with column name
			while (rs.next()) {			
				for (int i = 1; i <= colNum; i++) {
					if (i > i)
						System.out.println(", ");
					String colValue = rs.getString(i);
					System.out.println(rsmd.getColumnName(i) + ": " + colValue);
				}
				System.out.println("");
			}
			System.out.println("Theaters' info: ");
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select TID, Tname from Theaters");
			rsmd = rs.getMetaData();
			colNum = rsmd.getColumnCount();
			//print out the data in result set with column name
			while (rs.next()) {			
				for (int i = 1; i <= colNum; i++) {
					if (i > i)
						System.out.println(", ");
					String colValue = rs.getString(i);
					System.out.println(rsmd.getColumnName(i) + ": " + colValue);
				}
				System.out.println("");
			}
		} catch (SQLException e1) {
			System.out.println("SQL Exception: " + e1.getMessage());
		}
		//Get input
		System.out.println("Please insert new schedule infomation:");
		System.out.println("Enter schedule's ID: ");
		sid = sc.nextLine();
		System.out.println("Enter total slot: ");
		totalSlot = sc.nextInt();
		sc.nextLine();
		System.out.println("Enter available slot: ");
		availableSlot = sc.nextInt();
		sc.nextLine();
		System.out.println("Enter screen number: ");
		screen = sc.nextInt();
		sc.nextLine();
		System.out.println("Enter start time: ");
		startTime = sc.nextLine();
		System.out.println("Enter film's ID: ");
		fid = sc.nextLine();
		System.out.println("Enter theater's ID: ");
		tid = sc.nextLine();
		
		try {
			//call procedure and set parameters
			cStmt = conn.prepareCall("{call add_Showtime(?, ?, ?, ?, ?, ?, ?)}");
			cStmt.setString(1, sid);
			cStmt.setInt(2, totalSlot);
			cStmt.setInt(3, availableSlot);
			cStmt.setInt(4, screen);
			cStmt.setString(5, startTime);
			cStmt.setString(6, fid);
			cStmt.setString(7, tid);
			//execute and notify the success of the procedure
			if(cStmt.executeQuery() != null) {
				System.out.println("new schedule added successfully");
			}	
		} catch (SQLException e1) {
			System.out.println("SQL Exception: " + e1.getMessage());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e1) {
					System.out.println("SQL Exception: " + e1.getMessage());
				}
				
				rs = null;
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e1) {
					System.out.println("SQL Exception: " + e1.getMessage());
				}
				
				stmt = null;
			}
			if (cStmt != null) {
				try {
					cStmt.close();
				} catch (SQLException e1) {
					System.out.println("SQL Exception: " + e1.getMessage());
				}
				
				cStmt = null;
			}
		}
		//Ask user what do they want to do next
		do {
			valid = true;
			try {
				System.out.println("What do you want to do next?");
				System.out.println("1. Continue");
				System.out.println("2. Exit");
				feat = sc.nextLine();
				if (!feat.contentEquals("1") && !feat.contentEquals("2")) {
					throw new Exception("Invalid input!");
				}
			}
			catch (Exception e) {
				valid = false;
				System.out.println("Invalid input! Please try again");
			}
		} while (!valid);
		
		switch (feat) {
			case "1":
				admin(sc, conn);
				break;
			case "2":
				exitTicketer();
				break;
			default:
				System.out.println("Invalid input! Please try again");
				break;
		}
	}
	
	static void addCustomer (Scanner sc, Connection conn) {
		CallableStatement cStmt = null;
		String phone, name, membership;
		int age;
		String feat = "0";
		boolean valid = false;

		//Get input
		System.out.println("Please insert new customer infomation");
		System.out.println("Enter customer's phone number: ");
		phone = sc.nextLine();
		System.out.println("Enter customer's name: ");
		name = sc.nextLine();
		System.out.println("Enter customer's age: ");
		age = sc.nextInt();
		sc.nextLine();
		System.out.println("Enter customer's membership type: ");
		membership = sc.nextLine();
		
		try {
			//call procedure and set parameters
			cStmt = conn.prepareCall("{call add_customer(?, ?, ?, ?)}");
			cStmt.setString(1, phone);
			cStmt.setString(2, name);
			cStmt.setInt(3, age);
			cStmt.setString(4, membership);
			//execute and notify the success of the procedure
			if(cStmt.executeQuery() != null) {
				System.out.println("New customer added successfully!");
			}	
		} catch (SQLException e1) {
			System.out.println("SQL Exception: " + e1.getMessage());
		} finally {
			if (cStmt != null) {
				try {
					cStmt.close();
				} catch (SQLException e1) {
					System.out.println("SQL Exception: " + e1.getMessage());
				}
				
				cStmt = null;
			}
		}
		//Ask user what do they want to do next
		do {
			valid = true;
			try {
				System.out.println("What do you want to do next?");
				System.out.println("1. Continue");
				System.out.println("2. Exit");
				feat = sc.nextLine();
				if (!feat.contentEquals("1") && !feat.contentEquals("2")) {
					throw new Exception("Invalid input!");
				}
			}
			catch (Exception e) {
				valid = false;
				System.out.println("Invalid input! Please try again");
			}
		} while (!valid);
		
		switch (feat) {
			case "1":
				admin(sc, conn);
				break;
			case "2":
				exitTicketer();
				break;
			default:
				System.out.println("Invalid input! Please try again");
				break;
		}
	}
	
	static void addTicket(Scanner sc, Connection conn) {
		CallableStatement cStmt = null;
		Date tkDate = null;
		String tkid, stkDate, fid, tid, sid, seatCat;
		int seat, price;
		String feat = "0";
		boolean valid = false;

		//Get input
		System.out.println("Please insert new ticket infomation:");
		System.out.println("Enter ticket id:");
		tkid = sc.nextLine();
		System.out.println("Enter ticket day of purchase (YYYY-MM-DD): ");
		stkDate = sc.nextLine();
		tkDate = Date.valueOf(stkDate);
		System.out.println("Enter film id: ");
		fid = sc.nextLine();
		System.out.println("Enter theater id: ");
		tid = sc.nextLine();
		System.out.println("Enter show time id: ");
		sid = sc.nextLine();
		System.out.println("Enter seat number: ");
		seat = sc.nextInt();
		sc.nextLine();
		System.out.println("Enter seat category id: ");
		seatCat = sc.nextLine();
		System.out.println("Enter price (VND): ");
		price = sc.nextInt();
		sc.nextLine();
		try {
			//call procedure and set parameters
			cStmt = conn.prepareCall("{call add_tktr(?, ?, ?, ?, ?, ?, ?, ?)}");
			cStmt.setString(1, tkid);
			cStmt.setDate(2, tkDate);
			cStmt.setString(3, fid);
			cStmt.setString(4, tid);
			cStmt.setString(5, sid);
			cStmt.setInt(6, seat);
			cStmt.setString(7, seatCat);
			cStmt.setInt(8, price);
			//execute and notify the success of the procedure
			if(cStmt.executeQuery() != null) {
				System.out.println("New customer added successfully!");
			}	
		} catch (SQLException e1) {
			System.out.println("SQL Exception: " + e1.getMessage());
		} finally {
			if (cStmt != null) {
				try {
					cStmt.close();
				} catch (SQLException e1) {
					System.out.println("SQL Exception: " + e1.getMessage());
				}
				
				cStmt = null;
			}
		}
		//Ask user what do they want to do next
		do {
			valid = true;
			try {
				System.out.println("What do you want to do next?");
				System.out.println("1. Continue");
				System.out.println("2. Exit");
				feat = sc.nextLine();
				if (!feat.contentEquals("1") && !feat.contentEquals("2")) {
					throw new Exception("Invalid input!");
				}
			}
			catch (Exception e) {
				valid = false;
				System.out.println("Invalid input! Please try again");
			}
		} while (!valid);
		
		switch (feat) {
			case "1":
				admin(sc, conn);
				break;
			case "2":
				exitTicketer();
				break;
			default:
				System.out.println("Invalid input! Please try again");
				break;
		}
	}
	
	static void getRevenue (Scanner sc, Connection conn) {
		Date date = null;
		CallableStatement cStmt = null;
		ResultSetMetaData rsmd = null;
		String revenue;
		int month, year;
		String sDate = null;
		String feat = "0";
		boolean valid = false;		
		//ask if user wanna get day or month revenue
		do {
			valid = true;
			try {
				System.out.println("What kind of revenue do you want to calculate?");
				System.out.println("1. A day's revenue");
				System.out.println("2. A month's revenue");
				System.out.println("3. Exit");
				feat = sc.nextLine();
				if (!feat.contentEquals("1") && !feat.contentEquals("2") && !feat.contentEquals("3")) {
					throw new Exception("Invalid input!");
				}
			}
			catch (Exception e) {
				valid = false;
				System.out.println("Invalid input! Please try again");
			}
		} while (!valid);
				
		if (feat.contentEquals("1")) { //get day's revenue
			try {
				//get input
				System.out.println("Enter date (YYYY-MM-DD): ");
				sDate = sc.nextLine();
				date = Date.valueOf(sDate);
				//call procedure and set parameters
				cStmt = conn.prepareCall("{call drevenue(?)}");
				cStmt.setDate(1, date);
				//execute and notify the success of the procedure
				ResultSet rs = cStmt.executeQuery();
				if(rs != null) {
					rsmd = rs.getMetaData();
					int colNum = rsmd.getColumnCount();
					while (rs.next()) {			
						for (int i = 1; i <= colNum; i++) {
							if (i > i)
								System.out.println(", ");
							String colValue = rs.getString(i);
							System.out.println(date + " revenue: " + colValue + " VND");
						}
						System.out.println("");
					}
				}		
				else {
					System.out.println("No data available! Please try again.");
				}
			} catch (SQLException e1) {
				System.out.println("SQL Exception: " + e1.getMessage());
			} finally {
				if (cStmt != null) {
					try {
						cStmt.close();
					} catch (SQLException e1) {
						System.out.println("SQL Exception: " + e1.getMessage());
					}
					cStmt = null;
				}
			}
		}
		else if (feat.contentEquals("2")) { //get month's revenue
			try {
				//get input
				System.out.println("Enter month: ");
				month = sc.nextInt();
				sc.nextLine();
				System.out.println("Enter year: ");
				year = sc.nextInt();
				sc.nextLine();
				//call procedure and set parameters
				cStmt = conn.prepareCall("{call mrevenue(?, ?)}");
				cStmt.setInt(1, month);
				cStmt.setInt(2, year);
				//execute and notify the success of the procedure
				ResultSet rs = cStmt.executeQuery();
				if(rs != null) {
					rsmd = rs.getMetaData();
					int colNum = rsmd.getColumnCount();
					while (rs.next()) {			
						for (int i = 1; i <= colNum; i++) {
							if (i > i)
								System.out.println(", ");
							String colValue = rs.getString(i);
							System.out.println(month + "-" + year + " revenue: " + colValue + " VND");
						}
						System.out.println("");
					}

				}	
				else {
					System.out.println("No data available! Please try again.");
				}
			} catch (SQLException e1) {
				System.out.println("SQL Exception: " + e1.getMessage());
			} finally {
				if (cStmt != null) {
					try {
						cStmt.close();
					} catch (SQLException e1) {
						System.out.println("SQL Exception: " + e1.getMessage());
					}
					
					cStmt = null;
				}
			}
		}
		else if (feat.contentEquals("3")) {
			exitTicketer();
		}
		//Ask user what do they want to do next
		do {
			valid = true;
			try {
				System.out.println("What do you want to do next?");
				System.out.println("1. Continue");
				System.out.println("2. Exit");
				feat = sc.nextLine();
				if (!feat.contentEquals("1") && !feat.contentEquals("2")) {
					throw new Exception("Invalid input!");
				}
			}
			catch (Exception e) {
				valid = false;
				System.out.println("Invalid input! Please try again");
			}
		} while (!valid);
		
		switch (feat) {
			case "1":
				admin(sc, conn);
				break;
			case "2":
				exitTicketer();
				break;
			default:
				System.out.println("Invalid input! Please try again");
				break;
		}
	}
	static void exitTicketer(){
		System.out.println("======* Thank you for using Ticketer! We hope to see you again *======");
		try
		{
		    Thread.sleep(3000);
		    System.exit(0);
		}
		catch(InterruptedException ex)
		{
		    Thread.currentThread().interrupt();
		}
		
	}

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		boolean valid = false;
		String mode = "0";
		Connection conn = null;
		String url = "jdbc:mysql://localhost:3306/Cinema";
		//url = "jdbc:mysql://localhost:port_number/database_name
		String username = "root";
		String password = "123456";
		
		try {
			conn = DriverManager.getConnection(url, username, password);
			System.out.println("Success! The program is now connected to the database.");
		} catch (Exception e) {
			System.out.println("Error! unable to connect to the database.");
			e.printStackTrace();
		}
		System.out.println("=======* Welcome to Ticketer, the ticket ordering application! *=======");	
		do {
			valid = true;
			try {
				System.out.println("Please choose a user mode to proceed");
				System.out.println("(1. Client, 2. Admin, 3. Exit)");
				mode = sc.nextLine();
				if (!mode.contentEquals("1") && !mode.contentEquals("2") && !mode.contentEquals("3")) {
					throw new Exception("Invalid input!");
				}
			}
			catch (Exception e) {
				valid = false;
				System.out.println("Invalid input! Please try again");
			}
		} while (!valid);
		
		switch (mode) {
			case "1":
				client(sc, conn);
				break;
			case "2":
				checkAdmin(sc, conn);
				break;
			case "3": 
				exitTicketer();
				break;
			default:
				System.out.println("Invalid input! Please try again");
				break;
		}
		
	}

}
