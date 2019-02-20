package pl.piotrek.tenants.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
//Część konfiguracji pozostawiam wciaz pod spring bootem
//@EnableTransactionManagement
//@EnableJpaRepositories
@EnableJpaAuditing
public class PersistanceConfig {
}
