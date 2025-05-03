package griborga.tests;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class TestsApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestsApplication.class, args);
	}

}
