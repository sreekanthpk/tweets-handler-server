package org.sree.rest;

import java.util.concurrent.TimeUnit;

import org.apache.catalina.connector.Connector;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class CustomizationBean {
	
	@Value("${baseDir}")
	private String baseDir;

	@Bean
	public EmbeddedServletContainerFactory servletContainer() {
		TomcatEmbeddedServletContainerFactory factory = new TomcatEmbeddedServletContainerFactory();
		factory.setPort(9000);
		factory.setSessionTimeout(10, TimeUnit.MINUTES);
		Connector httpsConnector = new Connector();
		httpsConnector.setPort(9001);
		httpsConnector.setSecure(true);
		httpsConnector.setScheme("https");
		httpsConnector.setAttribute("keyAlias", "tomcat");
		httpsConnector.setAttribute("keystorePass", "password");
		httpsConnector
				.setAttribute(
						"keystoreFile",
						baseDir+"/restful-server-standalone/src/main/resources/keystore/keystore.jks");
		httpsConnector.setAttribute("clientAuth", "false");
		httpsConnector.setAttribute("sslProtocol", "TLS");
		httpsConnector.setAttribute("SSLEnabled", true);
		factory.addAdditionalTomcatConnectors(httpsConnector);
		return factory;
	}
}