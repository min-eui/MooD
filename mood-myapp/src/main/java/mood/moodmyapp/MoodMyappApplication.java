package mood.moodmyapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing(modifyOnCreate = false)    //JPA Auditing 활성화, Entity 등록시 수정일은 null로 두고싶다면
@SpringBootApplication
public class MoodMyappApplication {

	public static void main(String[] args) {
		SpringApplication.run(MoodMyappApplication.class, args);
	}

}
