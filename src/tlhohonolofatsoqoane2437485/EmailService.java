package tlhohonolofatsoqoane2437485;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class EmailService {

    private static final String ADMIN_EMAIL = "Tlhohonolofatsok.qoane@gmail.com";
    private static final String APP_PASSWORD = System.getProperty("GMAIL_APP_PASSWORD");

    public static void sendEmail(String recipientEmail, String subject, String messageBody) {
        System.out.println("--- OUTBOUND EMAIL NOTIFICATION ---");
        System.out.println("To: " + recipientEmail);
        System.out.println("Subject: " + subject);
        System.out.println("---------");

        if (APP_PASSWORD == null || APP_PASSWORD.isEmpty()) {
            System.out.println("GMAIL_APP_PASSWORD not set - email simulated only.");
            return;
        }
        
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(ADMIN_EMAIL, APP_PASSWORD);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(ADMIN_EMAIL));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(ADMIN_EMAIL));
            message.setSubject(subject);
            message.setText(messageBody);
            Transport.send(message);
            System.out.println("Email sent successfully.");
        } catch (MessagingException e) {
            System.err.println("Failed to send email: " + e.getMessage());
        }
    }

public static void sendEmailForClaimStatus(String claimNumber, String status) {
    sendEmail(ADMIN_EMAIL,
        "UbuntuSure Claim " + claimNumber + " - " + status,
        "Claim " + claimNumber + " has been assessed.\nNew status: " + status);
}

public static void sendPolicyEmail(String customerId, String policyNumber, String policyType, double annualPremium) {
        String recipientEmail = getCustomerEmail(customerId);
        if (recipientEmail == null || recipientEmail.isEmpty()) {
            recipientEmail = ADMIN_EMAIL; // Fallback to admin if customer email isn't found
        }
        
        String subject = "UbuntuSure - New " + policyType + " Policy Created (" + policyNumber + ")";
        String messageBody = """
                             Dear Customer,
                             
                             Your new """ + policyType + " policy has been successfully created.\n"
                + "Policy Number: " + policyNumber + "\n"
                + "Calculated Annual Premium: M" + annualPremium + "\n\n"
                + "Thank you for choosing UbuntuSure Insurance!";
        
        sendEmail(recipientEmail, subject, messageBody);
    }

    /**
     * Helper to fetch customer email from the database by customer ID
     */
    private static String getCustomerEmail(String customerId) {
        String query = "SELECT email FROM customers WHERE customer_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, customerId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("email");
                }
            }
        } catch (Exception e) {
            System.err.println("Error fetching customer email: " + e.getMessage());
        }
        return null;
    }

}