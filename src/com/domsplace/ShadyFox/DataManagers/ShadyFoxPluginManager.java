package com.domsplace.ShadyFox.DataManagers;

import com.domsplace.ShadyFox.ShadyFoxBase;
import java.io.InputStream;
import org.bukkit.configuration.file.YamlConfiguration;

public class ShadyFoxPluginManager extends ShadyFoxBase {
    
    public static YamlConfiguration pluginYML;
    public static boolean LoadPlugin() {
        try {
            InputStream is = getPlugin().getResource("plugin.yml");
            
            pluginYML = YamlConfiguration.loadConfiguration(is);
            
            is.close();
            
            return true;
        } catch(Exception ex) {
            error(ex);
            return false;
        }
    }
    
    public static String getPluginName() {
        return pluginYML.getString("name");
    }
    
    public static String getPluginVersion() {
        return pluginYML.getString("version");
    }
}
