package com.cect.backend.Services;

import com.cect.backend.Models.Details;
import com.cect.backend.Models.Drops;
import com.cect.backend.Repositories.DetailsRepo;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class DetailsServices {

    @Autowired
    DetailsRepo detailrepo;
    @Autowired
    JavaMailSender mailSender;

    public ResponseEntity<?> addApproval(Details details) {
        detailrepo.save(details);
        return sendMail(details, "YOUR REQUEST");
    }

    public ResponseEntity<?> sendMail(Details detail, String head) {
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

    private String buildEmailContent(Details detail, String head) {
        StringBuilder emailContent = new StringBuilder();

        emailContent.append("<html>")
                .append("<body>")
                .append("<div style=\"font-family: Helvetica,Arial,sans-serif;min-width:1000px;overflow:auto;line-height:2\">")
                .append("<div style=\"margin:50px auto;width:70%;padding:20px 0\">")
                .append("<div style=\"border-bottom:1px solid #eee\">")
                .append("<a style=\"font-size:1.4em;color: #00466a;text-decoration:none;font-weight:600\">CECT Portal</a>")
                .append("</div>")
                .append("<p style=\"font-size:1.1em\">Hi,</p>");


        if ("YOUR REQUEST".equals(head)) {
            emailContent.append("<p>Your request for course approval has been sent to your CA.</p>")
                    .append("<p>Your Course Details:</p>")
                    .append("<table style=\"border-collapse: collapse; width: 100%;\">")
                    .append("<tr>")
                    .append("<th style=\"border: 1px solid #ddd; padding: 6px;\">Course Title</th>")
                    .append("<td style=\"border: 1px solid #ddd; padding: 6px;\">").append(detail.getCourseTitle()).append("</td>")
                    .append("</tr>")
                    .append("<tr>")
                    .append("<th style=\"border: 1px solid #ddd; padding: 6px;\">Course Code</th>")
                    .append("<td style=\"border: 1px solid #ddd; padding: 6px;\">").append(detail.getCourseCode()).append("</td>")
                    .append("</tr>")
                    .append("<tr>")
                    .append("<th style=\"border: 1px solid #ddd; padding: 6px;\">Duration</th>")
                    .append("<td style=\"border: 1px solid #ddd; padding: 6px;\">").append(detail.getDuration()).append("</td>")
                    .append("</tr>")
                    .append("<tr>")
                    .append("<th style=\"border: 1px solid #ddd; padding: 6px;\">Credits</th>")
                    .append("<td style=\"border: 1px solid #ddd; padding: 6px;\">").append(detail.getCredits()).append("</td>")
                    .append("</tr>")
                    .append("</table>");
        }
        else if ("MARK VERIFICATION RERQUEST".equals(head)) {
            emailContent.append("<p>Your request for mark verification has been sent to your CA.</p>")
                    .append("<p>Your Course Details:</p>")
                    .append("<table style=\"border-collapse: collapse; width: 100%;\">")
                    .append("<tr>")
                    .append("<th style=\"border: 1px solid #ddd; padding: 6px;\">Course Title</th>")
                    .append("<td style=\"border: 1px solid #ddd; padding: 6px;\">").append(detail.getCourseTitle()).append("</td>")
                    .append("</tr>")
                    .append("<tr>")
                    .append("<th style=\"border: 1px solid #ddd; padding: 6px;\">Course Code</th>")
                    .append("<td style=\"border: 1px solid #ddd; padding: 6px;\">").append(detail.getCourseCode()).append("</td>")
                    .append("</tr>")
                    .append("<tr>")
                    .append("<th style=\"border: 1px solid #ddd; padding: 6px;\">Duration</th>")
                    .append("<td style=\"border: 1px solid #ddd; padding: 6px;\">").append(detail.getDuration()).append("</td>")
                    .append("</tr>")
                    .append("<tr>")
                    .append("<th style=\"border: 1px solid #ddd; padding: 6px;\">Credits</th>")
                    .append("<td style=\"border: 1px solid #ddd; padding: 6px;\">").append(detail.getCredits()).append("</td>")
                    .append("</tr>")
                    .append("<tr>")
                    .append("<th style=\"border: 1px solid #ddd; padding: 6px;\">Credits</th>")
                    .append("<td style=\"border: 1px solid #ddd; padding: 6px;\">").append(detail.getMark()).append("</td>")
                    .append("</tr>")
                    .append("<tr>")
                    .append("<th style=\"border: 1px solid #ddd; padding: 6px;\">Credits</th>")
                    .append("<td style=\"border: 1px solid #ddd; padding: 6px;\">").append(detail.getGrade()).append("</td>")
                    .append("</tr>")
                    .append("<tr>")
                    .append("<th style=\"border: 1px solid #ddd; padding: 6px;\">Credits</th>")
                    .append("<td style=\"border: 1px solid #ddd; padding: 6px;\">").append(detail.getCourseLink()).append("</td>")
                    .append("</tr>")
                    .append("</table>");
        }else if("REQUEST APPROVED SUCCESSFULLY".equals(head)){
            if(detail.getDir().equals("yes"))
                emailContent.append("<p>Your request for course approval has been verified by CDDA.</p>");
            else if(detail.getHod().equals("yes"))
                emailContent.append("<p>Your request for course approval has been verified by your HOD.</p>");
            else
                emailContent.append("<p>Your request for course approval has been verified by your CA.</p>");
        }else if("REQUEST REJECTED BY CA".equals(head)){
                emailContent.append("<p>Your request for course approval has been rejected by your CA.</p>");
        }
        else if("REQUEST REJECTED BY HOD".equals(head)){
                emailContent.append("<p>Your request for course approval has been rejected by your CA.</p>");
        }
        else if("REQUEST REJECTED BY CDDA".equals(head)){
                emailContent.append("<p>Your request for course approval has been rejected by your CA.</p>");
        }
        else if("MARK VERIFICATION COMPLETED".equals(head)){
            emailContent.append("<p>Your request for mark verification has been verified by your CA.</p>");
        }else if("MARK VERIFICATION REJECTED".equals(head)){
            emailContent.append("<p>Your request for mark verification has been rejected.</p>");
        }

        emailContent.append("<p style=\"font-size:0.9em;\">Regards,<br />CDDA Director</p>")
                .append("</div>")
                .append("</body>")
                .append("</html>");

        return emailContent.toString();
    }


    public List<Details> getUpdatedDetails(String email) {
        List<Details> userDetails =  detailrepo.findAll().stream().filter(one->email.equals(one.getEmail())).toList();
        userDetails =  userDetails.stream().filter(one->"Approved".equals(one.getStatus())).toList();
        return userDetails;
    }

    public List<Details> getDetails(String email) {
        System.out.println(email);
        List<Details> userDetails =  detailrepo.findAll().stream().filter(one->email.equals(one.getEmail())).toList();
        return userDetails;
    }

    public void updateApproval(Details details) {
        detailrepo.save(details);
        sendMail(details, "MARK VERIFICATION RERQUEST");
    }

    public List<Details> getDetailsCA(String year, String dept, String sec) {
        List<Details> Data = detailrepo.findAll().stream().filter(one->year.equals(one.getYear())).toList();
        Data = Data.stream().filter(one->dept.equals(one.getDept())).toList();
        Data = Data.stream().filter(one->sec.equals(one.getSec())).toList();
        return Data;
    }

    public List<Details> getDetailsHOD(String dept) {
        List<Details> Data = detailrepo.findAll().stream().filter(one->dept.equals(one.getDept())).toList();
        return Data.stream().filter(one->"yes".equals(one.getCa())).toList();
    }


    public List<Details> getDetailsbyRole(String role, String year, String dept, String sec) {

        if(role.equals("ca"))
        {
            List<Details> Data = getDetailsCA(year, dept, sec);
            return Data.stream().filter(one->"yes".equals(one.getCa())).toList();
        }
        else if(role.equals("hod"))
        {
            List<Details> Data = getDetailsHOD(dept);
            return Data.stream().filter(one->"yes".equals(one.getHod())).toList();
        }
        else
            return detailrepo.findAll().stream().filter(one->"yes".equals(one.getDir())).toList();
    }

    public void deleteDetailsbyRole(String role, String year, String dept, String sec) {
        List<Details> Data = getDetailsbyRole(role, year, dept, sec);
        for(Details one: Data)
        {
            detailrepo.delete(one);
        }
    }

    public List<Details> getRejectedDetailsbyRole(String role, String year, String dept, String sec) {
        if(role.equals("ca"))
        {
            List<Details> Data = getDetailsCA(year, dept, sec);
            return Data.stream().filter(one->"Rejected".equals(one.getStatus())).toList();
        }
        else if(role.equals("hod"))
        {
            List<Details> Data = getDetailsHOD(dept);
            return Data.stream().filter(one->"Rejected".equals(one.getStatus())).toList();
        }
        else
            return detailrepo.findAll().stream().filter(one->"Rejected".equals(one.getStatus())).toList();
    }

    public void deleteRejectedDetailsbyRole(String role, String year, String dept, String sec) {
        List<Details> Data = getRejectedDetailsbyRole(role, year, dept, sec);
        for(Details one: Data)
        {
            detailrepo.delete(one);
        }
    }

    public void approveDetailsbyRole(String role, String id, String email) {
        Details data = detailrepo.findById(id).orElse(null);
        if(data !=null)
        {
            if(role.equals("ca"))
            {
                data.setCa("yes");
                data.setStatus("Pending");
                sendMail(data, "REQUEST APPROVED SUCCESSFULLY");
            }
            else if(role.equals("hod"))
            {
                data.setHod("yes");
                data.setStatus("Pending");
                sendMail(data, "REQUEST APPROVED SUCCESSFULLY");
            }
            else {
                data.setDir("yes");
                data.setStatus("Approved");
                sendMail(data, "REQUEST APPROVED SUCCESSFULLY");
            }
            detailrepo.save(data);
        }
    }

    public void rejectDetailsbyRole(String role, String id, String email) {
        Details data = detailrepo.findById(id).orElse(null);
        if(data !=null)
        {
            if(role.equals("ca"))
            {
                data.setCa("no");
                data.setStatus("Rejected");
                sendMail(data, "REQUEST REJECTED BY CA");
            }
            else if(role.equals("hod"))
            {
                data.setHod("no");
                data.setStatus("Rejected");
                sendMail(data, "REQUEST REJECTED BY HOD");
            }
            else {
                data.setDir("no");
                data.setStatus("Rejected");
                sendMail(data, "REQUEST REJECTED BY CDDA");
            }
            detailrepo.save(data);
        }
    }

    public void approveDetailsMark(String id, String email) {
        Details data = detailrepo.findById(id).orElse(null);
        if(data!=null) {
            data.setStatus("Approved");
            data.setCa("yes");
            detailrepo.save(data);
            sendMail(data, "MARK VERIFICATION COMPLETED");
        }

    }

    public void rejectDetailsMark(String id, String email) {
        Details data = detailrepo.findById(id).orElse(null);
        if(data!=null) {
            data.setStatus("Rejected");
            detailrepo.save(data);
            sendMail(data, "MARK VERIFICATION REJECTED");
        }

    }

    public List<Details> getUpdatedDetailsCA(String year, String dept, String sec) {
        List<Details> Data = detailrepo.findAll().stream().filter(one->year.equals(one.getYear())).toList();
        Data = Data.stream().filter(one->dept.equals(one.getDept())).toList();
        Data = Data.stream().filter(one->sec.equals(one.getSec())).toList();
        Data = Data.stream().filter(one->"Mark Verification".equals(one.getStatus())).toList();
        return Data;
    }

    public void approveAllHOD(List<String> ids) {
        Details detail;
        for(String id: ids)
        {
            detail = detailrepo.findById(id).orElse(null);
            if(detail!=null){
                detail.setHod("yes");
                detailrepo.save(detail);
                sendMail(detail,"REQUEST APPROVED SUCCESSFULLY");
            }

        }
    }

    public void approveAll(List<String> ids) {
        Details detail;
        for(String id: ids)
        {
            detail = detailrepo.findById(id).orElse(null);
            if(detail!=null){
                detail.setDir("yes");
                detail.setStatus("Approved");
                detailrepo.save(detail);
                sendMail(detail,"REQUEST APPROVED SUCCESSFULLY");
            }

        }
    }

    public List<Details> getCDDARecords() {
        List<Details> data = detailrepo.findAll().stream().filter(one->"yes".equals(one.getHod())).toList();
        data = data.stream()
                .filter(one -> !"Mark Verification".equals(one.getStatus()))
                .toList();
        return data;
    }
}



