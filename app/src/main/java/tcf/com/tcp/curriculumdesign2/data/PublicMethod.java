package tcf.com.tcp.curriculumdesign2.data;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class PublicMethod {
    //Long型转年月日字符串型
    public static String getDate(Long date){
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(date);
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(c.getTime());
    }
}
