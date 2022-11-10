package events;
import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;


public class JoinEventListener extends ListenerAdapter {

    private static Dotenv config;

    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event) {

        config = Dotenv.configure().load();
        String dbuser = config.get("DBID");
        String dbpasswd = config.get("DBPW");

        Guild guild = event.getGuild();
        User user = event.getUser();
        JDA client = event.getJDA();

        if (user.isBot() == false){
            System.out.println("길드ID:" + guild + " 유저닉네임:" + user.getName() + "유저ID:" + user.getId() );

        }
    }
}
