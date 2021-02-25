package net.mhgoi.blog.components;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.ProtocolHandler;
import org.apache.coyote.http11.AbstractHttp11Protocol;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.stereotype.Component;

/**
 * @author 少年
 */
@Component
class AppTomcatConnectorCustomizer implements WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(AppTomcatConnectorCustomizer.class);

    @Override
    public void customize(ConfigurableServletWebServerFactory factory) {
        ((TomcatServletWebServerFactory) factory).setProtocol("org.apache.coyote.http11.Http11Nio2Protocol");
        ((TomcatServletWebServerFactory) factory).addConnectorCustomizers(new TomcatConnectorCustomizer() {
            @Override
            public void customize(Connector connector) {
                ProtocolHandler protocol = connector.getProtocolHandler();
                log.info("Tomcat({})  -- MaxConnection:{};MaxThreads:{};MinSpareThreads:{}",
                        protocol.getClass().getName(),
                        ((AbstractHttp11Protocol<?>) protocol).getMaxConnections(),
                        ((AbstractHttp11Protocol<?>) protocol).getMaxThreads(),
                        ((AbstractHttp11Protocol<?>) protocol).getMinSpareThreads());
            }
        });
    }
}
