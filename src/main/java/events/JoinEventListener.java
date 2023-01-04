package events;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import support.DBConnect;
import support.Emoji;
import support.serects;

import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class JoinEventListener extends ListenerAdapter {
    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
        User user = event.getUser();


        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = null;


        try{
            Connection conn = DBConnect.getConnection();
            sql = "SELECT * FROM user_account WHERE uuid_dc = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, user.getId());
            rs = pstmt.executeQuery();

            TextChannel welcomeChannel = null;
            EmbedBuilder embed = new EmbedBuilder();
            int result = 0;
            embed.setFooter(serects.dcname()+" System by moonlight.one");
            embed.setColor(Color.green);


            switch (event.getGuild().getId()) {
                case "1026881171507921036" -> {
                    //일반용
                    if(rs.next()){
                        //계정 있음
                        sql = "UPDATE user_account SET player=1 WHERE uuid_dc = ?";
                        pstmt = conn.prepareStatement(sql);
                        pstmt.setString(1, user.getId());
                        result = pstmt.executeUpdate();
                    }else{
                        //계정 없음
                        sql = "INSERT INTO user_account(uuid_dc, player) VALUES (?, ?)";
                        pstmt = conn.prepareStatement(sql);
                        pstmt.setString(1, user.getId());
                        pstmt.setInt(2, 1);
                        result = pstmt.executeUpdate();
                    }
                }
                case "940506901911851048" -> {
                    //일반용
                    if(rs.next()){
                        //계정 있음
                        sql = "UPDATE user_account SET broadcast=1 WHERE uuid_dc = ?";
                        pstmt = conn.prepareStatement(sql);
                        pstmt.setString(1, user.getId());
                        result = pstmt.executeUpdate();
                    }else{
                        //계정 없음
                        sql = "INSERT INTO user_account(uuid_dc, broadcast) VALUES (?, ?)";
                        pstmt = conn.prepareStatement(sql);
                        pstmt.setString(1, user.getId());
                        pstmt.setInt(2, 1);
                        result = pstmt.executeUpdate();
                    }
                }
                default -> {
                    embed.setTitle("알 수 없는 서버");
                    embed.setDescription("등록되지 않은 서버입니다.");
                    embed.setColor(Color.RED);
                    embed.addField(event.getGuild().getName() + "서버의", "정보를 서버로 전송합니다.", false);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
