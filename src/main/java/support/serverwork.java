package support;

import com.jcraft.jsch.*;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class serverwork extends ListenerAdapter {

    String[] portconlist = {};
    List<String> portnow = new ArrayList<String>(Arrays.asList(portconlist));
    int randomportlist = 0;
    JSch jsch = new JSch();

    @Override
    public void onStringSelectInteraction(StringSelectInteractionEvent event) {

        if (event.getComponentId().equals("servermenu:minigame") || event.getComponentId().equals("servermenu:pvpgame")) {
//            event.reply("Your selection was processed").queue();

            List<String> selected = event.getValues();
            Session seesion = null;
            Channel channel = null;
            try {

                randomportlist = (int) (Math.random() * 10000) + 50000;
                while (portnow.contains(String.valueOf(randomportlist))) {
                    System.out.println(portnow);
                    randomportlist = (int) (Math.random() * 10000) + 50000;
                }
                portnow.add(String.valueOf(randomportlist));

                seesion = jsch.getSession(serects.sshID(), serects.sshhost(), serects.sshport());
                seesion.setPassword(serects.sshPW());

                Properties config = new Properties();
                config.put("StrictHostKeyChecking", "no");
                seesion.setConfig(config);

                seesion.connect();

                channel = seesion.openChannel("exec");
                ChannelExec channelExec = (ChannelExec) channel;

                channelExec.setCommand("sudo docker run -it -d -p " + serects.sshhost() + ":" + randomportlist + ":25565 " + selected.get(0) + ":" + selected.get(0));
                channelExec.connect();


            } catch (JSchException e) {
                throw new RuntimeException(e);
            } finally {
                if (channel != null) {
                    channel.disconnect();
                }
                if (seesion != null) {
                    seesion.disconnect();
                }
            }

            EmbedBuilder embed = new EmbedBuilder();
            embed.setFooter("D-Day System by moonlight.one");
            embed.addField("서버주소", serects.sshhost() + ":" + randomportlist, false);
            embed.addField("오픈 제한", "1/3", true);
            embed.addField("인원 제한", "7명", true);
            embed.addField("시간 제한", "2시간", true);
            embed.addField("분류", "방송용", true);
            
            embed.setColor(Color.yellow);

            event.editMessage("")
                    .setEmbeds(embed.build())
                    .setReplace(true)
                    .setActionRow(
                            Button.link("https://mc.moonlight.one/manual/" + selected.get(0), "게임설명 보기")
                    )
                    .queue();

        }
    }
}
