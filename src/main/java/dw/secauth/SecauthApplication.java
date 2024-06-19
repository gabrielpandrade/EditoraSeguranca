package dw.secauth;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SecauthApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecauthApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/principal/api/**")
						.allowedOrigins("http://localhost:8080")
						.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS");
			}
		};
	}
}
