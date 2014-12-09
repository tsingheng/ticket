package net.shangtech.ticket.util;

import javax.net.ssl.SSLContext;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpUtils {
	
	private static final Logger logger = LoggerFactory.getLogger(HttpUtils.class);
	
	public static String get(HttpClient client, String url){
		HttpGet get = new HttpGet(url);
		HttpResponse response;
		try {
			response = client.execute(get);
			HttpEntity entity = response.getEntity();
			if(entity != null){
				String result = IOUtils.toString(entity.getContent(), "UTF-8");
				logger.info(result);
				return result;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static CloseableHttpClient getClient() {
		try {
			SSLContext context = new SSLContextBuilder().loadTrustMaterial(null, (chain, authType) -> {return true;}).build();
			SSLConnectionSocketFactory  sslsf = new SSLConnectionSocketFactory(context);
			return HttpClients.custom().setSSLSocketFactory(sslsf).build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return HttpClients.createDefault();
	}
}
