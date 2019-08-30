package Pdf;
import java.io.FileOutputStream;
import java.util.ArrayList;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;

import Bean.reci;
import Dao.chaxunreci; 

public class PdfCreater {
	public static void main(String[] args) throws  Exception {
		new PdfCreater().init();
	}
	public static String FILE_DIR ="D:/eclipse/daima/arecia/WebContent/";
	public void init() throws Exception, DocumentException
	{
		
		//Step 1—Create a Document.  
		Document document = new Document();  
		//Step 2—Get a PdfWriter instance.  
		FileOutputStream outputStream = new FileOutputStream( FILE_DIR+ "createSamplePDF.pdf");
		PdfWriter.getInstance(document,outputStream );  
		//Step 3—Open the Document.  
		document.open();  
		//Step 4—Add content.  
		ArrayList<reci> list=chaxunreci.zsc();//获取数据库内容
		BaseFont bfChinese = BaseFont.createFont( "STSongStd-Light" ,  "UniGB-UCS2-H" ,  false );
		Font fontChinese =  new  Font(bfChinese  ,  12 , Font.NORMAL,BaseColor.RED);
		Font fontChinese2 =  new  Font(bfChinese  ,  12 , Font.NORMAL);
		//document.add(new Paragraph("你好",fontChinese));  
		Paragraph ppp=null;
		Paragraph ppq=null;
		Chunk chuk=null;
		String name="";
		String jieshi[]=new String[100];
		LineSeparator line = new LineSeparator(2f,100,BaseColor.RED,Element.ALIGN_CENTER,-5f);
		ppq=new Paragraph("信息热词检测结果", fontChinese2);
		ppq.setAlignment(Element.ALIGN_CENTER);
		document.add(ppq);
		ppq=new Paragraph("检测范围 ◎ 百度百科", fontChinese2);
		ppq.setAlignment(Element.ALIGN_CENTER);
		document.add(ppq);
		ppq=new Paragraph("检测结论", fontChinese2);
		ppq.setAlignment(Element.ALIGN_CENTER);
		document.add(ppq);
		double nums=0;
		for(reci a :list)
		{
			if(a.getNum()!=null)
			{
				nums+=Double.parseDouble(a.getNum());
			}
		}
		nums=nums/2.54;
		ppq=new Paragraph("重复率："+nums+"%", fontChinese2);
		ppq.setAlignment(Element.ALIGN_CENTER);
		document.add(ppq);
		document.add(line);
		for(reci a:list)
		{
			ppp=new Paragraph();
			name=a.getName();
			ppq=new Paragraph(name,fontChinese2);
			ppq.setAlignment(Element.ALIGN_CENTER);
			document.add(ppq);
			jieshi=a.getJieshi().split(",");
			for(int i=0;i<jieshi.length;i++)
			{
				if(jieshi[i].indexOf("<font color=\"red\">")>-1)
				{
					jieshi[i]=jieshi[i].replace("<font color=\"red\">", "");
					jieshi[i]=jieshi[i].replace("</font>", "");
					chuk=new Chunk(jieshi[i], fontChinese);
				}
				else
				{
					chuk=new Chunk(jieshi[i], fontChinese2);
				}
				ppp.add(chuk);
			}
			document.add(ppp);
		}
		//Step 5—Close the Document.  

		document.close(); 
	}
}
