package com.example.ahmed.adminkoratickets.models;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ahmed on 4/16/2018.
 */

public class ticketInfoModel {

    String user_id,match_id,id,ticket_class;


    public ticketInfoModel(String user_id, String match_id,String ticket_class) {
        this.user_id = user_id;
        this.match_id = match_id;
        this.ticket_class = ticket_class;


    }

//    public ticketInfoModel(String user_id, String match_id, String id) {
//        this.user_id = user_id;
//        this.match_id = match_id;
//        this.id = id;
//    }

    public static Map<String, Object> ConvertObjectToMap(Object obj) throws
            IllegalAccessException,
            IllegalArgumentException,
            InvocationTargetException {

        Method[] methods = obj.getClass().getMethods();
        Map<String, Object> map = new HashMap<String, Object>();
        for (Method m : methods) {
            if (m.getName().startsWith("get") && !m.getName().startsWith("getClass")) {
                Object value = (Object) m.invoke(obj);
                map.put(m.getName().substring(3), (Object) value);
            }
        }
        return map;
    }
    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getMatch_id() {
        return match_id;
    }

    public void setMatch_id(String match_id) {
        this.match_id = match_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        return !super.equals(obj);
    }

    public int hashCode() {
        return getId().hashCode();
    }
}
