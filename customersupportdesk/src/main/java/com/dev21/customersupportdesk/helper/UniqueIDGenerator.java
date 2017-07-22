package com.dev21.customersupportdesk.helper;

/**
 * Created by Prajwal on 17/07/17.
 */

public class UniqueIDGenerator {

    public static String getUniqueID() {
        Long tsLong = System.currentTimeMillis() / 1000;
        return tsLong.toString();
    }
}
