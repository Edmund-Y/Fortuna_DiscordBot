package Commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import org.jetbrains.annotations.NotNull;

public class birthdayCommands extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        switch (event.getName()) {
            case "생일등록" -> {
                event.deferReply().queue();
                OptionMapping month = event.getOption("월");
                OptionMapping day = event.getOption("일");
                event.getHook().sendMessage("당신의 월은" + month.getAsString()).setEphemeral(true).queue();
            }
            case "생일삭제" -> {
                event.deferReply().queue();
                event.getHook().sendMessage("등록된 생일을 삭제했습니다.").setEphemeral(true).queue();
            }
        }
    }
}
