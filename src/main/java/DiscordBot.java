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
                        new MessageEventListener(), new JoinEventListener(), new mc_accountModalListener(),
                        new birthdayCommands(), new mc_accountCommands()
                )
                .build();

//        jda.getGuildById("").upsertCommand("fart","hard").queue(); 특정 길드에서만 작동

        jda.upsertCommand("계정등록", "마인크래프트 닉네임 입력하여 계정을 등록합니다.").setGuildOnly(true).queue();
//        jda.upsertCommand("계정삭제","등록한 마인크래프트 계정을 삭제합니다.").setGuildOnly(true).queue();
//        jda.upsertCommand("생일등록","자신의 생일을 등록합니다.")
//            .addOptions(
//                    new OptionData(OptionType.INTEGER, "월", "태어난 월을 입력해주세요.", true).setRequiredRange(1, 12),
//                    new OptionData(OptionType.INTEGER, "일", "태어난 일을 입력해주세요.", true).setRequiredRange(1, 31)
//            )
//            .setGuildOnly(true)
//            .queue();
//        jda.upsertCommand("생일삭제","등록한 생일을 삭제합니다.").setGuildOnly(true).queue();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                ZonedDateTime nowSeoul = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
                nowSeoul.minusDays(1);
                if (nowSeoul.getDayOfWeek().toString() == "MONDAY" && nowSeoul.getHour() == 22 && nowSeoul.getMinute() == 18 && nowSeoul.getSecond() == 00) {
//                if (nowSeoul.getDayOfWeek().toString() == "MONDAY" && nowSeoul.getHour() == 10 && nowSeoul.getMinute() == 00 && nowSeoul.getSecond() == 00) {
                    System.out.println("실행");
                    EmbedBuilder embed = new EmbedBuilder();
                    embed.setFooter(serects.dcname()+" System by moonlight.one");
                    embed.setColor(Color.white);
                    embed.setTitle("주간 콘텐츠 참석자 조사");
                    embed.setDescription(
                            nowSeoul.plusDays(7).format(DateTimeFormatter.ofPattern("MM")) + "월 " + nowSeoul.plusDays(7).format(DateTimeFormatter.ofPattern("dd")) + "일 ~ " +
                            nowSeoul.plusDays(14).format(DateTimeFormatter.ofPattern("MM")) + "월 " + nowSeoul.plusDays(14).format(DateTimeFormatter.ofPattern("dd")) + "일 " +
                            "동안 플레이 가능한 일자를 선택해주세요."
                    );
                    embed.addField("test", String.valueOf(Emoji.emoji_eheck), false);
                    for (int i = 7; i < 14; i++) {
                        System.out.println(nowSeoul.plusDays(i).format(DateTimeFormatter.ofPattern("MM/dd")));
                    }
                    jda.getGuildById("1026881171507921036").getTextChannelById("1029890264556388382").sendMessage("1").queue();
                }
            }
        };
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleAtFixedRate(runnable, 0, 1, TimeUnit.SECONDS);
    }

}