package tcf.com.tcp.curriculumdesign2.data;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import tcf.com.tcp.curriculumdesign2.server.model.LoginUser;
import tcf.com.tcp.curriculumdesign2.server.model.Privacy;
import tcf.com.tcp.curriculumdesign2.server.model.User;

public class CommonStaticData {
    public static LoginUser loginUser=new LoginUser("","");
    public static User authUser = new User();
    public static Privacy privacy = new Privacy();
    public static String today;
    public static List<String> sevenDays;
    public static List<String> sevenWeekDay;
}
