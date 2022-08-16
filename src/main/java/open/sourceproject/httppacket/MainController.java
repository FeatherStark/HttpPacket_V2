package open.sourceproject.httppacket;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.AnchorPane;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Properties;

import static open.sourceproject.httppacket.GetHistoryStorageList.*;

public class MainController {

    @FXML
    private Button FontSizeAddButton;

    @FXML
    private Button FontSizeLowButton;

    @FXML
    private Button GetCopyButton;

    @FXML
    private TextArea GetResponeData;

    @FXML
    private Button GetLastHistoryButton;

    @FXML
    private TabPane MainTablePanne;

    @FXML
    private Button GetRequireDataButton;

    @FXML
    public TextArea GetRequireData;

    @FXML
    private AnchorPane MainGETBar;

    @FXML
    private Button GetNextHistoryButton;

    @FXML
    private Button ClickResponseCopyButton;

    @FXML
    private Button StorageHtmlFIleButton;

    //初始化
    public void initialize(){
        GetHistoryStorageList.Initialization();
    }

    public void setGetRequireData(String str){
        GetRequireData.setText(str);
    }

    public  TextArea getGetRequireData() {
        return GetRequireData;
    }

    @FXML   //复制 请求 按钮，复制请求内容到剪切板
    void ClickButtonCopy(ActionEvent event) {
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();

        content.putString(GetRequireData.getText());  //复制请求内容到剪切板
        clipboard.setContent(content);
    }

    @FXML  // 发送请求按钮
    void ClickGetRequireDataButton(ActionEvent event) {
        System.out.println("ClickGetRequireDataButton");
        String RequireContent = GetRequireData.getText();
        if(CheckContentRepeat()){
            return;
        }else {
            HistoryRequestList.add(RequireContent);
            int HistoryStatus = 1;  // 历史记录加1
        }
        String[] strs = RequireContent.split("\n");
        String[] URIs = strs[0].split(" "); //获取首行中的第二个 uri
//        System.out.println(StringEscapeUtils.unescapeJava(strs[1]));

        try {
            String TargetUrl = "";
            for(int i = 1;i<strs.length;i++){
                String[] Headers = strs[i].split(": ");
                if(Headers[0].equals("Host")){
                    TargetUrl = "http://"+Headers[1]+URIs[1];  //提取Host+/uri中的URL
                }
            }
            URL url = new URL(TargetUrl);
            URLConnection connection = url.openConnection();
            for(int i = 1;i<strs.length;i++){
                String[] Headers = strs[i].split(": ");
                connection.setRequestProperty(Headers[0],Headers[1]);
            }
            try (InputStream in = connection.getInputStream()){
                InputStream buffer = new BufferedInputStream(in);
                Reader reader = new InputStreamReader(buffer);
                int c;
                String HtmlBody = "";
                while ((c = reader.read())!= -1) {
//                    System.out.print((char)c);
//                    System.out.println("===========");
                    HtmlBody += (char)c;
                    System.out.print((char)c);
                }
                GetResponeData.setText(HtmlBody);
                HistoryResponseList.add(HtmlBody);
            } catch (MalformedURLException e) {
                HistoryResponseList.add(e.toString());
            }
        } catch (IOException e) {
            HistoryResponseList.add(e.toString());
        }

    }
    @FXML  // 复制响应内容
    void ClickButtonResponseCopy(ActionEvent event) {
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();

        content.putString(GetResponeData.getText());  //复制请求内容到剪切板
        clipboard.setContent(content);
    }

    @FXML
    void ClickLastHistoryButton(ActionEvent event) {
        HistoryStatus -= 1;
        GetRequireData.setText(HistoryRequestList.get(HistoryStatus));
        GetResponeData.setText(HistoryResponseList.get(HistoryStatus));
    }

    @FXML
    void ClickNextHistoryButton(ActionEvent event) {
        if(HistoryRequestList.size()>HistoryStatus){
            GetRequireData.setText("");
            GetResponeData.setText("");
            HistoryStatus += 1;
            GetRequireData.setText(HistoryRequestList.get(HistoryStatus));
            GetResponeData.setText(HistoryResponseList.get(HistoryStatus));

        }
    }

    //检查当前请求是否与上条内容重复  重复则返回True 不重复则返回False
    public Boolean CheckContentRepeat(){
        if(GetResponeData.getText().equals(HistoryRequestList.get(HistoryStatus-1))){
            return Boolean.TRUE;
        }else {
            return Boolean.FALSE;
        }
    }

    // 调整字体大小 增加字号
    @FXML
    void ClickFontSizeAddButton(ActionEvent event) {
        String StyleConfig = "-fx-font-size: "+String.valueOf(getGetRequireData().getFont().getSize()+2);  //获取字体大小并增加2个字号
        getGetRequireData().setStyle(StyleConfig);
        GetResponeData.setStyle(StyleConfig);
    }

    //调整字体大小 减小字号
    @FXML
    void ClickFontSizeLowButton(ActionEvent event) {
        String StyleConfig = "-fx-font-size: "+String.valueOf(getGetRequireData().getFont().getSize()-2);
        GetRequireData.setStyle(StyleConfig);
        GetResponeData.setStyle(StyleConfig);
    }

    //按钮 文字换行
    @FXML
    void ClickChangeLineButton(ActionEvent event) {
        System.out.println(GetRequireData.isWrapText());
        if(GetRequireData.isWrapText()){
            GetRequireData.setWrapText(false);
            GetResponeData.setWrapText(false);
        }else {
            GetRequireData.setWrapText(true);
            GetResponeData.setWrapText(true);
        }
    }

    public void MainGETBarBind(Scene scene){
//        MainGETBar.layoutXProperty().bind(scene.widthProperty());
//        MainGETBar.layoutYProperty().bind(scene.heightProperty());
    }



    @FXML
    void ClickStorageHtmlFIleButton(ActionEvent event) throws IOException {
        System.out.println("OnClickStorageHtml");
        String WorkDir;
        System.out.println(GetResponeData.getText());
        if(GetResponeData.getText()!=null){
            Properties properties = System.getProperties();
            WorkDir = properties.getProperty("user.dir");
            System.out.println(GetHistoryStorageList.HistoryStatus);
            FileOutputStream fos = new FileOutputStream(WorkDir+"\\"+GetHistoryStorageList.HistoryStatus+".html",true);
            fos.write(GetResponeData.getText().getBytes());
            fos.close();
            System.out.println(WorkDir);
        }
    }

}
