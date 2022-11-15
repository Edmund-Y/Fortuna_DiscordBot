package Commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.interactions.modals.Modal;
import org.jetbrains.annotations.NotNull;

public class mc_accountCommands extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        super.onSlashCommandInteraction(event);

        switch (event.getName()) {
            case "계정등록" -> {
                TextInput name = TextInput.create("minecraftNickname", "마인크래프트 닉네임(영어)", TextInputStyle.SHORT)
                        .setPlaceholder("Enter someones name")
                        .setMinLength(1)
                        .setRequired(true)
                        .build();
                Modal modal = Modal.create("minecraftNickname", "마인크래프트 닉네임을 입력하세요.")
                        .addActionRows(ActionRow.of(name))
                        .build();
                event.replyModal(modal).queue();
            }
            case "계정삭제" -> {
                event.deferReply().queue();
                event.getHook().sendMessage(event.getGuild().toString()).setEphemeral(true).queue();
            }
        }
    }
}
