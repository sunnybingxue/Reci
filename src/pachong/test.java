package pachong;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.processor.PageProcessor;
public class test implements PageProcessor{

    private Site site = Site.me()
            .setUserAgent("Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
            .setRetryTimes(3)
            .setSleepTime(1000);
    @Override
    public Site getSite() {
        // TODO Auto-generated method stub
        return site;
    }
    
    public static void main(String[] args) {
        Spider.create(new test())
        .addUrl("https://baike.baidu.com/item/浜掕仈缃�+")//杩欓噷濉啓浣犵涓�娆¤鐖殑缃戝潃锛堝悗闈㈢洿鎺ヨ窡浣犺鏌ョ殑璇嶆眹鍚嶇О鎶婁簰鑱旂綉鏀逛簡灏辫锛�
        .addPipeline(new ConsolePipeline())
        .thread(15)
        .run();
        
}

    @Override
    public void process(Page page) {
    //杩欐浠ｇ爜閲嶅鑾峰彇 
        System.out.println(mySplitBaiDu(page));
        System.out.println("涓枃"+unicodeToString(mySplitBaiDu(page)));
    }
    
    //鐖彇鐧惧害瑙ｉ噴  涓簎nicode鏂囨湰
        public static String mySplitBaiDu(Page page)
        {
           String wordname=page.getUrl().toString().split("item/")[1];
            String basehtml=page.getJson().toString();
            String content =basehtml.split("bdText: \"")[1].split("@")[0];
            return content;
        }
    
        //unicode 杞腑鏂�
        public static String unicodeToString(String str) {
             
            Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");
            Matcher matcher = pattern.matcher(str);
            char ch;
            while (matcher.find()) {
                //group 6728
                String group = matcher.group(2);
                //ch:'鏈�' 26408
                ch = (char) Integer.parseInt(group, 16);
                //group1 \u6728
                String group1 = matcher.group(1);
                str = str.replace(group1, ch + "");
            }
            return str;
        }
    
}
