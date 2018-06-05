package com.ticket.czc.tickets.controller.shows;

import com.ticket.czc.tickets.factory.ServiceFactory;
import com.ticket.czc.tickets.model.SeatsEntity;
import com.ticket.czc.tickets.model.ShowsEntity;
import com.ticket.czc.tickets.service.SeatsManageService;
import com.ticket.czc.tickets.service.ShowManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.misc.BASE64Decoder;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.plaf.PanelUI;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Controller

@RequestMapping("/show")
public class ShowController {

    @Value("${web.upload-path}")
    private String path;

    private ShowManageService showManageService= ServiceFactory.getShowManageService();
    private SeatsManageService seatsManageService=ServiceFactory.getSeatsManageService();
//    @Autowired
//    private ShowManageService showManageService;
//    @Autowired
//    private SeatsManageService seatsManageService;

    @RequestMapping("/register/{showInfo}")
    @ResponseBody
    public ArrayList<String> showRegister(@PathVariable("showInfo")List showInfo, HttpServletRequest request) throws IOException{
        HttpSession session=request.getSession(false);
        ArrayList<String> result=new ArrayList<>();

        String idString=String.valueOf(session.getAttribute("venueId"));
        if (idString==null||idString==""){
            result.add("fail");
            result.add("登录过期，重新登录");
//            return "noVenue";
            return result;
        }
        int venueId=Integer.valueOf(idString);
        String showName=(String)showInfo.get(0);
        String showType=(String)showInfo.get(1);
        Timestamp showTime=Timestamp.valueOf((String) showInfo.get(2));
        String showDes=(String) showInfo.get(3);
        int showLine=Integer.valueOf((String) showInfo.get(4));
        int showRow=Integer.valueOf((String) showInfo.get(5));
        ShowsEntity show=new ShowsEntity();
        show.setShowname(showName);
        show.setShowtype(showType);
        show.setShowtime(showTime);
        show.setShowdescribe(showDes);
        show.setVenueid(venueId);
        show.setShowline(showLine);
        show.setShowrow(showRow);
        int restSeats=showLine*showRow;
        show.setRestseats(restSeats);
        show.setPosttime(new Timestamp(System.currentTimeMillis()));
        show.setVenueget(0.0);
        show.setAdminget(0.0);

        int showId=showManageService.registerShow(show);

        ArrayList<SeatsEntity> seats=new ArrayList<>();
        int priceNum=Integer.parseInt((String)showInfo.get(6));
        for (int i=1;i<=priceNum;i++){
            String priceName=(String)showInfo.get(6+(i-1)*4+1);
            int seatLeft=Integer.parseInt((String)showInfo.get(6+(i-1)*4+2));
            int seatRight=Integer.parseInt((String)showInfo.get(6+(i-1)*4+3));
            double seatPrice=Double.parseDouble((String)showInfo.get(6+(i-1)*4+4));
            for(int seatNum=seatLeft;seatNum<=seatRight;seatNum++){
                SeatsEntity seat=new SeatsEntity();
                seat.setShowid(showId);
                seat.setVenueid(venueId);
                seat.setSeatnum(seatNum);
                seat.setSeatprice(seatPrice);
                seat.setSeatisbooked(0);
                seats.add(seat);
                System.out.println("----size"+seats.size());
            }
        }


        System.out.println(seats.size());
        Boolean saveSeats=seatsManageService.saveSeats(seats);
        String addSeatRes= String.valueOf(saveSeats);
        result.add("success");
        result.add(String.valueOf(showId));
        return result;
//        System.out.println(showInfo);
    }

    @RequestMapping("/uploadPicture")
    @ResponseBody
    public ArrayList<String> doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        ArrayList<String> result=new ArrayList<>();
        String image = req.getParameter("image");
        String showId=req.getParameter("showId");

        // 只允许jpg
        String header ="data:image/jpeg;base64,";

        if(image.indexOf(header) != 0){
//            resp.getWriter().print(wrapJSON(false));
//            return null;
            result.add("fail");
            result.add("图片格式不正确，请确认是jpg/jepg");
            return result;
        }
        // 去掉头部
        image = image.substring(header.length());
        // 写入磁盘
        boolean success = false;
        BASE64Decoder decoder = new BASE64Decoder();
        try{

            byte[] decodedBytes = decoder.decodeBuffer(image);
//            File path = new File(ResourceUtils.getURL("classpath:").getPath());
//            String imgFilePath =path+"/images/"+showId+".jpg";
//            org.springframework.core.io.Resource resource = resourceLoader.getResource("classpath:");
//            System.out.println(resource.getFile().getPath());
            String imgFilePath =path+showId+".jpg/";
//            String imgFilePath ="images/"+showId+".jpg/";
//            String imgFilePath2 ="E:/testPictures/"+showId+".jpg";
            FileOutputStream out = new FileOutputStream(ResourceUtils.getFile(imgFilePath));
//            FileOutputStream out = new FileOutputStream(imgFilePath);
//            FileOutputStream out2 = new FileOutputStream(imgFilePath2);
            out.write(decodedBytes);
//            out2.write(decodedBytes);
            out.close();
//            out2.close();
            success = true;
        }catch(Exception e){
            success = false;
            e.printStackTrace();
        }
        result.add(String.valueOf(success));
        return result;
//        resp.getWriter().print(wrapJSON(success));
    }

    @RequestMapping("/getAvailableShows")
    @ResponseBody
    public ArrayList<ShowsEntity> getAvailableShows(){
        return showManageService.getAvailableShows();
    }

    @RequestMapping("/getCurrentShow")
    @ResponseBody
    public ShowsEntity getCurrentShow(HttpServletRequest request)throws IOException{
        HttpSession session=request.getSession();
        int showId=(Integer)session.getAttribute("showId");
        ShowsEntity show=showManageService.getShowById(showId);
        return show;
    }

    @RequestMapping("/getFutureShowByVenue")
    @ResponseBody
    public ArrayList<ShowsEntity> getFutureShowByVenue(HttpServletRequest request)throws IOException{
        HttpSession session=request.getSession();
        int venueId=(int)session.getAttribute("venueId");
        return showManageService.getFutureShowByVenue(venueId);
    }

    @RequestMapping("/getAllShow")
    @ResponseBody
    public ArrayList<ShowsEntity> getAllShow(HttpServletRequest request)throws IOException{
        HttpSession session=request.getSession();
        int venueId=(int) session.getAttribute("venueId");
        return showManageService.getAllShowByVenue(venueId);
    }
}
