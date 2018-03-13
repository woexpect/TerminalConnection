package co.com.pgr;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Properties;
import java.util.Scanner;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public class Main {
	
	public static String user = "2092063";
	public static String password = "Simpsons741";
	public static String host = "10.2.67.116";
	public static String commandInput = "";
	public static int port = 22;
	public static JSch jsch = null;
	public static Session session = null;
	public static ChannelExec channel = null;
	public static BufferedReader in = null;
	public static Scanner javaInput = null;
	public static Properties config = null; 
	

	public static void main(String[] args) {
		
		javaInput = new Scanner(System.in);
//		getUserCredentials();
		createConnection();
		menuInteraction();
		closeConnection();
		
	}

	private static void getUserCredentials() {
		
		System.out.println("Please, enter your user name:");
		user = javaInput.nextLine();
		System.out.println("Please, enter your password for user name [" + user + "] :");
		password = javaInput.nextLine();		
	}

	private static void menuInteraction() {
		
		try {
			
			String msg = null;
			System.out.println(user + "@java: ");
			commandInput = javaInput.nextLine();
			
			while(commandInput != null || !commandInput.equals("")) {
				
				channel.setCommand(commandInput);
				channel.connect();
				
				while((msg = in.readLine()) != null){
					System.out.println(msg + "a");
				}
				if (!session.isConnected()) {
					System.out.println("Session dwon.");
					commandInput = "";
		        } else {
		        	System.out.println(user + "@java: ");
		        	commandInput = javaInput.nextLine();
		        }
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			closeConnection();
		}
		
	}

	private static void closeConnection() {
		channel.disconnect();
		session.disconnect();
	}

	private static void createConnection() {
		
		try {
			//Creation of the JSch object
			jsch = new JSch();
			//Creation of the Session Object
			session = jsch.getSession(user, host, 22);
			session.setPassword(password);
			//Setting Connection properties
			config = new Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);
			//Create connection to remote server
			System.out.println("Establishing Connection...");
			session.connect();
			System.out.println("Connection established.");
			//Creation of the Command execution channel object.
			System.out.println("Opening command channel.");
			channel = (ChannelExec) session.openChannel("exec");
			System.out.println("Channel open.");
			//Set the input reader of channel conection. 
			in = new BufferedReader(new InputStreamReader(channel.getInputStream()));
			System.out.println("Welcome, " + user + ".");
		} catch (Exception e) {
			System.out.println("An error ocurred while creating connection.");
			e.printStackTrace();
		}
		
	}

}