package com.domsplace.ShadyFox.Commands;

import com.domsplace.ShadyFox.Constants.ShadyFoxSQLQueries;
import java.net.InetAddress;
import java.util.List;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class ShadyFoxCommandLookup extends ShadyFoxCommandBase {
    public ShadyFoxCommandLookup() {
        super("shadylookup");
    }
    
    @Override
    public boolean cmd(CommandSender sender, Command cmd, String label, String[] args) {
        if(args.length < 1) {
            sender.sendMessage(ChatError + "Please enter a player or an IP address.");
            return true;
        }
        
        //Try to get IP address
        String IPADDRESS_PATTERN = 
        "(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)";
        
        if(args[0].matches(IPADDRESS_PATTERN)) {
            InetAddress address;
            try {
                address = InetAddress.getByName(args[0]);
            } catch(Exception ex) {
                sender.sendMessage(ChatError + "Invalid IP address.");
                return true;
            }
            
            List<OfflinePlayer> players = ShadyFoxSQLQueries.getPlayersFromIP(address);
            
            
            String msg = "";
            for(OfflinePlayer p : players) {
                msg += p.getName() + ", ";
            }
            
            sender.sendMessage(ChatImportant + "The following players have used this IP address:");
            sender.sendMessage(ChatDefault + msg);
            return true;
        } else {
            OfflinePlayer p = getOfflinePlayer(args[0], sender);
            if(p == null) {
                sender.sendMessage(ChatError + args[0] + " isn't a valid player.");
                return true;
            }
            
            List<InetAddress> addresses = ShadyFoxSQLQueries.getIPsFromPlayer(p);
            
            String msg = "";
            for(InetAddress ia : addresses) {
                msg += ia.getHostAddress() + ", ";
            }
            
            sender.sendMessage(ChatImportant + "This player has connected from the following IP Addresses:");
            sender.sendMessage(ChatDefault + msg);
            return true;
        }
    }
}
