package org.charity;

import org.charity.service.AuthenticationService;
import org.charity.service.Service;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class StartServer {
    public static void StartServer() {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        Service service = context.getBean(Service.class);
        AuthenticationService authenticationService = context.getBean(AuthenticationService.class);
        CharityConcurrentServer server = new CharityConcurrentServer(service, authenticationService);
    }
}
