package com.slk.program;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;






public class Example {

	static int id = 0;
	static String firstname = null;
	static String lastname = null;
	static String address = null;
	static String emailid = null;
	static int age = 0;
	
	
	static int choice;
	public static void main(String[] args) {
		
		do{
		Scanner sc=new Scanner(System.in);
		System.out.println("case 1:insert");
		System.out.println("case 2:delete");
		System.out.println("case 3:update");
		System.out.println("case 4:get all details");
		System.out.println("case 5:copy to other csv");
		System.out.println("case 6:load to database");
		System.out.println("case 0:to exit");
		System.out.println("Enter your choice");
		choice=sc.nextInt(); 
		

		switch (choice) {
		case 0:
			break;
			
		case 1:
			insert();
			break;
		case 2:
			delete();
			break;
		case 3:
			update();
			break;
		case 4:
			getAllDetails();
			break;
		case 5:
			copyToCsv();
			break;
		case 6:
			loadToDB();
			break;

		}}while(choice>0&&choice<7);
	}

	private static void loadToDB() {
		String filename = "D://emp.csv";
		try (FileReader reader = new FileReader(filename); BufferedReader in = new BufferedReader(reader);) {
			String line;
			in.readLine();
			while ((line = in.readLine()) != null) {
				String[] ar = line.split(",");
				id = Integer.parseInt(ar[0]);
				firstname = ar[1];
				lastname = ar[2];
				address = ar[3];
				emailid = ar[4];
				age = Integer.parseInt(ar[5]);
				
				System.out.println("id----------------" + id);
				System.out.println("firstname---------------" + firstname);
				System.out.println("lastname---------------" + lastname);
				System.out.println("address---------------" + address);
				System.out.println("emailid---------------" + emailid);
				System.out.println("age---------------" + age);
				
				System.out.println("Id      :" + ar[0]);
				System.out.println("firstName     :" + ar[1]);
				System.out.println("lastName     :" + ar[2]);
				System.out.println("address    :" + ar[3]);
				System.out.println("emailid    :" + ar[4]);
				System.out.println("age     :" + ar[5]);
				
				System.out.println();
				String sql = "insert into emp values(?, ?,?,?,?,?)";
				try (Connection conn = openConnection(); PreparedStatement stmt = conn.prepareStatement(sql);) {

					stmt.setInt(1, id);
					stmt.setString(2, firstname);
					stmt.setString(3, lastname);
					stmt.setString(4, address);
					stmt.setString(5, emailid);
					stmt.setInt(6, age);

					stmt.execute();

				} catch (Exception ex) {

					ex.printStackTrace();
				}
			}
		} catch (Exception ex) {
		}		
	}

	private static void copyToCsv() {
		// Input file which needs to be parsed
				FileInputStream instream = null;
				File infile = new File("D://emp.csv");
				

				try {
					instream = new FileInputStream(infile);
				} catch (FileNotFoundException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				String fileToParse = "D://emp.csv";
				File outfile = new File("D://empnew.csv");
				FileOutputStream outstream = null;
				BufferedReader fileReader = null;
				byte[] buffer = new byte[2048];
				try {
					outstream = new FileOutputStream(outfile);
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				// Delimiter used in CSV file
				final String DELIMITER = ",";
				try {
					String line = "";
					int length;
					while ((length = instream.read(buffer)) > 0) {
						outstream.write(buffer, 0, length);
					}
					// Create the file reader
					fileReader = new BufferedReader(new FileReader(fileToParse));

					// Read the file line by line
					while ((line = fileReader.readLine()) != null) {
						// Get all tokens available in line
						String[] tokens = line.split(DELIMITER);
						for (String token : tokens) {
							// Print all tokens
							System.out.printf(token);
							System.out.println();
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}		
	}

	private static void getAllDetails() {
		List<String> list = new ArrayList<>();
		try (Connection conn = openConnection();
				PreparedStatement stmt = conn.prepareStatement("select * from emp");
				ResultSet rs = stmt.executeQuery();) {
			while (rs.next()) {
				
				id=rs.getInt("id");
				firstname=rs.getString("firstname");
				lastname=rs.getString("lastname");
				address=rs.getString("address");
				emailid=rs.getString("emailid");
				age=rs.getInt("age");
				System.out.println(id);
				System.out.println(firstname);
				System.out.println(lastname);
				System.out.println(address);
				System.out.println(emailid);
				System.out.println(age);

				
				
			
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private static void update() {
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter id to update record");
		id=sc.nextInt();
		System.out.println("Enter firstname");
		firstname=sc.next();
		System.out.println("Enter lastname");
		lastname=sc.next();
		System.out.println("Enter address");
		address=sc.next();
		System.out.println("Enter emailid");
		emailid=sc.next();
		System.out.println("Enter age");
		age=sc.nextInt();
		
		String sql = "update emp set firstname=?,lastname=?,address=?,emailid=?,age=? where id=?";

		try (Connection conn = openConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);) {
			
			stmt.setString(1, firstname);
			stmt.setString(2, lastname);
			stmt.setString(3, address);
			stmt.setString(4, emailid);
			stmt.setInt(5, age);
			stmt.setInt(6, id);
			stmt.executeUpdate();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private static void delete() {
		Scanner sc=new Scanner(System.in);
		
		int id;
		System.out.println("Enter id");
		id=sc.nextInt();
		
		
		
		String sql = "delete from emp where id=?";
		try (Connection conn = openConnection(); PreparedStatement stmt = conn.prepareStatement(sql);) {

			stmt.setInt(1, id);
			

			stmt.execute();

		} catch (Exception ex) {

			ex.printStackTrace();
		}
	}

	private static void insert() {
		Scanner sc=new Scanner(System.in);
		String firstname;
		String lastname;
		String address;
		String emailid;
		int age;
		int id;
		System.out.println("Enter id");
		id=sc.nextInt();
		System.out.println("Enter firstname");
		
		firstname=sc.next();
		System.out.println("Enter lastname");
		lastname=sc.next();
		System.out.println("Enter address");
		address=sc.next();
		System.out.println("Enter emailid");
		emailid=sc.next();
		System.out.println("Enter age");
		age=sc.nextInt();
		
		
		String sql = "insert into emp values(?, ?,?,?,?,?)";
		try (Connection conn = openConnection(); PreparedStatement stmt = conn.prepareStatement(sql);) {

			stmt.setInt(1, id);
			stmt.setString(2, firstname);
			stmt.setString(3, lastname);
			stmt.setString(4, address);
			stmt.setString(5, emailid);
			
			stmt.setInt(6, age);



			stmt.execute();

		} catch (Exception ex) {

			ex.printStackTrace();
		}
		
	}

	public static Connection openConnection() throws ClassNotFoundException, SQLException {

		Class.forName("org.h2.Driver");
		String url = "jdbc:h2:tcp://localhost/~/test";
		String user = "sa";
		String password = "";
		return DriverManager.getConnection(url, user, password);
	}

}
