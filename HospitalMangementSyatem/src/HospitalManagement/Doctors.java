package HospitalManagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;


public class Doctors {
	private Connection con;
	private Scanner scan;

	public Doctors(Connection con) {
this.con=con;

	
	}
		public void viewdoctors() {
			String que = "SELECT * FROM Doctors";
			ResultSet rs;
			try {
				PreparedStatement pst = con.prepareStatement(que);
				rs = pst.executeQuery();
				System.out.println("Doctors :");
				System.out.println("=============+===============+==============+");
				System.out.println("| Doctor Id | Name          |Specialization |");
				System.out.println("=============+===============+==============+");
                while(rs.next()) {
					int id = rs.getInt("id");
					String name = rs.getString("name");
					String spec = rs.getString("specialization");
	                System.out.printf("|%-11s|%-15s|%-15s|\n",id,name,spec);
					System.out.println("=============+===============+==============+");
	  
				}
			}catch(Exception e) {
				System.out.println(e.getMessage());
			}
		
		}
		
		public boolean getdoctor(int id) {
			
			String query = "SELECT * FROM DOCTORS WHERE ID =?";
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
