package pachong;
import java.util.ArrayList;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import Dao.chaxunreci;
public class paqu {
	public static void main(String[] args) {	
		ArrayList<String> result=chaxunreci.chaxunname();
		for(int i=0;i<result.size();i++) {
			String s=result.get(i);
			System.out.println(s);
			try {
				String name=s;
				s=s.replace("什么是", "");
				s=s.replace("?", "");
				s=s.replace("？", "");
				s=s.replace(" ", "");
				s=s.replace("有哪些", "");
				if(s.indexOf('（')>0)
				{
					s=s.split("（")[0];
				}
				if(s.indexOf("的")>0)
				{
					s=s.split("的")[0];
				}
				Connection connection=Jsoup.connect("https://baike.baidu.com/item/"+s);
				connection.timeout(2000);
				Document document=connection.get();
				chaxunreci.charubd(name, document.getElementsByClass("para").first().text());
			}catch(Exception e){
				System.out.println(e.getMessage());
			}
		}
		
	}

}
