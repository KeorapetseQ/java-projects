/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tlhohonolofatsoqoane2437485;

import java.io.PrintWriter;
import java.io.PrintWriter;
import java.sql.*;

public class ReportTask implements Runnable {
    private String reportType;
    private String sqlQuery;

public ReportTask(String reportType, String sqlQuery) {
        this.reportType = reportType;
        this.sqlQuery = sqlQuery;
        
    }

    @Override
    public void run() {
        System.out.println("Generating " + reportType + " report in background thread...");
        String csvFile = reportType + "_Report.csv";
        
        try (PrintWriter writer = new PrintWriter(csvFile);
             Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sqlQuery)) {

            ResultSetMetaData meta = rs.getMetaData();
            int cols = meta.getColumnCount();
            
            StringBuilder header = new StringBuilder();
            for (int i = 1; i <= cols; i++) {
                if (i > 1) header.append(',');
                header.append(meta.getColumnLabel(i));
            }
            writer.println(header);
            
            while (rs.next()) {
               StringBuilder line = new StringBuilder();
                for (int i = 1; i <= cols; i++) {
                    if (i > 1) line.append(',');
                    String val = rs.getString(i);
                    if (val == null) {
                        line.append("");
                    } else if (val.contains(",") || val.contains("\"") || val.contains("\n")) {
                        line.append("\"").append(val.replace("\"", "\"\"")).append("\"");
                    } else {
                        line.append(val);
                    }
                }
                writer.println(line);
            }
            System.out.println("Background Report exported successfully to " + csvFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
