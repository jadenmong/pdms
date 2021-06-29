package com.len.util;

import java.util.UUID;

public class IDUtil {
    public static  String getID(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }


}
