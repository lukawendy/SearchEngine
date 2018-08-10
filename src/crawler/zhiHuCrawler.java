package crawler;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class zhiHuCrawler {
	
	public void getHtmlByURL() {
		try {
			URL url = new URL("http://www.baidu.com");
			InputStream in = url.openStream();
			InputStreamReader isr = new InputStreamReader(in);
			BufferedReader bufr = new BufferedReader(isr);
			String str;
			while ((str = bufr.readLine()) != null) {
				System.out.println(str);
			}
			bufr.close();
			isr.close();
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		zhiHuCrawler zhihu = new zhiHuCrawler();
		zhihu.getHtmlByURL();
	}
}