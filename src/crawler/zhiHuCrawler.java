package crawler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.SelectableChannel;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class zhiHuCrawler {

	public void getHtmlByURL(String urlAddress) {
		try {
			/*
			 * openStream()方法与制定的URL建立连接并返回InputStream类的对象，以从这一连接中读取数据；
			 * openStream()方法只能读取网络资源。
			 */
			URL url = new URL(urlAddress);
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

	public void getHtmlByURLConnection(String urlAddress) {
		try {
			/*
			 * openConnection()方法会创建一个URLConnection类的对象，
			 * 此对象在本地机和URL指定的远程节点建立一条HTTP协议的数据通道，可进行双向数据传输。
			 * 类URLConnection提供了很多设置和获取连接参数的方法，最常用到的是getInputStream()
			 * 和getOutputStream()方法。 openConnection()方法既能读取又能发送数据
			 */
			URL url = new URL(urlAddress);
			URLConnection URLconnection = url.openConnection();
			HttpURLConnection httpConnection = (HttpURLConnection) URLconnection;
			int responseCode = httpConnection.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				System.err.println("成功");
				InputStream in = httpConnection.getInputStream();
				InputStreamReader isr = new InputStreamReader(in);
				BufferedReader bufr = new BufferedReader(isr);
				String str;
				while ((str = bufr.readLine()) != null) {
					System.out.println(str);
				}
				bufr.close();
			} else {
				System.err.println("失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void getHtmlByJsoup(String address) {
		Document document;
		String userName;
		try {
			document = Jsoup.connect(address).get();
			Elements personItems = document.getElementsByAttributeValue("itemtype", "http://schema.org/Person");
			Elements personInfo = personItems.select("[itemprop=people] > meta");
			
			for (Element infoItem : personInfo) {
				String itemprop = infoItem.attr("itemprop");
				String content = infoItem.attr("content");
				if (itemprop.equals("url")) {
					System.out.printf("Get user %s info; \n", content.substring(content.lastIndexOf("/")+1));
				}
				System.out.println(itemprop+": "+content);
			}
			//System.out.println(personInfo.get(0));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		zhiHuCrawler zhihu = new zhiHuCrawler();
		String url = "https://www.zhihu.com/people/excited-vczh/answers";
//		zhihu.getHtmlByURL(url);
//		zhihu.getHtmlByURLConnection(url);
		zhihu.getHtmlByJsoup(url);
	}
}