package Commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.selections.SelectOption;
import net.dv8tion.jda.api.interactions.components.selections.SelectMenu;
import net.dv8tion.jda.api.interactions.components.selections.SelectMenuInteraction;
import net.dv8tion.jda.api.interactions.components.selections.StringSelectMenu;

import java.awt.*;

public class serverworkCommands extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if ("서버열기".equals(event.getName())) {
            EmbedBuilder embed = new EmbedBuilder();
            embed.setFooter("D-Day System by moonlight.one");
            embed.setImage("https://cdn.discordapp.com/attachments/1029890264556388382/1058966037997031476/-001.png");
            embed.setColor(Color.blue);
            event.reply("")
                    .addActionRow(
                            Button.primary("minigameserverlist", "MiniGame"),
                            Button.danger("pvpserverlist", "PVP")
                    )
                    .setEmbeds(embed.build())
                    .setEphemeral(true)
                    .queue();
        }
    }

}
