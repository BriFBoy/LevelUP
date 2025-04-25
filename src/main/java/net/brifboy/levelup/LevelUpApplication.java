package net.brifboy.levelup;

import net.dv8tion.jda.api.JDA;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class LevelUpApplication {
	public static JDA jda;

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(LevelUpApplication.class, args);




	}

}
