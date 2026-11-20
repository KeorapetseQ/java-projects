/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tlhohonolofatsoqoane2437485;

import java.sql.*;

public class PolicyDAO {
    public boolean savePolicy(int customerId, String policyType, String startDate, String endDate, 
                              double coverageAmount, double premium, String status) {
        String sql = "INSERT INTO Policies (customer_id, policy_type, start_date, end_date, coverage_amount, premium, status) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setInt(1, customerId);
            pstmt.setString(2, policyType);
            pstmt.setString(3, startDate);
            pstmt.setString(4, endDate);
            pstmt.setDouble(5, coverageAmount);
            pstmt.setDouble(6, premium); // Enforces BR-P08: pre-calculated premium
            pstmt.setString(7, status);
            
            int affected = pstmt.executeUpdate();
            return affected > 0;
        } catch (SQLException e) {
            System.err.println("Error saving policy: " + e.getMessage());
            return false;
        }
    }
}
