package com.siteactualites.config;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

/**
 * Publie le service SOAP.
 * Le WSDL est généré automatiquement par Spring-WS à partir de utilisateurs.xsd
 * et est accessible sur : http://localhost:8080/ws/utilisateurs.wsdl
 */
@EnableWs
@Configuration
public class WebServiceConfig extends WsConfigurerAdapter {

    @Bean
    public ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(ApplicationContext applicationContext) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean<>(servlet, "/ws/*");
    }

    // Le nom du bean ("utilisateurs") détermine l'URL finale : /ws/utilisateurs.wsdl
    @Bean(name = "utilisateurs")
    public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema utilisateursSchema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("UtilisateursPort");
        wsdl11Definition.setLocationUri("/ws");
        wsdl11Definition.setTargetNamespace("http://siteactualites.com/soap/utilisateurs");
        wsdl11Definition.setSchema(utilisateursSchema);
        return wsdl11Definition;
    }

    @Bean
    public XsdSchema utilisateursSchema() {
        return new SimpleXsdSchema(new ClassPathResource("utilisateurs.xsd"));
    }
}
