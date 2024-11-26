package HospitalManagement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Mangement {
private static final String url="jdbc:mysql://localhost:3306/hospital";
private static final String username="root";
private static final String password="password";
public static void main(String[] args) throws ClassNotFoundException, SQLException {
	try {
		Scanner scan = new Scanner(System.in);

		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con = DriverManager.getConnection(url,username,password);
		Patient patient = new Patient(con,scan);
		Doctors doc = new Doctors(con);
		while(true) {
			System.out.println("Hospital Mangement System ");
			System.out.println("1: Add Patient");
			System.out.println("2: View Patient");
			System.out.println("3: View Doctors");
			System.out.println("4: Book Appointment");
			System.out.println("5: View Appointments");
			System.out.println("6: Exit");
			System.out.println("Enter Your Choice :");
			int choice = scan.nextInt();
			switch(choice) {
			case 1:{
				patient.addpatient();
				break;
			}
			case 2:{
				patient.viewpatient();
				break;
			}
			case 3:{
				//view doctors
				doc.viewdoctors();
				break;
			}
			case 4:{
				//book
				bookappointment(patient,doc, con,scan);
				break;
			}
			case 5:{
				//Check Appointments
				checkappointment(doc,con,scan);
			}
			case 6:{
				//Exit
				return;
			}
			default:
				System.out.println("Enter Valid Choice!");
			}



		}
	}
	catch(Exception e) {
		e.printStackTrace();
		System.out.println(e.getMessage());
	}
}
 public  static void checkappointment(Doctors doc, Connection con, Scanner scan) throws SQLException {
   
	 System.out.println("Enter the Patient-ID :");
	 int id1 = scan.nextInt();
	 String que = "SELECT * FROM APPOINTMENT WHERE PATIENT_ID=?";
	 PreparedStatement ps = con.prepareStatement(que);
	 ps.setInt(1, id1);
	 ResultSet res = ps.executeQuery();
	 System.out.println("Your Appointments are :");
	    System.out.println("=============+===============+===========+");
		System.out.println("| Patient Id  |  Doctor Id   |    Date   |");
		System.out.println("=============+===============+===========+");
	 while(res.next()) {
		 int id = res.getInt("patient_id");
		 int id2 = res.getInt("doctor_id");
		 String Date = res.getString("appointment_date");
         System.out.printf("|%-12s|%-15s|%-11s|\n",id,id2,Date);
 		System.out.println("=============+===============+===========+");

		 
	 }
	 
}
public static void  bookappointment(Patient pat,Doctors doc,Connection con,Scanner scan) {
	System.out.println("Enter Patient Id :");
	int id1 = scan.nextInt();
	System.out.println("Enter Doctors Id :");
	int id2 = scan.nextInt();
	System.out.println("Enter Appointment Date(YYYY-MM-DD) :");
    String date = scan.next();
    if(pat.checkPatient(id1) && doc.getdoctor(id2)) {
    	if(checkavailable(id2,date)) {
    		String  appquery = "INSERT INTO APPOINTMENT(patient_id,doctor_id,appointment_date) VALUES (?,?,?)";
    		try {
    			PreparedStatement ps = con.prepareStatement(appquery);
    			ps.setInt(1, id1);
    			ps.setInt(2, id2);
    			ps.setString(3, date);
    			int res = ps.executeUpdate();
    			if(res>0) {
    				System.out.println("Booked Appointment Successfully!");
    			}
    			else {
    				System.out.println("Failed to book appointment due to Doctors Unavalability!");
    				System.out.println("You can Try different Date or Doctor");

    			}
    		}catch(Exception e) {
    			e.printStackTrace();
    		}
    	}else {
        	System.out.println("Either Doctor or Patient Dosen't Exist!");
			System.out.println("You can Try different Date or Doctor");

        }
    }
    
}

public static boolean checkavailable(int id2,String date) {
	String que = "Select COUNT(*) from appointment where doctor_id = ? AND appointment_date=?";
	try {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con = DriverManager.getConnection(url,username,password);
		PreparedStatement ps  = con.prepareStatement(que);
		ps.setInt(1, id2);
		ps.setString(2,date);
		ResultSet rs = ps.executeQuery();
		if(rs.next()) {
			int count = rs.getInt(1);
			if(count==0) {
				return true;
			}else {
				return false;
			}
		}
	}catch(Exception e) {
		e.printStackTrace();
	}
	
	return false;
}
}
