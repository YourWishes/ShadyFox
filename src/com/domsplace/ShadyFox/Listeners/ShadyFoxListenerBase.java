package com.domsplace.ShadyFox.Listeners;

import com.domsplace.ShadyFox.ShadyFoxBase;
import org.bukkit.event.Listener;

public class ShadyFoxListenerBase extends ShadyFoxBase implements Listener {
    public ShadyFoxListenerBase() {
        getPlugin().RegisterListener(this);
    }
}
