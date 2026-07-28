/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.swp.whmsystem.api;

import java.util.Properties;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

public class EmailApi {

    public static boolean sendEmail(String toEmail, String userPass) {
        // Cấu hình SMTP của Gmail
        final String fromEmail = "ducanh06tb@gmail.com";
        final String password = "mwwj uqlx voih ldun";

        //thiết lập cấu hình trong java để kết nối tới máy chủ gửi email của google
        Properties props = new Properties();                //đối tượng properties lưu thông số cấu hình dưới dạng key - value
        props.put("mail.smtp.host", "smtp.gmail.com");      //chỉ định địa chỉ máy chủ gửi email, vì dùng gmail nên địa chỉ là smtp.gmail.com
        props.put("mail.smtp.port", "587");                 //khai báo port kết nối máy chủ, đối với gmail thì 587 là cổng tiêu chuẩn hỗ trợ TLS
        props.put("mail.smtp.auth", "true");                //bật tính năng yêu cầu xác thực email và password
        props.put("mail.smtp.starttls.enable", "true");     //kích hoạt giao thức bảo mật STARTTLS, giao thức này có nhiệm vụ nâng cấp 1 kết nối
                                                            //mạng bình thường thành 1 kết nối mã hóa an toàn. Bảo vệ nội dung email ko bị đánh cắp

        //hàm kiểm tra thông tin gmail có chính xác với fromEmail và password không
        Authenticator authenticator = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        };

        //mỗi lần gọi getInstance, nó sẽ tạo ra một phiên làm việc hoàn toàn mới và độc lập cho cấu hình đó tránh email trùng nhau
        Session session = Session.getInstance(props, authenticator);
        try {
            Message message = new MimeMessage(session);                 //MIME - Multipurpose Internet Mail Extensions: thư đạt chuẩn Internet
            message.setFrom(new InternetAddress(fromEmail));            //ghi tên người gửi
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));        //ghi tên người nhận
                                    //.CC đồng gửi, .BCC ẩn danh
            message.setSubject("Resend the password to the user.");         //ghi tiêu đề
            message.setText("Hello, \n\nThis is the password to log in to your account: " + userPass + 
                    "\n\nPlease do not share this code with anyone.");      //ghi nội dung

            Transport.send(message);                                        //giao email cho Transport
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }
}
