package com.domsplace.ShadyFox.DataManagers;

import com.domsplace.ShadyFox.Constants.ShadyFoxSQLQueries;
import com.domsplace.ShadyFox.ShadyFoxBase;
import com.domsplace.ShadyFox.Utils.ShadyFoxSQLUtils;
import java.io.File;
import org.bukkit.configuration.file.YamlConfiguration;

public class ShadyFoxConfigManager extends ShadyFoxBase {
    
    public static File configFile;
    public static YamlConfiguration config;
    
    public static boolean loadConfig() {
        try {
            if(!getPlugin().getDataFolder().exists()) {
                getPlugin().getDataFolder().mkdir();
            }
            
            configFile = new File(getPlugin().getDataFolder(), "config.yml");
            
            if(!configFile.exists()) {
                configFile.createNewFile();
            }
            
            config = YamlConfiguration.loadConfiguration(configFile);
            
            if(!config.contains("sql")) {                
                config.set("sql.username", "root");
                config.set("sql.password", "password");
                config.set("sql.host", "localhost");
                config.set("sql.database", "minecraft");
                config.set("sql.port", "3306");
                config.set("sql.tableprefix", "ShadyFox_");
            }
            
            config.save(configFile);
            
            ShadyFoxSQLUtils.host = config.getString("sql.host");
            ShadyFoxSQLUtils.port = config.getString("sql.port");
            ShadyFoxSQLUtils.database = config.getString("sql.database");
            ShadyFoxSQLUtils.username = config.getString("sql.username");
            ShadyFoxSQLUtils.password = config.getString("sql.password");
            ShadyFoxSQLUtils.prefix = config.getString("sql.tableprefix").replaceAll("%p%", "p");
            
            if(ShadyFoxSQLUtils.sql != null) {
                ShadyFoxSQLUtils.sqlClose();
            }
            
            if(!ShadyFoxSQLUtils.sqlConnect()) {
                return false;
            }
            
            ShadyFoxSQLUtils.sqlQuery(ShadyFoxSQLQueries.TABLE_CREATE_QUERY);
            
            return true;
        } catch(Exception ex) {
            error(ex);
            return false;
        }
    }
}
