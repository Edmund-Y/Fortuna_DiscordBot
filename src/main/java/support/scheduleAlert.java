package support;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import java.awt.*;
import java.lang.reflect.Array;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class scheduleAlert extends ListenerAdapter {
    EmbedBuilder embed = new EmbedBuilder();
    @Override
    public void onReady(ReadyEvent event) {
        super.onReady(event);

        Timer timer = new Timer();

        String[] weekname = {"월", "화", "수", "목", "금", "토", "일"};

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                TextChannel sendmsg = event.getJDA().getTextChannelById(serects.scheduleAlertCH());

                ZonedDateTime nowSeoul = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
//                System.out.println(nowSeoul.getHour() + ":" + nowSeoul.getMinute() + ":" + nowSeoul.getSecond());
//                nowSeoul.minusDays(2);
//                if (nowSeoul.getHour() == 18 && nowSeoul.getMinute() == 27 && nowSeoul.getSecond() == 40){
                if (nowSeoul.getDayOfWeek().toString() == "MONDAY" && nowSeoul.getHour() == 10 && nowSeoul.getMinute() == 00 && nowSeoul.getSecond() == 00){
                    embed.setColor(Color.white);
                    embed.setTitle("다음주 콘텐츠 참여 투표");
                    embed.setDescription(nowSeoul.plusDays(7).format(DateTimeFormatter.ofPattern("MM월 dd일")) + " ~ " +
                            nowSeoul.plusDays(14).format(DateTimeFormatter.ofPattern("MM월 dd일")) +
                            " 콘텐츠 참석 가능일자를 선택해주세요."
                    );
                    for (int i = 7; i < 14; i++){
                        embed.addField(weekname[i-6] + "요일", nowSeoul.plusDays(i).format(DateTimeFormatter.ofPattern("MM월 dd일")) + " 참여는 " + (i-6) + "이모티콘을 클릭해주세요.", false);
                    }
                    sendmsg.sendMessage("").setEmbeds(embed.build()).queue(message ->{
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

    @Override
    public void onMessageReactionAdd(MessageReactionAddEvent event) {
        super.onMessageReactionAdd(event);

        if (!event.getUser().isBot() && event.getChannel().getId().equals(serects.scheduleAlertCH())){
            System.out.println(event.getMessageId());
            switch (event.getReaction().getEmoji().getName()){
                case "1️⃣" -> {
                    System.out.println("1번");
                }
                case "2️⃣" -> {
                    System.out.println("2번");
                }
                case "3️⃣" -> {
                    System.out.println("3번");
                }
                case "4️⃣" -> {
                    System.out.println("4번");
                }
                case "5️⃣" -> {
                    System.out.println("5번");
                }
                case "6️⃣" -> {
                    System.out.println("6번");
                }
                case "7️⃣" -> {
                    System.out.println("7번");
                }
            }
        }
    }
}
