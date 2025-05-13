package net.brifboy.levelup.service.slashcommands;

import net.brifboy.levelup.LevelUPConfiguration;
import net.brifboy.levelup.model.Guild;
import net.brifboy.levelup.model.LevelRole;
import net.brifboy.levelup.repo.LevelRoleRepository;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.EntitySelectInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.selections.EntitySelectMenu;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.interactions.modals.Modal;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SetLevelRoles extends ListenerAdapter {

    @Autowired
    private LevelRoleRepository levelRoleRepository;

    private static final String ROLE_MENU_ID = "rolemenu";
    private static final String ROLE_MODAL_ID = "role_modal";
    private static final String MODAL_TEXTINPUT_ID = "role_text_input";

    private static List<Role> roleList;

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if (event.getName().equals(LevelUPConfiguration.SETLEVELROLES_ID)) {
            event.reply("Select the roles. IN ORDER").addActionRow(getRoleMenu()).setEphemeral(true).queue();
        }
    }

    @Override
    public void onEntitySelectInteraction(@NotNull EntitySelectInteractionEvent event) {
        if (event.getComponentId().equals(ROLE_MENU_ID)) {
            event.replyModal(getModal()).queue();
            roleList = event.getMentions().getRoles();
        }
    }

    @Override
    public void onModalInteraction(@NotNull ModalInteractionEvent event) {
        if (event.getModalId().equals(ROLE_MODAL_ID)) {
            String[] levellist = event.getValue(MODAL_TEXTINPUT_ID).getAsString().split(" ");

            for (int i = 0; i < roleList.size(); i++) {
                addRoleToDB(roleList.get(i).getIdLong(), new Guild(roleList.get(i).getGuild().getIdLong(), roleList.get(i).getGuild().getName()),
                        Integer.parseInt(levellist[i]), roleList.get(i).getName());
            }
            event.reply("All done").setEphemeral(true).queue();



        }
    }

    private void addRoleToDB(long id, Guild guild, int atlevel, String name) {
        levelRoleRepository.save(new LevelRole(id, guild, atlevel, name));
    }

    private Modal getModal() {
        TextInput textInput = TextInput.create(MODAL_TEXTINPUT_ID, "Levels. Use space to separate",
                TextInputStyle.PARAGRAPH).build();

        return Modal.create(ROLE_MODAL_ID, "What level go get roles. IN ORDER")
                .addActionRow(textInput)
                .build();
    }
    private EntitySelectMenu getRoleMenu() {
        return EntitySelectMenu.create(ROLE_MENU_ID, EntitySelectMenu.SelectTarget.ROLE)
                .setMaxValues(25).build();
    }
}
