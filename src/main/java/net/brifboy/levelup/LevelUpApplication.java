package net.brifboy.levelup;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LevelUpApplication {
	 @Autowired
	private LevelUPConfiguration levelup;

	private static Logger logger = LoggerFactory.getLogger(LevelUpApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(LevelUpApplication.class, args);







	}

}
