//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.rainchat.soulpvp.utils.objects;

import com.rainchat.soulpvp.hook.PlaceholderApiCompatibility;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

final public class Nametag {
    private final String name;
    private final Player player;
    private final String prefix;
    private final Team team;
    private Boolean isAfterIncluding13;

    public Nametag(Player player, String prefix, Scoreboard scoreboard) {
        Team team1;
        this.player = player;
        this.name = player.getName();
        this.prefix = "test";
        team1 = scoreboard.getTeam(name);

        if (team1 == null) {
            team1 = scoreboard.registerNewTeam(name);
        }
        this.team = team1;
    }

    public String getName() {
        return this.name;
    }

    public void update() {


        /*String preffix2 = "";
        preffix2 = PlaceholderApiCompatibility.setPlaceholders(player, PlaceholderApiCompatibility.getTeg(player));
        team.setPrefix(Color.parseHexString(preffix2));
        team.setColor(this.fromPrefix(preffix2));
        team.addEntry(player.getName());


        System.out.println(team.getEntries() + " " + team.getPrefix() + " " + preffix2+player.getName());
        */

        Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        registerTeams(scoreboard);
        int i = 0;
        for (Player all : Bukkit.getOnlinePlayers()) {
            getTeam(scoreboard, all.getName()).addPlayer(all);
        }
        player.setScoreboard(scoreboard);

    }


    public void registerTeams(Scoreboard scoreboard) {
        for (Player teamPlayer : Bukkit.getOnlinePlayers()) {
            String text = PlaceholderApiCompatibility.setPlaceholders(teamPlayer,PlaceholderApiCompatibility.getTeg(teamPlayer));
            registerTeam(scoreboard, teamPlayer.getName(), text);
        }
    }

    private void registerTeam(Scoreboard scoreboard, String name, String prefix) {
        Team team = scoreboard.registerNewTeam(name);
        team.setPrefix(prefix);
        team.setColor(fromPrefix(prefix));
    }

    private Team getTeam(Scoreboard scoreboard, String name) {
        Team team = scoreboard.getTeam(name);
        return team;
    }


    private ChatColor fromPrefix(String prefix) {
        char colour = 0;
        char[] chars = prefix.toCharArray();

        for(int i = 0; i < chars.length; ++i) {
            char at = chars[i];
            if ((at == 167 || at == '&') && i + 1 < chars.length) {
                char code = chars[i + 1];
                if (ChatColor.getByChar(code) != null) {
                    colour = code;
                }
            }
        }

        return colour == 0 ? ChatColor.RESET : ChatColor.getByChar(colour);
    }

    public void remove() {
       // team.unregister();
    }
}
