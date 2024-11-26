package HospitalManagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Patient {
private Connection con;
private Scanner scan;

public Patient(Connection con,Scanner scan) {
	this.con = con;
	this.scan = scan;
}
public void addpatient() {
	System.out.print("Enter Patient Name :");
	String name = scan.next();

	System.out.print("Enter Patient Age :");
	int age = scan.nextInt();

	System.out.print("Enter Patient Gender :");
	String gen = scan.next();

	try {
		String query ="INSERT INTO PATIENT (name,age,gender) VALUES(?,?,?)";
		PreparedStatement pst = con.prepareStatement(query);
		pst.setString(1, name);
		pst.setInt(2, age);
		pst.setString(3,gen);
       int affectrow =  pst.executeUpdate();
       if(affectrow>0) {
    	   System.out.println("Patient added Successfully");
       }
       else {
    	   System.out.println("Failed to add Patient");
       }
	}
	catch(Exception e) {
		e.printStackTrace();
		System.out.println(e.getMessage());
	}
}
	public void viewpatient() {
		String que = "SELECT * FROM PATIENT";
		ResultSet rs;
		try {
			PreparedStatement pst = con.prepareStatement(que);
			rs = pst.executeQuery();
			System.out.println("Patients :");
			System.out.println("=============+===============+=====+========+");
			System.out.println("| Patient Id | Name          | Age | Gender |");
			System.out.println("=============+===============+=====+========+");

			while(rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				int age = rs.getInt("age");
				String gender = rs.getString("gender");
                System.out.printf("|%-12s|%-15s|%-5s|%-8s|\n",id,name,age,gender);
    			System.out.println("=============+===============+=====+========+");
  
			}
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	
	}
	
	public boolean checkPatient(int id) {
		
		String query = "SELECT * FROM PATIENT WHERE ID =?";
		
		try {
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, id);
			ResultSet rs =ps.executeQuery();
			if(rs.next()) {
				return true;
			}
			else {
				return false;
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return false;
		
	}
}

