package com.archiving.archivingTool.Configuration;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.apache.http.ssl.SSLContexts;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.stereotype.*;

import javax.net.ssl.SSLContext;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

@Configuration
public class AppConfig {

//    @Bean
//    public RestTemplate restTemplate (){
////        SSLContext sslContext = TrustAllCertificates.createTrustAllContext();
////        SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(
////                sslContext,
////                NoopHostnameVerifier.INSTANCE
////        );
////
////        CloseableHttpClient httpClient = HttpClients.custom()
////                .setSSLSocketFactory(socketFactory)
////                .build();
////
////        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);
////        return new RestTemplate(factory);
//
//        /////////////////////////////
//        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
//        factory.setProxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("proxy-host", proxy-port)));
//        return new RestTemplate(factory);
//    }

    @Bean
    public RestTemplate restTemplate() throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        HttpComponentsClientHttpRequestFactory requestFactoryHttp = new HttpComponentsClientHttpRequestFactory();

        TrustStrategy acceptingTrustStrategy = (TrustStrategy) (cert, authType) -> true;        SSLContext sslContext = SSLContexts.custom()
                .loadTrustMaterial(null, acceptingTrustStrategy)
                .build();
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE);
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory> create()
                .register("https", sslsf)
                .register("http", new PlainConnectionSocketFactory())
                .build();

        BasicHttpClientConnectionManager connectionManager =
                new BasicHttpClientConnectionManager(socketFactoryRegistry);
        CloseableHttpClient httpClient = HttpClients.custom()
                .setConnectionManager(connectionManager)
                .build();
        requestFactoryHttp.setHttpClient((HttpClient) httpClient);
        return new RestTemplate(requestFactoryHttp);
    }

}
