
# LevelUP

LevelUP is a Discord bot that adds levels to you Discord server.
## Adding to Guild
If you just want to add the bot to you Guild, you can simply paste this URL into your browser.

https://discord.com/oauth2/authorize?client_id=1365249526704771132
## Self Hosting the Bot

To deploy this project you will need to install git and docker.io. This guide is made for Debian Linux, but it may work on other debian based distribution.
To install the needed tools you can run

```bash
  sudo apt install git docker.io
```
Next you will need to clone the repository by running
```bash
 git clone https://github.com/BriFBoy/LevelUP
 cd LevelUP
```
After cloning the repository you can now just run the docker-compose file. Make sure to CHANGE the env variables to your need.
```bash
 sudo docker-compose run -d -e DATASOURCE_USERNAME=you_database_username -e DATASOURCE_PASSWORD=the_users_password -e DISCORD_TOKEN=you_discord_token levelup
```
Now you should have a working bot running in a docker container.

