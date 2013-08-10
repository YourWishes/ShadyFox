package com.domsplace.ShadyFox.Constants;

import com.domsplace.ShadyFox.Utils.ShadyFoxSQLUtils;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class ShadyFoxSQLQueries {
    public static final String TABLE_CREATE_QUERY = 
        "CREATE TABLE IF NOT EXISTS %p% (" +
        "`PlayerID` int(11) NOT NULL AUTO_INCREMENT," +
        "`PlayerUsername` varchar(96) NOT NULL," +
        "`PlayerIP` varchar(16) NOT NULL," +
        "CONSTRAINT ShadyFoxPlayersPK PRIMARY KEY (`PlayerID`)" +
        ");"
    ;
    
    public static String addIPQuery(Player player) {
        String ip = player.getAddress().getAddress().getHostAddress();
        String plyr = player.getName();
        
        String query = "INSERT INTO %p% (`PlayerUsername`, `PlayerIP`) VALUES ('" + plyr + "', '" + ip + "');";
        
        return query;
    }
    
    public static List<OfflinePlayer> getPlayersFromIP(InetAddress address) {
        List<OfflinePlayer> players = new ArrayList<OfflinePlayer>();
        
        String query = "SELECT DISTINCT `PlayerUsername` FROM %p% WHERE `PlayerIP`='" + address.getHostAddress() + "';";
        
        try {
            List<Map<String, String>> result = ShadyFoxSQLUtils.sqlFetch(query);

            for(Map<String, String> row : result) {
                String username = row.get("PlayerUsername");
                OfflinePlayer player = Bukkit.getOfflinePlayer(username);
                players.add(player);
            }
        } catch(Exception ex) {
        }
        
        return players;
    }

    public static List<InetAddress> getIPsFromPlayer(OfflinePlayer player) {
        List<InetAddress> addresses = new ArrayList<InetAddress>();
        
        String query = "SELECT DISTINCT `PlayerIP` FROM %p% WHERE `PlayerUsername`='" + player.getName() + "';";
        try {
            List<Map<String, String>> result = ShadyFoxSQLUtils.sqlFetch(query);
            for(Map<String, String> row : result) {
                String ipstr = row.get("PlayerIP");
                InetAddress addr = InetAddress.getByName(ipstr);
                addresses.add(addr);
            }
        } catch(Exception ex) {
        }
        
        return addresses;
    }
}
