
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
After cloning the repository you will have to create a environment variable file. This can be done With our favorite text editor.
```bash
 nvim .env
```
Write this into the file you just created. Make sure to CHANGE the environment variable to you need. 
```bash
DATASOURCE_USERNAME=you_database_username 
DATASOURCE_PASSWORD=the_users_password 
DISCORD_TOKEN=you_discord_token
```
Finally, you can now run the docker compose file and the bot should be up and running after the bot is built and packaged. 
```bash
 sudo docker-compose run -d levelup
```
Now you should have a working bot running in a docker container.
