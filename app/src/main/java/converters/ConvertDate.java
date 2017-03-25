package converters;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Milica on 01-Mar-17.
 */
public class ConvertDate {


    /**
     The method takes long var and return the appropriate date in dd-MM-yyyy format
     @param dateLong date in long type
     @return the date return in specified format
     */
    public static String castDate(long dateLong){
        if (dateLong == 0)
            return null;
        Date date = new Date(dateLong);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String formatDate = sdf.format(date);
        return formatDate;
    }
}
