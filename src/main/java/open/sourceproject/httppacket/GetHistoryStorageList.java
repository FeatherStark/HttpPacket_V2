package open.sourceproject.httppacket;

import java.util.ArrayList;

public class GetHistoryStorageList {
    static ArrayList<String> HistoryRequestList = new ArrayList<String>();
    static ArrayList<String> HistoryResponseList = new ArrayList<String>();
    static int HistoryStatus = 1;

    public static void Initialization(){
        HistoryRequestList.add("请求体");
        HistoryResponseList.add("响应内容");
        String ContentText = "GET / HTTP/1.1 \n\rHost: example.com";
        HistoryRequestList.add(ContentText);
        HistoryResponseList.add("Response<html>");
        System.out.println(HistoryRequestList);
    }
}
