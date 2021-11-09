package doAn;

import java.sql.*;
import java.sql.Date;
import java.util.*;

public class MySQLConnect {
	
	static void ticketOrder (Scanner sc, Connection conn) {
		char feat = 0;
		boolean valid = false;
		System.out.println("Here is the list of Films with available tickets:");
		System.out.println("...");
		do {
			valid = true;
			try {
				System.out.println("What do you want to do next?");
				System.out.println("1. Continue using Ticketer");
				System.out.println("2. Exit");
				feat = sc.next().charAt(0);
				if (feat != '1' && feat != '2') {
					throw new Exception("Invalid input!");
				}
			}
			catch (Exception e) {
				valid = false;
				System.out.println("Invalid input! Please try again");
			}
		} while (!valid);
		
		if (feat == '1') {
			client(sc, conn);
		}
		else {
			exitTicketer();
		}
	}
	
	static void lookupSchedule (Scanner sc, Connection conn) {
		char feat = 0;
		boolean valid = false;
		System.out.println("Here is the schedule of upcoming Films:");
		System.out.println("...");
		do {
			valid = true;
			try {
				System.out.println("What do you want to do next?");
				System.out.println("1. Continue using Ticketer");
				System.out.println("2. Exit");
				feat = sc.next().charAt(0);
				if (feat != '1' && feat != '2') {
					throw new Exception("Invalid input!");
				}
			}
			catch (Exception e) {
				valid = false;
				System.out.println("Invalid input! Please try again");
			}
		} while (!valid);
		
		if (feat == '1') {
			client(sc, conn);
		}
		else {
			exitTicketer();
		}
	}
	
	static void client(Scanner sc, Connection conn) {
		char feat = 0;
		boolean valid = false;
		System.out.println("Hello dear client! How may Ticketer help you today?");
		do {
			valid = true;
			try {
				System.out.println("Please choose a feature you wish to use: ");
				System.out.println("1. Order a ticket");
				System.out.println("2. Look up Film schedule");
				System.out.println("3. Exit");
				feat = sc.next().charAt(0);
				if (feat != '1' && feat != '2' && feat  != '3') {
					throw new Exception("Invalid input!");
				}
			}
			catch (Exception e) {
				valid = false;
				System.out.println("Invalid input! Please try again");
			}
		} while (!valid);
		
		if (feat == '1') {
			ticketOrder(sc, conn);
		}
		else if (feat == '2') {
			lookupSchedule(sc, conn);
		}
		else {
			exitTicketer();
		}
		
	}
	
	static void checkAdmin(Scanner sc, Connection conn) { //Get password from admin user
		boolean valid = false;
		String password = new String();
		do {
			valid = true;
			try {
				System.out.println("To proceed in administrator mode, please enter the correct password");
				System.out.println("or press 3 to exit");
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
		else if (password == "3") {
			exitTicketer();
		}	
	}
	
	static void admin(Scanner sc, Connection conn) {
		char feat = 0;
		boolean valid = false;
		do {
			valid = true;
			try {
				System.out.println("Please choose a feature you wish to use: ");
				System.out.println("1. Add new Film info");
				System.out.println("2. Add new theater info");
				System.out.println("3. Add new scheduling info");
				System.out.println("4. Add new customer info");
				System.out.println("5. Exit");
				feat = sc.next().charAt(0);
				if (feat != '1' && feat != '2' && feat  != '3' && feat  != '4' && feat  != '5') {
					throw new Exception("Invalid input!");
				}
			}
			catch (Exception e) {
				valid = false;
				System.out.println("Invalid input! Please try again");
			}
		} while (!valid);
		
		if (feat == '1') {
			addFilm(sc, conn);
		}
		else if (feat == '2') {
			addTheater(sc, conn);
		}
		else if (feat == '3') {
			addSchedule(sc, conn);
		}
		else if (feat == '4') {
			addCustomer(sc, conn);
		}
		else {
			exitTicketer();
		}
	}
	
	static void addFilm (Scanner sc, Connection conn) {
		Statement stmt = null;
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;
		CallableStatement cStmt = null;
		String id, name, genre, sreleaseDate, origin, lang, limit;
		int duration;
		char feat = 0;
		boolean valid = false;
		//get current info of Films table
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select * from Films");
			rsmd = rs.getMetaData();
			int colNum = rsmd.getColumnCount();
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
		System.out.println("Please insert new film infomation:");
		System.out.println("Film's ID: ");
		id = sc.nextLine();
		System.out.println("Film's name: ");
		name = sc.nextLine();
		System.out.println("Film's genre: ");
		genre = sc.nextLine();
		System.out.println("Film's duration (minutes): ");
		duration = sc.nextInt();
		System.out.println("Film's release date (YYYY-MM-DD): ");
		sreleaseDate = sc.nextLine();
		Date releaseDate = Date.valueOf(sreleaseDate);
		System.out.println("Film's origin (country): ");
		origin = sc.nextLine();
		System.out.println("Film's language: ");
		lang = sc.nextLine();
		System.out.println("Film's age limit: ");
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
				System.out.println("new Film added successfully");
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
				feat = sc.next().charAt(0);
				if (feat != '1' && feat != '2') {
					throw new Exception("Invalid input!");
				}
			}
			catch (Exception e) {
				valid = false;
				System.out.println("Invalid input! Please try again");
			}
		} while (!valid);
		
		if (feat == '1') {
			admin(sc, conn);
		}
		else {
			exitTicketer();
		}
	}
	
	static void addTheater (Scanner sc, Connection conn) {
		Statement stmt = null;
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;
		CallableStatement cStmt = null;
		String id, name, genre, sreleaseDate, origin, lang, limit;
		int duration;
		char feat = 0;
		boolean valid = false;
		//get current info of Films table
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select * from Films");
			rsmd = rs.getMetaData();
			int colNum = rsmd.getColumnCount();
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
		System.out.println("Please insert new film infomation:");
		System.out.println("Film's ID: ");
		id = sc.nextLine();
		System.out.println("Film's name: ");
		name = sc.nextLine();
		System.out.println("Film's genre: ");
		genre = sc.nextLine();
		System.out.println("Film's duration (minutes): ");
		duration = sc.nextInt();
		System.out.println("Film's release date (YYYY-MM-DD): ");
		sreleaseDate = sc.nextLine();
		Date releaseDate = Date.valueOf(sreleaseDate);
		System.out.println("Film's origin (country): ");
		origin = sc.nextLine();
		System.out.println("Film's language: ");
		lang = sc.nextLine();
		System.out.println("Film's age limit: ");
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
				System.out.println("new Film added successfully");
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
				feat = sc.next().charAt(0);
				if (feat != '1' && feat != '2') {
					throw new Exception("Invalid input!");
				}
			}
			catch (Exception e) {
				valid = false;
				System.out.println("Invalid input! Please try again");
			}
		} while (!valid);
		
		if (feat == '1') {
			admin(sc, conn);
		}
		else {
			exitTicketer();
		}
	}
	
	static void addSchedule (Scanner sc, Connection conn) {
		Statement stmt = null;
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;
		CallableStatement cStmt = null;
		String id, name, genre, sreleaseDate, origin, lang, limit;
		int duration;
		char feat = 0;
		boolean valid = false;
		//get current info of Films table
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select * from Films");
			rsmd = rs.getMetaData();
			int colNum = rsmd.getColumnCount();
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
		System.out.println("Please insert new film infomation:");
		System.out.println("Film's ID: ");
		id = sc.nextLine();
		System.out.println("Film's name: ");
		name = sc.nextLine();
		System.out.println("Film's genre: ");
		genre = sc.nextLine();
		System.out.println("Film's duration (minutes): ");
		duration = sc.nextInt();
		System.out.println("Film's release date (YYYY-MM-DD): ");
		sreleaseDate = sc.nextLine();
		Date releaseDate = Date.valueOf(sreleaseDate);
		System.out.println("Film's origin (country): ");
		origin = sc.nextLine();
		System.out.println("Film's language: ");
		lang = sc.nextLine();
		System.out.println("Film's age limit: ");
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
				System.out.println("new Film added successfully");
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
				feat = sc.next().charAt(0);
				if (feat != '1' && feat != '2') {
					throw new Exception("Invalid input!");
				}
			}
			catch (Exception e) {
				valid = false;
				System.out.println("Invalid input! Please try again");
			}
		} while (!valid);
		
		if (feat == '1') {
			admin(sc, conn);
		}
		else {
			exitTicketer();
		}
	}
	
	static void addCustomer (Scanner sc, Connection conn) {
		Statement stmt = null;
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;
		CallableStatement cStmt = null;
		String id, name, genre, sreleaseDate, origin, lang, limit;
		int duration;
		char feat = 0;
		boolean valid = false;
		//get current info of Films table
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select * from Films");
			rsmd = rs.getMetaData();
			int colNum = rsmd.getColumnCount();
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
		System.out.println("Please insert new film infomation:");
		System.out.println("Film's ID: ");
		id = sc.nextLine();
		System.out.println("Film's name: ");
		name = sc.nextLine();
		System.out.println("Film's genre: ");
		genre = sc.nextLine();
		System.out.println("Film's duration (minutes): ");
		duration = sc.nextInt();
		System.out.println("Film's release date (YYYY-MM-DD): ");
		sreleaseDate = sc.nextLine();
		Date releaseDate = Date.valueOf(sreleaseDate);
		System.out.println("Film's origin (country): ");
		origin = sc.nextLine();
		System.out.println("Film's language: ");
		lang = sc.nextLine();
		System.out.println("Film's age limit: ");
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
				System.out.println("new Film added successfully");
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
				feat = sc.next().charAt(0);
				if (feat != '1' && feat != '2') {
					throw new Exception("Invalid input!");
				}
			}
			catch (Exception e) {
				valid = false;
				System.out.println("Invalid input! Please try again");
			}
		} while (!valid);
		
		if (feat == '1') {
			admin(sc, conn);
		}
		else {
			exitTicketer();
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
		char mode = '0';
		Connection conn = null;
		String url = "jdbc:mysql://localhost:3306/qldiem";
		//url = "jdbc:mysql://localhost:port_number/database_name
		String username = "root";
		String password = "123456";
		
		try {
			conn = DriverManager.getConnection(url, username, password);
			System.out.println("Noi ket voi csdl thanh cong!");
		} catch (Exception e) {
			System.out.println("Noi ket voi csdl that bai");
			e.printStackTrace();
		}
		System.out.println("=======* Welcome to Ticketer, the ticket ordering application! *=======");	
		do {
			valid = true;
			try {
				System.out.println("Please choose a user mode to proceed");
				System.out.println("(1. Client, 2. Admin, 3. Exit)");
				mode = sc.next().charAt(0);
				if (mode != '1' && mode != '2' && mode  != '3') {
					throw new Exception("Invalid input!");
				}
			}
			catch (Exception e) {
				valid = false;
				System.out.println("Invalid input! Please try again");
			}
		} while (!valid);
		
		if (mode == '1') {
			client(sc, conn);
		}
		else if (mode == '2') {
			checkAdmin(sc, conn);
		}
		else {
			exitTicketer();
		}
		
	}

}
