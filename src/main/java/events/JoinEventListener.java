package events;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class JoinEventListener extends ListenerAdapter {
    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
        Guild guild = event.getGuild();
        User user = event.getUser();
        JDA client = event.getJDA();

        if (user.isBot() == false){
            System.out.println("길드ID:" + guild + " 유저:" + user + "JDA:" +client );

        }
    }
}
