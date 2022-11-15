package events;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import support.DBConnect;
import support.Emoji;
import support.serects;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.regex.Pattern;

public class mc_accountModalListener extends ListenerAdapter {

    Pattern pattern = Pattern.compile("^[0-9a-zA-Z_-]*$");

    public String mcplayeruuid(String nickname){
        
        URL url = null;
        String uuid = null;
        JSONObject jsonObj = null;

        try {
            url = new URL("https://api.mojang.com/users/profiles/minecraft/"+nickname);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json"); // Content-Type 지정
            conn.setDoOutput(true); // 출력 가능 상태로 변경
            conn.connect();
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            String line = "";
            while((line = br.readLine()) != null) {
                sb.append(line);
            }
            conn.disconnect();
            jsonObj = (JSONObject) new JSONParser().parse(sb.toString());
            return jsonObj.get("id").toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception e){
            return "Not account";
        }
    }

    @Override
    public void onModalInteraction(@NotNull ModalInteractionEvent event) {
        String dcname = serects.dcname();

        if (event.getModalId().equals("minecraftNickname")) {
            event.deferReply().queue();
            String name = event.getValue("minecraftNickname").getAsString();
            String uuid = mcplayeruuid(name);;
            EmbedBuilder embed = new EmbedBuilder();
            embed.setFooter("D-Day System by moonlight.one");

            if (uuid.equals("Not account")){
                embed.setTitle("계정 등록 실패");
                embed.setDescription("없는 마인크래프트 닉네임(계정)입니다.");
                embed.setColor(Color.red);
                event.getHook().sendMessage("").setEmbeds(embed.build()).setEphemeral(true).queue();
            }else{
                if (pattern.matcher(name).find()){

                    PreparedStatement pstmt = null;
                    ResultSet rs = null;
                    String sql = null;
                    int result = 0;
                    embed.setFooter(dcname+" System by moonlight.one");
                    embed.setColor(Color.green);

                    try {
                        Connection conn = DBConnect.getConnection();
                        switch (event.getGuild().getId()) {
                            case "1026881171507921036" -> {
                                //일반용
                                sql = "UPDATE user_account SET uuid_mc = ? WHERE uuid_dc = ?";
                                pstmt = conn.prepareStatement(sql);
                                pstmt.setString(1, uuid);
                                pstmt.setString(2, event.getUser().getId());
                                result = pstmt.executeUpdate();

                                embed.setTitle("마인크래프트 계정 등록!");
                                embed.setDescription(dcname + "의 모든 콘텐츠서버에 접속이 가능합니다.");
                                embed.setColor(Color.orange);
                                embed.addField("닉네임", name, false);
                                embed.addField("고유ID", uuid, false);
                                event.getHook().sendMessage("").setEmbeds(embed.build()).setEphemeral(true).queue(message -> {
                                    message.addReaction(Emoji.emoji_eheck).queue();
                                });
                            }
                            case "875723042200911893" -> {
                                //방송
                            }
                            default -> {
                                embed.setTitle("계정 등록 실패");
                                embed.setDescription("등록되지 않은 서버입니다.");
                                embed.setColor(Color.RED);
                                embed.addField(event.getGuild().getName() + "서버의", "정보를 서버로 전송합니다.", false);
                                event.getHook().sendMessage("").setEmbeds(embed.build()).setEphemeral(true).queue();
                            }
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }else{
                    event.getHook().sendMessage("마인크래프트 닉네임이 올바르지 않습니다.").setEphemeral(true).queue();
                }
            }
        }
    }
}