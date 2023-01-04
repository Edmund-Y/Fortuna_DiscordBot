package events;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.events.thread.member.ThreadMemberLeaveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LeaveEventListener extends ListenerAdapter {

    @Override
    public void onGuildMemberRemove(GuildMemberRemoveEvent event) {
        System.out.println(event);
        System.out.println(event);
    }

    @Override
    public void onGuildLeave(GuildLeaveEvent event) {
        System.out.println(event);
        System.out.println(event);
    }

    @Override
    public void onThreadMemberLeave(ThreadMemberLeaveEvent event) {
        System.out.println(event);
        System.out.println(event);
    }
}
