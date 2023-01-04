package Commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class testCommands extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        super.onSlashCommandInteraction(event);

        switch (event.getName()){
            case "테스트버튼":
                event.getChannel().sendMessage("button").setActionRow(sendButtons()).queue();
        }
    }

    private static List<Button> sendButtons(){
        List<Button> buttons = new ArrayList<>();
        buttons.add(Button.primary("Monday", "월요일"));
        buttons.add(Button.primary("Tuesday","화요일"));
        buttons.add(Button.primary("Wednesday","수요일"));
        buttons.add(Button.primary("Thursday","목요일"));
        buttons.add(Button.primary("Friday","금요일"));
        return buttons;
    }
}
