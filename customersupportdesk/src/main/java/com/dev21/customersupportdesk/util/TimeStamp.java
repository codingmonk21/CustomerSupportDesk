package com.dev21.customersupportdesk.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Prajwal on 05/04/17.
 */

public class TimeStamp {

    public static String getCurrentTimeStamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm MMM dd, yyyy");
        return sdf.format(new Date());
    }
}
