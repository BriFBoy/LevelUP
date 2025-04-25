package net.brifboy.levelup;

import net.brifboy.levelup.service.slashcommands.Commands;
import net.brifboy.levelup.service.slashcommands.StatCommand;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LevelUpApplication {

	public static void main(String[] args) {
		SpringApplication.run(LevelUpApplication.class, args);


	}

}
