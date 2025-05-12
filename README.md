
# LevelUP

LevelUP is a Discord bot that adds levels to you Discord server.
## Adding to Guild
If you just want to add the bot to you Guild, you can simply paste this URL in your browser.

https://discord.com/oauth2/authorize?client_id=1365249526704771132
## Self Hosting the Bot

To deploy this project you will need to install git and jdk-21. It install the needed tools you can run

```bash
  sudo apt install git docker.io
  sudo apt-get install -y openjdk-21-jdk
```
Next you will need to clone the repository by running
```bash
 git clone https://github.com/BriFBoy/LevelUP
 cd LevelUP
```
After cloning the repository you will need to build to jar and than running the docker-compose file.

```bash
 ./mvnw package -Dmaven.test.skip
```
After building the project to jar, make sure to change the env variables to your need.
```bash
 sudo docker-compose -d -e DATASOURCE_URL=your_jdbc_URL -e DATASOURCE_USERNAME=you_database_username -e DATASOURCE_PASSWORD=the_users_password -e DISCORD_TOKEN=you_discord_token
```
Now you should have a working bot running in a docker container.


