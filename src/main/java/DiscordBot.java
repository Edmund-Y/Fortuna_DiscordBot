import net.dv8tion.jda.api.EmbedBuilder;
import support.*;
import Commands.*;
import events.*;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import javax.security.auth.login.LoginException;
import java.awt.*;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class DiscordBot {

    public static void main(String[] args) throws LoginException {//psvm
        String TOKEN = serects.discordtoken();
        String STATS = "D-Day 2.0 준비!";

        JDABuilder jdaBuilder = JDABuilder.createDefault(TOKEN);
        JDA jda = jdaBuilder
                .setStatus(OnlineStatus.DO_NOT_DISTURB)
                .setActivity(Activity.playing(STATS))
                .setChunkingFilter(ChunkingFilter.ALL)
                .setMemberCachePolicy(MemberCachePolicy.ALL)
                .enableIntents(GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_PRESENCES)
                .addEventListeners(
                        new MessageEventListener(), new JoinEventListener(), new mc_accountModalListener(), new serverworkEventListener(),
                        new mc_accountCommands(), new testCommands(), new serverworkCommands(),
                        new scheduleAlert(), new serverwork()
                )
                .build();

//        jda.getGuildById("").upsertCommand("fart","hard").queue(); 특정 길드에서만 작동
        jda.upsertCommand("계정등록", "마인크래프트 닉네임 입력하여 계정을 등록합니다.").setGuildOnly(true).queue();
        jda.upsertCommand("서버열기", "서버 열기 명령어").setGuildOnly(true).queue();
    }
}