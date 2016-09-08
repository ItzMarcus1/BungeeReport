package me.itzmarcus.bungeereport.handlers;

import me.itzmarcus.bungeereport.utils.FileUtils;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.event.EventHandler;

/**
 * Created by marcus on 08-09-2016.
 */
public class FirstJoin implements Listener {

    FileUtils fileUtils = new FileUtils();

    @EventHandler
    public void join(PostLoginEvent e) {
        Configuration configuration = fileUtils.getConfig("cooldowns.yml");
        if(!configuration.getStringList("cooldowns").contains(e.getPlayer().getName())) {
            configuration.set("cooldowns." + e.getPlayer().getName(), 0);
            fileUtils.saveFile(configuration, "cooldowns.yml");
        }
    }
}
