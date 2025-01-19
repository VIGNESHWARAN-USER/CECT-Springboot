package com.cect.backend.Controllers;
import com.cect.backend.Models.Details;
import com.cect.backend.Models.Drops;
import com.cect.backend.Services.DetailsServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class DetailsController {

    @Autowired
    DetailsServices service;

    @RequestMapping("/addapproval")
    public void addApproval(@RequestBody Details details)
    {
        details.setStatus("Pending");
        details.setType("Approval");
        details.setCa("no");
        details.setHod("no");
        details.setDir("no");
        details.setCourseLink("Not Set");
        details.setMark("Not Issued");
        details.setGrade("Not Issued");
        System.out.println(details);
        service.addApproval(details);
    }

    @RequestMapping("/markupdation")
    public void updateApproval(@RequestBody Details details)
    {
        details.setStatus("Mark Verification");
        details.setCa("no");
        details.setDir("yes");
        details.setHod("yes");
        System.out.println(details);
        service.updateApproval(details);
    }

    @RequestMapping("/getDetailsUpdate")
    public List<Details> getUpdatedDetails(@RequestBody Map<String, String> req)
    {
        String email = req.get("email");
        System.out.println(email);
        return service.getUpdatedDetails(email);
    }

    @RequestMapping("/getDetails")
    public List<Details> getDetails(@RequestBody Map<String, String> req)
    {
        String email = req.get("email");
        System.out.println(email);
        return service.getDetails(email);
    }

    @RequestMapping("/detailsca")
    public List<Details> getDetailsCA(@RequestBody Map<String, String> req)
    {
        String year = req.get("year");
        String dept = req.get("dept");
        String sec = req.get("sec");
        return service.getDetailsCA(year, dept, sec);
    }

    @RequestMapping("/detailshod")
    public List<Details> getDetailsHOD(@RequestBody Map<String, String> req)
    {
        String dept = req.get("dept");
        return service.getDetailsHOD(dept);
    }

    @RequestMapping("/detailsapproved")
    public List<Details> getDetailsbyRole(@RequestBody Map<String, String> req)
    {
        String year = req.get("year");
        String dept = req.get("dept");
        String sec = req.get("sec");
        String role = req.get("role");
        return service.getDetailsbyRole(role, year, dept, sec);
    }

    @RequestMapping("/deletedetails")
    public void deleteDetailsbyRole(@RequestBody Map<String, String> req)
    {
        String year = req.get("year");
        String dept = req.get("dept");
        String sec = req.get("sec");
        String role = req.get("role");
        service.deleteDetailsbyRole(role, year, dept, sec);
    }

    @RequestMapping("/detailsrejected")
    public List<Details> getRejectedDetailsbyRole(@RequestBody Map<String, String> req)
    {
        System.out.println("Hi");
        String year = req.get("year");
        String dept = req.get("dept");
        String sec = req.get("sec");
        String role = req.get("role");
        return service.getRejectedDetailsbyRole(role, year, dept, sec);
    }

    @RequestMapping("/deleterejecteddetails")
    public void deleteRejectedDetailsbyRole(@RequestBody Map<String, String> req)
    {
        String year = req.get("year");
        String dept = req.get("dept");
        String sec = req.get("sec");
        String role = req.get("role");
        service.deleteRejectedDetailsbyRole(role, year, dept, sec);
    }

    @RequestMapping("/approvedetails")
    public void approveDetailsByRole(@RequestBody Map<String, String> req)
    {
        String id = req.get("id");
        String email = req.get("email");
        String role = req.get("role");
        service.approveDetailsbyRole(role, id, email);
    }

    @RequestMapping("/rejectdetails")
    public void rejectDetailsByRole(@RequestBody Map<String, String> req)
    {
        String id = req.get("id");
        String email = req.get("email");
        String role = req.get("role");
        service.rejectDetailsbyRole(role, id, email);
    }

    @RequestMapping("/approvemark")
    public void approveDetailsMark(@RequestBody Map<String, String> req)
    {
        String id = req.get("id");
        String email = req.get("email");
        service.approveDetailsMark(id, email);
    }

    @RequestMapping("/rejectmark")
    public void rejectDetailsMark(@RequestBody Map<String, String> req)
    {
        String id = req.get("id");
        String email = req.get("email");
        service.rejectDetailsMark(id, email);
    }

    @RequestMapping("/getupdatedcourses")
    public List<Details> getUpdatedDetailsCA(@RequestBody Map<String, String> req)
    {
        String year = req.get("year");
        String dept = req.get("dept");
        String sec = req.get("sec");
        return service.getUpdatedDetailsCA(year, dept, sec);
    }

    @RequestMapping("/approveallhod")
    public void ApproveAllHOD(@RequestBody Map<String, List<String>> req)
    {
        List<String> ids = req.get("ids");
        System.out.println(ids);
        service.approveAllHOD(ids);
    }

    @RequestMapping("/detailsdir")
    public List<Details> getCDDARecords()
    {
        return service.getCDDARecords();
    }

    @RequestMapping("/approveall")
    public void ApproveAll(@RequestBody Map<String, List<String>> req)
    {
        List<String> ids = req.get("ids");
        System.out.println(ids);
        service.approveAll(ids);
    }

}
