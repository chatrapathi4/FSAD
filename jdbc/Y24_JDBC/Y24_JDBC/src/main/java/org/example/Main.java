package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {

        String url = "jdbc:mysql://localhost:3306/myjdbc";
        String uname = "root";
        String pass = "Chatrapathi@09";

        Scanner sc = new Scanner(System.in);

        System.out.println("Choose Operation:");
        System.out.println("1. Insert");
        System.out.println("2. Delete");
        System.out.print("Enter choice: ");
        int choice = sc.nextInt();

        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection(url, uname, pass);

        if (choice == 1) {
            // INSERT
            System.out.print("Enter User ID: ");
            int userid = sc.nextInt();
            sc.nextLine(); // clear buffer

            System.out.print("Enter Username: ");
            String user = sc.nextLine();

            System.out.print("Enter Employee ID: ");
            int empid = sc.nextInt();

            String insertQuery =
                "INSERT INTO faculty (userid, username, empid) VALUES (?, ?, ?)";

            PreparedStatement st = con.prepareStatement(insertQuery);
            st.setInt(1, userid);
            st.setString(2, user);
            st.setInt(3, empid);

            int count = st.executeUpdate();
            System.out.println("Rows inserted: " + count);

            st.close();

        } else if (choice == 2) {
            // DELETE
            System.out.print("Enter User ID to delete: ");
            int userid = sc.nextInt();

            String deleteQuery =
                "DELETE FROM faculty WHERE userid = ?";

            PreparedStatement st = con.prepareStatement(deleteQuery);
            st.setInt(1, userid);

            int count = st.executeUpdate();
            System.out.println("Rows deleted: " + count);

            st.close();

        } else {
            System.out.println("Invalid choice");
        }

        con.close();
        sc.close();
    }
}
