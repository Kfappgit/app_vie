package DataseBase_MSql;

import java.sql.Connection;

public class Databaser_MSql {

    private static  String ip =" ";
    private static  String port=" ";
    private static  String Class=" ";
    private static  String database=" ";
    private static  String username=" ";
    private static  String password=" ";
    private static  String url="jdbc:jtds:sqlserver://"+ip+":"+port+"/"+database;

    private Connection connection = null;

}
