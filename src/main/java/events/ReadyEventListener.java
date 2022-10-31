package events;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.EventListener;

import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ReadyEventListener implements EventListener {

    @Override
    public void onEvent(GenericEvent event) {
        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
        ZonedDateTime nextFirstLesson = now.withHour(8).withMinute(05).withSecond(0);
        if (now.compareTo(nextFirstLesson) > 0) {
            nextFirstLesson = nextFirstLesson.plusDays(1);
        }

        Duration durationUntilFirstLesson = Duration.between(now, nextFirstLesson);
        long initialDelayFirstLesson = durationUntilFirstLesson.getSeconds();
        ScheduledExecutorService schedulerFirstLesson = Executors.newScheduledThreadPool(1);
        schedulerFirstLesson.scheduleAtFixedRate(() -> {
            System.out.println(now);
//            String message = "Class is starting! Get to class!";
//            JDA jda = event.getJDA();
//            for (Guild guild : jda.getGuilds()) {
//                guild.getDefaultChannel().sendMessage(message).queue();
//      }

        },
        initialDelayFirstLesson,
        TimeUnit.DAYS.toSeconds(1),
        TimeUnit.SECONDS);
    }
}
