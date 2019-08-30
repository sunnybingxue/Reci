package bijiao;
import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.tokenizer.StandardTokenizer;

import Bean.reci;
import Dao.chaxunreci;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import java.lang.reflect.Array;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MySimHash {
    private String tokens; //�ַ���
    private BigInteger strSimHash;//�ַ�����hashֵ
    private int hashbits = 64; // �ִʺ��hash��;


    public MySimHash(String tokens) {
        this.tokens = tokens;
        this.strSimHash = this.simHash();
    }

    private MySimHash(String tokens, int hashbits) {
        this.tokens = tokens;
        this.hashbits = hashbits;
        this.strSimHash = this.simHash();
    }


    /**
     * ���html��ǩ
     * @param content
     * @return
     */
    private String cleanResume(String content) {
        // ������ΪHTML,�������˵����е�HTML��tag
        content = Jsoup.clean(content, Whitelist.none());
        content = StringUtils.lowerCase(content);
        String[] strings = {" ", "\n", "\r", "\t", "\\r", "\\n", "\\t", "&nbsp;"};
        for (String s : strings) {
            content = content.replaceAll(s, "");
        }
        return content;
    }


    /**
     * ����Ƕ������ַ�������hash����
     * @return
     */
    private BigInteger simHash() {

        tokens = cleanResume(tokens); // cleanResume ɾ��һЩ�����ַ�

        int[] v = new int[this.hashbits];

        List<Term> termList = StandardTokenizer.segment(this.tokens); // ���ַ������зִ�

        //�Էִʵ�һЩ���⴦�� : ����: ���ݴ������Ȩ�� , ���˵������� , ���˳�Ƶ�ʻ��;
        Map<String, Integer> weightOfNature = new HashMap<String, Integer>(); // ���Ե�Ȩ��
        weightOfNature.put("n", 2); //�����ʵ�Ȩ����2;
        Map<String, String> stopNatures = new HashMap<String, String>();//ͣ�õĴ��� ��һЩ������֮���;
        stopNatures.put("w", ""); //
        int overCount = 5; //�趨��Ƶ�ʻ�Ľ��� ;
        Map<String, Integer> wordCount = new HashMap<String, Integer>();

        for (Term term : termList) {
            String word = term.word; //�ִ��ַ���

            String nature = term.nature.toString(); // �ִ�����;
            //  ���˳�Ƶ��
            if (wordCount.containsKey(word)) {
                int count = wordCount.get(word);
                if (count > overCount) {
                    continue;
                }
                wordCount.put(word, count + 1);
            } else {
                wordCount.put(word, 1);
            }

            // ����ͣ�ô���
            if (stopNatures.containsKey(nature)) {
                continue;
            }

            // 2����ÿһ���ִ�hashΪһ��̶����ȵ�����.���� 64bit ��һ������.
            BigInteger t = this.hash(word);
            for (int i = 0; i < this.hashbits; i++) {
                BigInteger bitmask = new BigInteger("1").shiftLeft(i);
                // 3������һ������Ϊ64����������(����Ҫ����64λ������ָ��,Ҳ��������������),
                // ��ÿһ���ִ�hash������н����ж�,�����1000...1,��ô����ĵ�һλ��ĩβһλ��1,
                // �м��62λ��һ,Ҳ����˵,��1��1,��0��1.һֱ�������еķִ�hash����ȫ���ж����.
                int weight = 1;  //���Ȩ��
                if (weightOfNature.containsKey(nature)) {
                    weight = weightOfNature.get(nature);
                }
                if (t.and(bitmask).signum() != 0) {
                    // �����Ǽ��������ĵ�������������������
                    v[i] += weight;
                } else {
                    v[i] -= weight;
                }
            }
        }
        BigInteger fingerprint = new BigInteger("0");
        for (int i = 0; i < this.hashbits; i++) {
            if (v[i] >= 0) {
                fingerprint = fingerprint.add(new BigInteger("1").shiftLeft(i));
            }
        }
        return fingerprint;
    }


    /**
     * �Ե����ķִʽ���hash����;
     * @param source
     * @return
     */
    private BigInteger hash(String source) {
        if (source == null || source.length() == 0) {
            return new BigInteger("0");
        } else {
            /**
             * ��sourece �ĳ��ȹ��̣��ᵼ��hash�㷨ʧЧ�������Ҫ�Թ��̵Ĵʲ���
             */
            while (source.length() < 3) {
                source = source + source.charAt(0);
            }
            char[] sourceArray = source.toCharArray();
            BigInteger x = BigInteger.valueOf(((long) sourceArray[0]) << 7);
            BigInteger m = new BigInteger("1000003");
            BigInteger mask = new BigInteger("2").pow(this.hashbits).subtract(new BigInteger("1"));
            for (char item : sourceArray) {
                BigInteger temp = BigInteger.valueOf((long) item);
                x = x.multiply(m).xor(temp).and(mask);
            }
            x = x.xor(new BigInteger(String.valueOf(source.length())));
            if (x.equals(new BigInteger("-1"))) {
                x = new BigInteger("-2");
            }
            return x;
        }
    }

    /**
     * ���㺣������,��������ԽС˵��Խ����;
     * @param other
     * @return
     */
    private int hammingDistance(MySimHash other) {
        BigInteger m = new BigInteger("1").shiftLeft(this.hashbits).subtract(
                new BigInteger("1"));
        BigInteger x = this.strSimHash.xor(other.strSimHash).and(m);
        int tot = 0;
        while (x.signum() != 0) {
            tot += 1;
            x = x.and(x.subtract(new BigInteger("1")));
        }
        return tot;
    }


    public double getSemblance(MySimHash s2 ){
        double i = (double) this.hammingDistance(s2);
        return 1 - i/this.hashbits ;
    }

    public static void main(String[] args) throws SQLException {
    	ArrayList<reci> list=chaxunreci.chachong();
    	String jieshi[]=new String[100];
    	String bdwk[]=new String[100];
    	String geshi="��|��";
    	MySimHash hashreal = null;
    	MySimHash hashreal2 = null;
  //  	String chong="";
    	Double xiansidu=1.0;
    	for(reci are :list)
    	{
    		System.out.println(are.getName());
    		//System.out.println(are.getJieshi());
    		System.out.println(are.getBdwk());
    		jieshi=are.getJieshi().split(geshi);
    		//System.out.println(are.getJieshi().split(geshi)[0]);
    		bdwk=are.getBdwk().split(geshi);
    		for(int i=0;i<jieshi.length;i++)
    		{
    		    hashreal = new MySimHash(jieshi[i], 64);
    			for(int j=0;j<bdwk.length;j++)
    			{
    				hashreal2 = new MySimHash(bdwk[j], 64);
    				xiansidu=hashreal.getSemblance(hashreal2);
    				if (xiansidu>0.68) {
    	           		jieshi[i]=jieshi[i].replace(jieshi[i], "<font color=\"red\">"+jieshi[i]+"</font>");
    	           		break;
    	   			}
    			}
    		}
        	hashreal= new MySimHash(are.getJieshi(), 64);
        	hashreal2 = new MySimHash(are.getBdwk(), 64);
//        	chong=Arrays.toString(jieshi);
//        	chong=chong.replaceAll("]", "");
//        	chong=chong.replace("[", "");
//        	System.out.println(chong);   
        	System.out.println(Arrays.toString(jieshi).replaceAll("]", "").replace("[", ""));
        	chaxunreci.charucf(are.getName(),Arrays.toString(jieshi).replaceAll("]", "").replace("[", ""),hashreal.getSemblance(hashreal2)+"");
    	}
//    	String list[]= new String[2];
//    	String a="1,2 3,4 5";
//    	String b=",|\\s+";
//    	list[0]=a;
//    	list[1]=b;
//    	System.out.println(a.split(b)[2]);
//    	System.out.println(Arrays.toString(list));
    }
}

