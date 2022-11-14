import Commands.birthdayCommands;
import Commands.mc_accountCommands;
import events.JoinEventListener;
import events.MessageEventListener;
import events.ReadyEventListener;
import events.mc_accountModalListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import support.serects;

import javax.security.auth.login.LoginException;

public class DiscordBot {

    public static void main(String[] args) throws LoginException {//psvm
        String TOKEN = serects.discordtoken();
        String STATS = "D-Day 2.0 준비";

        JDABuilder jdaBuilder = JDABuilder.createDefault(TOKEN);
        JDA jda = jdaBuilder
                .setStatus(OnlineStatus.DO_NOT_DISTURB)
                .setActivity(Activity.playing(STATS))
                .setChunkingFilter(ChunkingFilter.ALL)
                .setMemberCachePolicy(MemberCachePolicy.ALL)
                .enableIntents(GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_PRESENCES)
                .addEventListeners(
                        new ReadyEventListener(), new MessageEventListener(), new JoinEventListener(), new mc_accountModalListener(),
                        new birthdayCommands(), new mc_accountCommands()
                )
                .build();

//        jda.getGuildById("").upsertCommand("fart","hard").queue(); 특정 길드에서만 작동

        jda.upsertCommand("계정등록","마인크래프트 닉네임 입력하여 계정을 등록합니다.").setGuildOnly(true).queue();
//        jda.upsertCommand("계정삭제","등록한 마인크래프트 계정을 삭제합니다.").setGuildOnly(true).queue();
//        jda.upsertCommand("생일등록","자신의 생일을 등록합니다.")
//            .addOptions(
//                    new OptionData(OptionType.INTEGER, "월", "태어난 월을 입력해주세요.", true).setRequiredRange(1, 12),
//                    new OptionData(OptionType.INTEGER, "일", "태어난 일을 입력해주세요.", true).setRequiredRange(1, 31)
//            )
//            .setGuildOnly(true)
//            .queue();
//        jda.upsertCommand("생일삭제","등록한 생일을 삭제합니다.").setGuildOnly(true).queue();
    }
}
