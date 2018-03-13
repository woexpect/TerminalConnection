package co.com.pgr;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class Main {
	
	public static String user = "2092063";
	public static String password = "";
	public static String host = "10.2.67.116";
	public static int port = 22;
	public static JSch jsch = null;
	public static Session session = null;
	public static ChannelExec channel = null;
	public static BufferedReader in = null;

	public static void main(String[] args) {
		
		createConnection();
		menuInteraction();
		closeConnection();
		
	}

	private static void menuInteraction() {
		
		try {
			String msg=null;
			while((msg=in.readLine())!=null){
			  System.out.println(msg);
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private static void closeConnection() {
		// TODO Auto-generated method stub
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
			Properties config = new Properties();
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
			channel.setCommand("ls");
			channel.connect();
		} catch (Exception e) {
			System.out.println("An error ocurred while creating connection.");
			e.printStackTrace();
		}
		
	}

}