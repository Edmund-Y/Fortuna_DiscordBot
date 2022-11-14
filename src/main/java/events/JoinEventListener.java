package events;

import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import support.DBConnect;

import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class JoinEventListener extends ListenerAdapter {

    private static Dotenv config;
    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event) {

        config = Dotenv.configure().load();
        String dbuser = config.get("DBID");
        String dbpasswd = config.get("DBPW");
        String dcname = config.get("dcname");

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
            embed.setFooter(dcname+" System by moonlight.one");
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
                    if (result >= 1) {
                        embed.setTitle(user.getName() + "님 환영합니다!");
                        embed.setDescription("일반서버 계정등록에 완료하였습니다.");
                        embed.addField("마인크래프트 계정을 등록해주세요.", "/계정등록", false);
                        welcomeChannel = event.getJDA().getTextChannelById("1029890264556388382");
                        welcomeChannel.sendMessage("").setEmbeds(embed.build()).queue();
                    }else{
                        embed.setTitle(user.getName() + "님 등록에 실패했습니다.");
                        embed.setDescription("일반서버 계정등록에 실패하였습니다.");
                        embed.addField("관리자를 호출해주세요.", "DB 업데이트 오류", false);
                        welcomeChannel = event.getJDA().getTextChannelById("1029890264556388382");
                        welcomeChannel.sendMessage("").setEmbeds(embed.build()).queue();
                    }
                }
                case "875723042200911893" -> {
                    //방송용
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
