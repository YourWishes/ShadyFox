package com.domsplace.ShadyFox.Listeners;

import com.domsplace.ShadyFox.Constants.ShadyFoxSQLQueries;
import com.domsplace.ShadyFox.Utils.ShadyFoxSQLUtils;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;

public class ShadyFoxLoginListener extends ShadyFoxListenerBase {
    @EventHandler
    public void addPlayerRecord(PlayerJoinEvent e) {
        String query = ShadyFoxSQLQueries.addIPQuery(e.getPlayer());
        
        ShadyFoxSQLUtils.sqlQuery(query);
        getPlugin().getLogger().log(Level.INFO, "Adding record for {0}", e.getPlayer().getName());
    }
    
    @EventHandler(ignoreCancelled=true, priority=EventPriority.HIGHEST)
    public void displayPlayerRecords(PlayerJoinEvent e) {
        List<OfflinePlayer> players = ShadyFoxSQLQueries.getPlayersFromIP(e.getPlayer().getAddress().getAddress());
        List<InetAddress> ipAddresses = ShadyFoxSQLQueries.getIPsFromPlayer(Bukkit.getOfflinePlayer(e.getPlayer().getName()));
        
        String ipmsg = "";
        for(OfflinePlayer p : players) {
            ipmsg += p.getName() + ", ";
            
            if(p.getName().equals(e.getPlayer().getName())) {
                continue;
            }
            
            if(!p.isOnline()) {
                continue;
            }
            permBroadcast("ShadyFox.notify", ChatImportant + "Warning! " + ChatDefault + p.getName() + " and " + e.getPlayer().getName() + " have the same IP!");
        }
        
        String addmsg = "";
        for(InetAddress ia : ipAddresses) {
            addmsg += ia.getHostAddress() + ", ";
        }
        
        List<String> message = new ArrayList<String>();
        
        message.add(ChatImportant + e.getPlayer().getName() + ChatDefault + " logged in from " + ChatImportant + e.getPlayer().getAddress().getAddress().getHostAddress());
        message.add(ChatImportant + "Players with same IP: " + ChatDefault + ipmsg);
        message.add(ChatImportant + "IPs from this Player: " + ChatDefault + addmsg);
        
        msgConsole(message);
    }
}
