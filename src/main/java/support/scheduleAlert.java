package support;

import net.dv8tion.jda.api.EmbedBuilder;
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
                ZonedDateTime nowSeoul = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
                nowSeoul.minusDays(2);
                System.out.println(nowSeoul.getHour() + ":" + nowSeoul.getMinute() + ":" + nowSeoul.getSecond());
                if (nowSeoul.getHour() == 8 && nowSeoul.getMinute() == 51 && nowSeoul.getSecond() == 30){
//                if (nowSeoul.getDayOfWeek().toString() == "MONDAY" && nowSeoul.getHour() == 10 && nowSeoul.getMinute() == 00 && nowSeoul.getSecond() == 00){
                    EmbedBuilder embed = new EmbedBuilder();
                    embed.setColor(Color.white);
                    embed.setTitle("다음주 콘텐츠 참여 투표");
                    embed.setDescription(nowSeoul.plusDays(7).format(DateTimeFormatter.ofPattern("MM월 DD일")) + " ~ " +
                            nowSeoul.plusDays(14).format(DateTimeFormatter.ofPattern("MM월 DD일")) +
                            " 콘텐츠 참석 가능일자를 선택해주세요."
                    );
                    for (int i = 7; i < 14; i++){
                        embed.addField(nowSeoul.plusDays(i).format(DateTimeFormatter.ofPattern("MM/dd")), "Players: ", false);
                    }
                    
                }
            }
        }, 0, 1000);


    }
}
