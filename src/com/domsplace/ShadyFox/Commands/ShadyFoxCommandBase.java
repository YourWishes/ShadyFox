package com.domsplace.ShadyFox.Commands;

import com.domsplace.ShadyFox.ShadyFoxBase;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ShadyFoxCommandBase extends ShadyFoxBase implements CommandExecutor {
    private String command;
    
    public ShadyFoxCommandBase(String command) {
        this.command = command;
        getPlugin().getCommand(command.toLowerCase()).setExecutor(this);
    }
    
    public String getCommand() {
        return this.command;
    }

    @Override
    public boolean onCommand(CommandSender cs, Command cmnd, String string, String[] strings) {
        if(cmnd.getName().equalsIgnoreCase(this.getCommand())) {
            return cmd(cs, cmnd, string, strings);
        }
        return false;
    }
    
    public boolean cmd(CommandSender sender, Command cmd, String label, String[] args) {
        return false;
    }
}
