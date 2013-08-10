package com.domsplace.ShadyFox;

import com.domsplace.ShadyFox.Commands.ShadyFoxCommandLookup;
import com.domsplace.ShadyFox.Constants.ShadyFoxSQLQueries;
import com.domsplace.ShadyFox.DataManagers.ShadyFoxConfigManager;
import com.domsplace.ShadyFox.DataManagers.ShadyFoxPluginManager;
import com.domsplace.ShadyFox.Listeners.ShadyFoxLoginListener;
import com.domsplace.ShadyFox.Utils.ShadyFoxSQLUtils;
import java.util.logging.Level;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class ShadyFoxPlugin extends JavaPlugin {
    
    public static boolean isLoaded = false;
    public static PluginManager pluginManager;
    
    //Commands
    public static ShadyFoxCommandLookup LookupCommand;
    
    //Listeners
    public static ShadyFoxLoginListener LoginListener;
    
    @Override
    public void onEnable() {
        pluginManager = Bukkit.getPluginManager();
        
        if(!ShadyFoxPluginManager.LoadPlugin()) {
            Disable();
            ShadyFoxBase.error("Failed to load plugin.yml.");
            return;
        }
        
        if(!ShadyFoxConfigManager.loadConfig()) {
            Disable();
            ShadyFoxBase.error("Failed to load config.yml");
            return;
        }
        
        //Load Commands
        LookupCommand = new ShadyFoxCommandLookup();
        
        //Load Listeners
        LoginListener = new ShadyFoxLoginListener();
        
        for(Player p : Bukkit.getOnlinePlayers()) {
            String query = ShadyFoxSQLQueries.addIPQuery(p);

            ShadyFoxSQLUtils.sqlQuery(query);
            getPlugin().getLogger().log(Level.INFO, "Adding record for {0}", p.getName());
        }
        
        isLoaded = true;
        ShadyFoxBase.permBroadcast(
            "ShadyFox.*",
            "Enabled " + ShadyFoxPluginManager.getPluginName() + " version " + ShadyFoxPluginManager.getPluginVersion() + "."
        );
    }
    
    @Override
    public void onDisable() {
        if(!isLoaded) {
            ShadyFoxBase.msgConsole("Failed to load ShadyFox!");
            return;
        }
    }
    
    public void Disable() {
        Bukkit.getServer().getPluginManager().disablePlugin(this);
    }
    
    public void RegisterListener(Listener l) {
        pluginManager.registerEvents(l, this);
    }
    
    public static com.domsplace.ShadyFox.ShadyFoxPlugin getPlugin() {
        try {
            Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("ShadyFox");
            if(plugin == null || !plugin.isEnabled() || !(plugin instanceof com.domsplace.ShadyFox.ShadyFoxPlugin)) {
                return null;
            }
            
            return (com.domsplace.ShadyFox.ShadyFoxPlugin) plugin;
        } catch(NoClassDefFoundError e) {
            return null;
        } catch(Exception e) {
            return null;
        }
    }
    
}
