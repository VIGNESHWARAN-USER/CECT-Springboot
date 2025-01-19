package com.cect.backend.Services;
import com.cect.backend.Models.Users;
import com.cect.backend.Repositories.UsersRepo;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MainServices {

    @Autowired
    UsersRepo userrepo;
    @Autowired
    private JavaMailSender mailSender;

    public List<Users> demo() {
        return userrepo.findAll();
    }

    public ResponseEntity<?> getLoginInfo(String email, String pass) {
        Users user = userrepo.findAll().stream().filter(one->email.equals(one.getEmail())).findFirst().orElse(null);
        if(user != null)
        {
            if(user.getPass().equals(pass))
                return new ResponseEntity<>(user,HttpStatusCode.valueOf(200));
            else
                return new ResponseEntity<>(HttpStatusCode.valueOf(201));
        }
        else
            return new ResponseEntity<>(HttpStatusCode.valueOf(202));
    }


    public ResponseEntity<?> updatePassword(String email, String newPassword) {
        Users user = userrepo.findAll().stream().filter(one-> email.equals(one.getEmail())).findFirst().orElse(null);
        if (user != null)
        {
            user.setPass(newPassword);
            userrepo.save(user);
            return new ResponseEntity<>(HttpStatusCode.valueOf(200));
        }
        else
            return new ResponseEntity<>(HttpStatusCode.valueOf(201));
    }

    public ResponseEntity<?> sendOtpEmail(String email, String otp) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            helper.setTo(email);
            helper.setSubject("PASSWORD RECOVERY");
            helper.setText(buildEmailContent(otp), true);
            mailSender.send(mimeMessage);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private String buildEmailContent(String otp) {
        StringBuilder emailContent = new StringBuilder();

        emailContent.append("<html>")
                .append("<body>")
                .append("<div style=\"font-family: Helvetica,Arial,sans-serif;min-width:1000px;overflow:auto;line-height:2\">")
                .append("<div style=\"margin:50px auto;width:70%;padding:20px 0\">")
                .append("<div style=\"border-bottom:1px solid #eee\">")
                .append("<h2 style=\"font-size:1.4em;color: #00466a;text-decoration:none;font-weight:600\">CECT Portal</h2>")
                .append("</div>")
                .append("<p style=\"font-size:1.1em\">Hi,</p>")
                .append("<p>Use the following OTP to complete your <b>Password Recovery</b> Procedure. OTP is valid for 5 minutes.</p>")
                .append("<div style=\"background: #00466a;margin: 20px auto;width: max-content;padding: 10px 20px;color: #fff;border-radius: 4px;font-size: 1.4em;\">")
                .append(otp)
                .append("</div>")
                .append("<p style=\"font-size:0.9em;\">Regards,<br />CDDA Director</p>")
                .append("</div>")
                .append("</div>")
                .append("</body>")
                .append("</html>");

        return emailContent.toString();
    }

}

