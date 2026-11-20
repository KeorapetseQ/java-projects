/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tlhohonolofatsoqoane2437485;

import java.sql.*;

public class CustomerDAO {

    public boolean registerCustomer(String firstName, String surname, String nationalId, String dob, String gender, String phone, String email, String address) {
        // 1. Check National ID uniqueness (BR-C02)
        String checkQuery = "SELECT COUNT(*) FROM customers WHERE national_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkQuery)) {
            checkStmt.setString(1, nationalId);
            try (ResultSet rs = checkStmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    return false; // Duplicate found, blocks insertion
                }
            }
        } catch (Exception e) {
            System.err.println("Error checking National ID uniqueness: " + e.getMessage());
            return false;
        }

        // 2. Generate unique Customer ID (BR-C01)
        String customerId = "CUST-" + (System.currentTimeMillis() % 100000);

        // 3. Insert customer record
        String insertQuery = "INSERT INTO customers (customer_id, first_name, surname, national_id, date_of_birth, gender, phone_number, email, physical_address) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(insertQuery)) {
            
            pstmt.setString(1, customerId);
            pstmt.setString(2, firstName);
            pstmt.setString(3, surname);
            pstmt.setString(4, nationalId);
            pstmt.setDate(5, java.sql.Date.valueOf(dob));
            pstmt.setString(6, gender);
            pstmt.setString(7, phone);
            pstmt.setString(8, email);
            pstmt.setString(9, address);
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (Exception e) {
            System.err.println("Error saving customer: " + e.getMessage());
            return false;
        }
    }
}