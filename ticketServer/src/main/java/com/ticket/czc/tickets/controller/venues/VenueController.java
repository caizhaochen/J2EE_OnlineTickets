package com.ticket.czc.tickets.controller.venues;

import com.ticket.czc.tickets.common.Constant;
import com.ticket.czc.tickets.factory.ServiceFactory;
import com.ticket.czc.tickets.model.VenuesEntity;
import com.ticket.czc.tickets.service.VenueManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLTransactionRollbackException;
import java.util.ArrayList;

@Controller

@RequestMapping("/venue")
public class VenueController {

    private VenueManageService venueManageService= ServiceFactory.getVenueManageService();

//    @Autowired
//    private VenueManageService venueManageService;
    @RequestMapping("/register/{venueInfo}")
    @ResponseBody
    public String register(@PathVariable("venueInfo")ArrayList<String> venueInfo){
        if(venueInfo.size()<7){
            return "fail";
        }
        String name=venueInfo.get(0);
        String phone=venueInfo.get(1);
        String location=venueInfo.get(2);
        String information=venueInfo.get(3);
        int line=Integer.valueOf(venueInfo.get(4));
        int row= Integer.valueOf(venueInfo.get(5));
        String password=venueInfo.get(6);

        VenuesEntity venue=new VenuesEntity();
        venue.setVenuename(name);
        venue.setVenuephone(phone);
        venue.setLocation(location);
        venue.setInformation(information);
        venue.setVenueline(line);
        venue.setVenuerow(row);
        venue.setVenuepassword(password);
        venue.setIschecked(0);
        venue.setCheckInfo("您的账号还未审核！");

        String result=venueManageService.registerVenue(venue);
        return result;
    }

    @RequestMapping("/login/{venueInfo}")
    @ResponseBody
    public String venueLogin(@PathVariable("venueInfo") ArrayList<String> venueInfo,HttpServletRequest request) throws IOException{
        if (venueInfo.size()<2){
            return "fail";
        }
        int id=Integer.valueOf(venueInfo.get(0));
        String password=venueInfo.get(1);
        String res=venueManageService.validateVenue(id,password);
        if (res.equals(Constant.USER_SUCCESS_LOGIN) || res.equals(Constant.USER_NOT_CHECK) ||res.equals(Constant.USER_NOT_PASS)) {
            HttpSession session = request.getSession();
            session.setAttribute("venueId", id);
        }
        return res;
    }

    @RequestMapping("/getVenueInfo")
    @ResponseBody
    public VenuesEntity getVenueInfo(HttpServletRequest request){
        HttpSession session=request.getSession();
//        if (String.valueOf(session.getAttribute("venueId"))==null||String.valueOf(session.getAttribute("venueId"))==""){
//            return null;
//        }
        String idString=String.valueOf(session.getAttribute("venueId"));

        int id=Integer.valueOf(idString);
        VenuesEntity venue=venueManageService.getVenueInfo(id);
        return venue;
    }

    @RequestMapping("/modify/{modifyInfo}")
    @ResponseBody
    public String modifyVenueInfo(@PathVariable("modifyInfo") ArrayList<String> modifyInfo){
        if(modifyInfo.size()<7){
            return "fail";
        }

        int id=Integer.valueOf(modifyInfo.get(0));
        String name=modifyInfo.get(1);
        String phone=modifyInfo.get(2);
        String location=modifyInfo.get(3);
        String info=modifyInfo.get(4);
        int line=Integer.valueOf(modifyInfo.get(5));
        int row=Integer.valueOf(modifyInfo.get(6));
        VenuesEntity venue=venueManageService.getVenueInfo(id);
        if (venue.getVenuename().equals(name)&&venue.getVenuephone().equals(phone)&&venue.getLocation().equals(location)&&venue.getInformation().equals(info)&&venue.getVenueline()==line&&venue.getVenuerow()==row){
            return "same";
        }

        venue.setVenuename(name);
        venue.setVenuephone(phone);
        venue.setLocation(location);
        venue.setInformation(info);
        venue.setVenueline(line);
        venue.setVenuerow(row);
        venue.setIschecked(0);
        venue.setCheckInfo("您的场馆还未审核！");
        VenuesEntity modifyRes=venueManageService.updateVenue(venue);
        if(modifyRes==null){
            return "fail";
        }

        return "success";
    }

    @RequestMapping("/modifyPass/{modifyInfo}")
    @ResponseBody
    public String modifyVenuePass(@PathVariable("modifyInfo") ArrayList<String> modifyInfo){
        VenuesEntity venue=venueManageService.getVenueInfo(Integer.valueOf(modifyInfo.get(0)));
        venue.setVenuepassword(modifyInfo.get(1));
        VenuesEntity venuesEntity=venueManageService.updateVenue(venue);
        if (venuesEntity==null){
            return "fail";
        }
        return "success";
    }

    @RequestMapping("/getUnCheckedVenues")
    @ResponseBody
    public ArrayList<VenuesEntity> getUnCheckedVenues(){
        return venueManageService.getUncheckedVenues();
    }

    @RequestMapping("/checkVenue/{checkInfo}")
    @ResponseBody
    public String checkVenue(@PathVariable("checkInfo")ArrayList<String> checkInfo){
        if(checkInfo==null||checkInfo.size()<3){
            return "fail";
        }
        int venueId=Integer.parseInt(checkInfo.get(0));
        int state=Integer.parseInt(checkInfo.get(1));
        String info=checkInfo.get(2);
        venueManageService.checkVenue(venueId,state,info);
        return "success";
    }
}
