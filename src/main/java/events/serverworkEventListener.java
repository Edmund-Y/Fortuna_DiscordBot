package events;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.components.ItemComponent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.selections.StringSelectMenu;

import java.awt.*;
import java.util.List;


public class serverworkEventListener extends ListenerAdapter {

    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {
        EmbedBuilder embed = new EmbedBuilder();
        embed.setFooter("D-Day System by moonlight.one");
        embed.setImage("https://cdn.discordapp.com/attachments/1029890264556388382/1058967722299502683/-_-001.png");
        embed.setColor(Color.blue);

        StringSelectMenu menu = null;
        switch (event.getComponentId()) {
            case "minigameserverlist" -> {
                menu = StringSelectMenu.create("servermenu:minigame")
                        .setPlaceholder("서버를 선택하세요.")
                        .addOption("투명 숨바꼭질", "thas")
                        .addOption("꼬리잡기", "test")
                        .build();
            }
            case "pvpserverlist" -> {
                menu = StringSelectMenu.create("servermenu:pvpgame")
                        .setPlaceholder("서버를 선택하세요.")
                        .addOption("신들의 전쟁", "p1")
                        .addOption("능력자", "p2")
                        .build();
            }
        }
        List<ItemComponent> actionRow = event.getMessage().getActionRows().get(0).getComponents();
        event.editMessage("")
                .setEmbeds(embed.build())
                .setActionRow(menu)
                .queue();
    }
}
