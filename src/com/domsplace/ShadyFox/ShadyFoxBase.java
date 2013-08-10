package com.domsplace.ShadyFox;

import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ShadyFoxBase {
    
    public static String ChatDefault = ChatColor.GRAY.toString();
    public static String ChatImportant = ChatColor.GOLD.toString();
    public static String ChatError = ChatColor.RED.toString();
    
    public static void msgPlayer(CommandSender player, String message) {
        player.sendMessage(ChatDefault + message);
    }
    
    public static void msgConsole(String message) {
        msgPlayer(Bukkit.getConsoleSender(), message);
    }
    
    public static void msgConsole(List<String> message) {
        for(String s : message) {
            msgConsole(s);
        }
    }
    
    public static void broadcast(String message) {
        msgConsole(message);
        for(Player p : Bukkit.getOnlinePlayers()) {
            msgPlayer(p, message);
        }
    }
    
    public static void debug(Object message) {
        broadcast("§bDEBUG: §d" + message.toString());
    }
    
    public static void error(String error) {
        msgConsole("§4ERROR! §c" + error);
    }
    
    public static void error(Exception ex) {
        error(ex.getLocalizedMessage());
        msgConsole("§cCaused by:");
        ex.printStackTrace();
    }
    
    public static void permBroadcast(String perm, String message) {
        msgConsole(message);
        
        for(Player p : Bukkit.getOnlinePlayers()) {
            if(!p.hasPermission(perm)) {
                continue;
            }
            
            msgPlayer(p, message);
        }
    }
    
    public static ShadyFoxPlugin getPlugin() {
        return ShadyFoxPlugin.getPlugin();
    }
    
    public static boolean canSee(OfflinePlayer player, CommandSender getter) {
        if(!(getter instanceof Player)) {
            return true;
        }
        if(!player.isOnline()) {
            return true;
        }
        
        Player sender = (Player) getter;
        Player p = player.getPlayer();
        
        if(!sender.canSee(p)) {
            return false;
        }
        
        return true;
    }
    
    public static OfflinePlayer getOfflinePlayer(String name, CommandSender getter) {
        OfflinePlayer target = Bukkit.getPlayer(name);
        
        if(target != null && !canSee(target, getter)) {
            target = null;
        }
        
        if(target == null) {
            target = Bukkit.getOfflinePlayer(name);
        }
        
        return target;
    }
}
