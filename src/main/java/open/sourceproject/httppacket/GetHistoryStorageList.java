package open.sourceproject.httppacket;

import java.util.ArrayList;

public class GetHistoryStorageList {
    static ArrayList<String> HistoryRequestList = new ArrayList<String>();
    static ArrayList<String> HistoryResponseList = new ArrayList<String>();
    static int HistoryStatus = 1;

    public static void Initialization(){
        String ContentText = "GET / HTTP/1.1 \n\rHost: www.baidu.com";
        HistoryRequestList.add(ContentText);
        HistoryResponseList.add("<html>Response</html>");
        System.out.println(HistoryRequestList);
    }
}
