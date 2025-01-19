package com.cect.backend.Controllers;
import com.cect.backend.Models.Details;
import com.cect.backend.Models.Drops;
import com.cect.backend.Services.DropsServices;
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
public class DropsController {

    @Autowired
    DropsServices service;

    @RequestMapping("/addnewdrop")
    public void addDrop(@RequestBody Drops drop)
    {
        drop.setCa("no");
        drop.setHod("no");
        drop.setDir("no");
        drop.setType("Drop");
        drop.setStatus("Pending");
        System.out.println(drop);
        service.addDrops(drop);
    }

    @RequestMapping("/getDropDetails")
    public List<Drops> getDropDetails(@RequestBody Map<String, String> req)
    {
        String email = req.get("email");
        return service.getDropDetails(email);
    }

    @RequestMapping("/dropsca")
    public List<Drops> getDropsCA(@RequestBody Map<String, String> req)
    {
        String year = req.get("year");
        String dept = req.get("dept");
        String sec = req.get("sec");
        return service.getDropsCA(year, dept, sec);
    }

    @RequestMapping("/dropshod")
    public List<Drops> getDetailsHOD(@RequestBody Map<String, String> req)
    {
        String dept = req.get("dept");
        return service.getDropsHOD(dept);
    }


    @RequestMapping("/detailsapproveddrop")
    public List<Drops> getDropsbyRole(@RequestBody Map<String, String> req)
    {
        String year = req.get("year");
        String dept = req.get("dept");
        String sec = req.get("sec");
        String role = req.get("role");
        return service.getDropsbyRole(role, year, dept, sec);
    }

    @RequestMapping("/detailsrejecteddrop")
    public List<Drops> getRejectedDropsbyRole(@RequestBody Map<String, String> req)
    {
        String year = req.get("year");
        String dept = req.get("dept");
        String sec = req.get("sec");
        String role = req.get("role");
        return service.getRejectedDropsbyRole(role, year, dept, sec);
    }

    @RequestMapping("/deletedrops")
    public void deleteDropsbyRole(@RequestBody Map<String, String> req)
    {
        String year = req.get("year");
        String dept = req.get("dept");
        String sec = req.get("sec");
        String role = req.get("role");
        service.deleteDropsbyRole(role, year, dept, sec);
    }

    @RequestMapping("/deleterejecteddrops")
    public void deleteRejectedDropsbyRole(@RequestBody Map<String, String> req)
    {
        String year = req.get("year");
        String dept = req.get("dept");
        String sec = req.get("sec");
        String role = req.get("role");
        service.deleteRejectedDropsbyRole(role, year, dept, sec);
    }

    @RequestMapping("/approvedrops")
    public void approveDropssByRole(@RequestBody Map<String, String> req)
    {
        String id = req.get("id");
        String email = req.get("email");
        String role = req.get("role");
        service.approveDropsbyRole(role, id, email);
    }

    @RequestMapping("/rejectdrops")
    public void rejectDropsByRole(@RequestBody Map<String, String> req)
    {
        String id = req.get("id");
        String email = req.get("email");
        String role = req.get("role");
        service.rejectDropsbyRole(role, id, email);
    }

    @RequestMapping("/approveallhoddrops")
    public void ApproveAllHOD(@RequestBody Map<String, List<String>> req)
    {
        List<String> ids = req.get("ids");
        System.out.println(ids);
        service.approveAllHOD(ids);
    }

    @RequestMapping("/dropsdir")
    public List<Drops> getCDDARecords()
    {
        return service.getCDDARecords();
    }

    @RequestMapping("/approvealldrops")
    public void ApproveAll(@RequestBody Map<String, List<String>> req)
    {
        List<String> ids = req.get("ids");
        System.out.println(ids);
        service.approveAll(ids);
    }
}
