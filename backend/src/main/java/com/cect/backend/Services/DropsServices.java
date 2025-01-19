package com.cect.backend.Services;
import com.cect.backend.Models.Details;
import com.cect.backend.Models.Drops;
import com.cect.backend.Repositories.DropsRepo;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DropsServices {
    @Autowired
    DropsRepo droprepo;
    @Autowired
    JavaMailSender mailSender;

    public void addDrops(Drops drop) {
        droprepo.save(drop);
        sendDropMail(drop, "YOUR REQUEST COURSE DROP");
    }

    public ResponseEntity<?> sendDropMail(Drops detail, String head) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            helper.setTo(detail.getEmail());
            helper.setSubject(head);
            helper.setText(buildEmailContent(detail, head), true);
            mailSender.send(mimeMessage);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private String buildEmailContent(Drops detail, String head) {
        StringBuilder emailContent = new StringBuilder();

        emailContent.append("<html>")
                .append("<body>")
                .append("<div style=\"font-family: Helvetica,Arial,sans-serif;min-width:1000px;overflow:auto;line-height:2\">")
                .append("<div style=\"margin:50px auto;width:70%;padding:20px 0\">")
                .append("<div style=\"border-bottom:1px solid #eee\">")
                .append("<a style=\"font-size:1.4em;color: #00466a;text-decoration:none;font-weight:600\">CECT Portal</a>")
                .append("</div>")
                .append("<p style=\"font-size:1.1em\">Hi,</p>");


        if ("YOUR REQUEST COURSE DROP".equals(head)) {
            emailContent.append("<p>Your request for course drop has been sent to your CA.</p>")
                    .append("<p>Your Course Details:</p>")
                    .append("<table style=\"border-collapse: collapse; width: 100%;\">")
                    .append("<tr>")
                    .append("<th style=\"border: 1px solid #ddd; padding: 6px;\">Course Title</th>")
                    .append("<td style=\"border: 1px solid #ddd; padding: 6px;\">").append(detail.getCourseName()).append("</td>")
                    .append("</tr>")
                    .append("<tr>")
                    .append("<th style=\"border: 1px solid #ddd; padding: 6px;\">Course Code</th>")
                    .append("<td style=\"border: 1px solid #ddd; padding: 6px;\">").append(detail.getCourseCode()).append("</td>")
                    .append("</tr>")
                    .append("<tr>")
                    .append("<th style=\"border: 1px solid #ddd; padding: 6px;\">Semester</th>")
                    .append("<td style=\"border: 1px solid #ddd; padding: 6px;\">").append(detail.getSemester()).append("</td>")
                    .append("</tr>")
                    .append("<tr>")
                    .append("<th style=\"border: 1px solid #ddd; padding: 6px;\">Credits</th>")
                    .append("<td style=\"border: 1px solid #ddd; padding: 6px;\">").append(detail.getCredits()).append("</td>")
                    .append("</tr>")
                    .append("</table>");
        }else if("REQUEST APPROVED SUCCESSFULLY".equals(head)){
            if(detail.getDir().equals("yes"))
                emailContent.append("<p>Your request for course drop has been verified by CDDA.</p>");
            else if(detail.getHod().equals("yes"))
                emailContent.append("<p>Your request for course drop has been verified by your HOD.</p>");
            else
                emailContent.append("<p>Your request for course drop has been verified by your CA.</p>");
        }else if("REQUEST REJECTED BY CA".equals(head)){
            emailContent.append("<p>Your request for course drop has been rejected by your CA.</p>");
        }
        else if("REQUEST REJECTED BY HOD".equals(head)){
            emailContent.append("<p>Your request for course drop has been rejected by your CA.</p>");
        }
        else if("REQUEST REJECTED BY CDDA".equals(head)){
            emailContent.append("<p>Your request for course drop has been rejected by your CA.</p>");
        }
        emailContent.append("<p style=\"font-size:0.9em;\">Regards,<br />CDDA Director</p>")
                .append("</div>")
                .append("</body>")
                .append("</html>");

        return emailContent.toString();
    }

    public List<Drops> getDropDetails(String email) {
        System.out.println("Drop");
        List<Drops> userDetails =  droprepo.findAll().stream().filter(one->email.equals(one.getEmail())).toList();
        return userDetails;
    }

    public List<Drops> getDropsCA(String year, String dept, String sec) {
        List<Drops> Data = droprepo.findAll().stream().filter(one->year.equals(one.getYear())).toList();
        Data = Data.stream().filter(one->dept.equals(one.getDept())).toList();
        Data = Data.stream().filter(one->sec.equals(one.getSec())).toList();
        return Data;
    }

    public List<Drops> getDropsHOD(String dept) {
        List<Drops> Data = droprepo.findAll().stream().filter(one->dept.equals(one.getDept())).toList();
        return Data.stream().filter(one->"yes".equals(one.getCa())).toList();
    }

    public List<Drops> getDropsbyRole(String role, String year, String dept, String sec) {
        if(role.equals("ca"))
        {
            List<Drops> Data = getDropsCA(year, dept, sec);
            return Data.stream().filter(one->"yes".equals(one.getCa())).toList();
        }
        else if(role.equals("hod"))
        {
            List<Drops> Data = getDropsHOD(dept);
            return Data.stream().filter(one->"yes".equals(one.getHod())).toList();
        }
        else
            return droprepo.findAll().stream().filter(one->"yes".equals(one.getDir())).toList();
    }

    public void deleteDropsbyRole(String role, String year, String dept, String sec) {
        List<Drops> Data = getDropsbyRole(role, year, dept, sec);
        for(Drops one: Data)
        {
            droprepo.delete(one);
        }
    }

    public List<Drops> getRejectedDropsbyRole(String role, String year, String dept, String sec) {
        if(role.equals("ca"))
        {
            List<Drops> Data = getDropsCA(year, dept, sec);
            return Data.stream().filter(one->"Rejected".equals(one.getStatus())).toList();
        }
        else if(role.equals("hod"))
        {
            List<Drops> Data = getDropsHOD(dept);
            return Data.stream().filter(one->"Rejected".equals(one.getStatus())).toList();
        }
        else
            return droprepo.findAll().stream().filter(one->"Rejected".equals(one.getStatus())).toList();
    }

    public void deleteRejectedDropsbyRole(String role, String year, String dept, String sec) {
        List<Drops> Data = getRejectedDropsbyRole(role, year, dept, sec);
        for(Drops one: Data)
        {
            droprepo.delete(one);
        }
    }

    public void approveDropsbyRole(String role, String id, String email) {
        Drops data = droprepo.findById(id).orElse(null);
        if(data !=null)
        {
            if(role.equals("ca"))
            {
                data.setCa("yes");
                data.setStatus("Pending");
                sendDropMail(data, "REQUEST APPROVED SUCCESSFULLY");
            }
            else if(role.equals("hod"))
            {
                data.setHod("yes");
                data.setStatus("Pending");
                sendDropMail(data, "REQUEST APPROVED SUCCESSFULLY");
            }
            else {
                data.setDir("yes");
                data.setStatus("Approved");
                sendDropMail(data, "REQUEST APPROVED SUCCESSFULLY");
            }
            droprepo.save(data);
        }
    }

    public void rejectDropsbyRole(String role, String id, String email) {
        Drops data = droprepo.findById(id).orElse(null);
        if(data !=null)
        {
            if(role.equals("ca"))
            {
                data.setCa("no");
                data.setStatus("Rejected");
                sendDropMail(data, "REQUEST REJECTED BY CA");
            }
            else if(role.equals("hod"))
            {
                data.setHod("no");
                data.setStatus("Rejected");
                sendDropMail(data, "REQUEST REJECTED BY HOD");
            }
            else {
                data.setDir("no");
                data.setStatus("Rejected");
                sendDropMail(data, "REQUEST REJECTED BY CDDA");
            }
            droprepo.save(data);
        }
    }

    public void approveAllHOD(List<String> ids) {
        Drops detail;
        for(String id: ids)
        {
            detail = droprepo.findById(id).orElse(null);
            if(detail!=null){
                detail.setHod("yes");
                droprepo.save(detail);
                sendDropMail(detail,"REQUEST APPROVED SUCCESSFULLY");
            }

        }
    }

    public void approveAll(List<String> ids) {
        Drops detail;
        for(String id: ids)
        {
            detail = droprepo.findById(id).orElse(null);
            if(detail!=null){
                detail.setDir("yes");
                detail.setStatus("Approved");
                droprepo.save(detail);
                sendDropMail(detail,"REQUEST APPROVED SUCCESSFULLY");
            }

        }
    }

    public List<Drops> getCDDARecords() {
        return droprepo.findAll().stream().filter(one->"yes".equals(one.getHod())).toList();
    }
}
