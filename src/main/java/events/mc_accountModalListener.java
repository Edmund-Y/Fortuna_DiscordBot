package events;

import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.regex.Pattern;

public class mc_accountModalListener extends ListenerAdapter {

    private static Dotenv config;
    private Connection con;
    private Statement stmt;
    Pattern pattern = Pattern.compile("^[0-9a-zA-Z_-]*$");

    public void DBConnection(){
        try{
            String url = "jdbc:mysql://dsm.moonlight.one:6133/?characterEncoding=UTF-8&serverTimezone=UTC";
            config = Dotenv.configure().load();
            String user = config.get("DBID");
            String passwd = config.get("DBPW");

            con = DriverManager.getConnection(url, user, passwd);
            stmt = con.createStatement();
            stmt.close();
            con.close();

        }catch (SQLException e){
            System.out.println("DB 연결오류 : " + e.getMessage());
        }
    }

    //
    public String mcplayeruuid(String nickname){
        //"https://api.mojang.com/users/profiles/minecraft/"+name
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
                embed.setTitle("계정 등록 성공");
                embed.addField("마인크래프트 닉네임", name+"", false);
                embed.addField("마인크래프트 고유식별", uuid+"", false);
                embed.setColor(Color.CYAN);

                if (pattern.matcher(name).find()){
                    switch (event.getGuild().getId()) {
                        case "875723042200911892" -> {
                            embed.setDescription("일반크루용 설정을 완료했습니다.");
                            event.getHook().sendMessage("").setEmbeds(embed.build()).setEphemeral(true).queue();
                        }
                        case "875723042200911893" -> {
                            embed.setDescription("방송크루용 설정을 완료했습니다.");
                            event.getHook().sendMessage("").setEmbeds(embed.build()).setEphemeral(true).queue();
                        }
                        default -> {
                            embed.setTitle("계정 등록 실패");
                            embed.setDescription("등록되지 않은 서버입니다.");
                            embed.setColor(Color.RED);
                            embed.addField(event.getGuild().getName() + "서버의", "정보를 서버로 전송합니다.", false);
                            event.getHook().sendMessage("").setEmbeds(embed.build()).setEphemeral(true).queue();
                        }
                    }
                }else{
                    event.getHook().sendMessage("마인크래프트 닉네임이 올바르지 않습니다.").setEphemeral(true).queue();
                }
            }
        }
    }
}