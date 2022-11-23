package support;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Timer;
import java.util.TimerTask;

public class scheduleAlert extends ListenerAdapter {
    @Override
    public void onReady(ReadyEvent event) {
        super.onReady(event);


        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                TextChannel sendmsg = event.getJDA().getTextChannelById(serects.scheduleAlertCH());

                ZonedDateTime nowSeoul = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
                System.out.println(nowSeoul.getHour() + ":" + nowSeoul.getMinute() + ":" + nowSeoul.getSecond());
                nowSeoul.minusDays(2);
                if (nowSeoul.getHour() == 13 && nowSeoul.getMinute() == 7 && nowSeoul.getSecond() == 40){
//                if (nowSeoul.getDayOfWeek().toString() == "MONDAY" && nowSeoul.getHour() == 10 && nowSeoul.getMinute() == 00 && nowSeoul.getSecond() == 00){
                    EmbedBuilder embed = new EmbedBuilder();
                    embed.setColor(Color.white);
                    embed.setTitle("다음주 콘텐츠 참여 투표");
                    embed.setDescription(nowSeoul.plusDays(7).format(DateTimeFormatter.ofPattern("MM월 dd일")) + " ~ " +
                            nowSeoul.plusDays(14).format(DateTimeFormatter.ofPattern("MM월 dd일")) +
                            " 콘텐츠 참석 가능일자를 선택해주세요."
                    );
                    for (int i = 7; i < 14; i++){
                        embed.addField(i + "번 " + nowSeoul.plusDays(i).format(DateTimeFormatter.ofPattern("MM/dd")), "참여자: 없음", false);
                    }
                    sendmsg.sendMessage("").setEmbeds(embed.build()).queue(message -> {
                        message.addReaction(Emoji.emoji_one).queue();
                        message.addReaction(Emoji.emoji_two).queue();
                        message.addReaction(Emoji.emoji_three).queue();
                        message.addReaction(Emoji.emoji_four).queue();
                        message.addReaction(Emoji.emoji_five).queue();
                        message.addReaction(Emoji.emoji_six).queue();
                        message.addReaction(Emoji.emoji_seven).queue();
                    });
                }
            }
        }, 0, 1000);
    }
}
