package me.itzmarcus.bungeereport;

import me.itzmarcus.bungeereport.commands.ReportCommand;
import me.itzmarcus.bungeereport.handlers.FirstJoin;
import me.itzmarcus.bungeereport.utils.FileUtils;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.event.EventHandler;


/**
 * Created by marcus on 07-09-2016.
 */
public class Core extends Plugin {

    FileUtils fileUtils;

    @Override
    public void onEnable() {
        fileUtils = new FileUtils();
        getProxy().getPluginManager().registerCommand(this, new ReportCommand());
        getProxy().getPluginManager().registerListener(this, new FirstJoin());
        if(!getDataFolder().exists()) { getDataFolder().mkdir(); }
        if(!fileUtils.fileExists("data.yml")) {
            fileUtils.getNewFile("data.yml");
            Configuration config = fileUtils.getConfig("data.yml");
            config.set("report-count", 0);
            fileUtils.saveFile(config, "data.yml");
            getProxy().getConsole().sendMessage("Created new file: data.yml at " + fileUtils.getFile("data.yml").getAbsolutePath());
        }
        if(!fileUtils.fileExists("messages.yml")) {
            fileUtils.getNewFile("messages.yml");
            Configuration msgConfig = fileUtils.getConfig("messages.yml");
            msgConfig.set("prefix", "&8[&3Prefix&8]");
            fileUtils.saveFile(msgConfig, "messages.yml");
        }
        if(!fileUtils.fileExists("cooldowns.yml")) {
            fileUtils.getNewFile("cooldowns.yml");
        } else {
            Configuration cooldowns = fileUtils.getConfig("cooldowns.yml");
            for(String s : cooldowns.getStringList("cooldowns")) {
                ReportCommand.cooldowns.put(s, cooldowns.getLong("cooldowns." + s));
                getProxy().broadcast("Importeret:");
                getProxy().broadcast("s: " + cooldowns.getLong("cooldowns." + s));
            }
        }
    }

    @Override
    public void onDisable() {
        if(!ReportCommand.cooldowns.isEmpty()) {
            for(String s : ReportCommand.cooldowns.keySet()) {
                Configuration config = fileUtils.getConfig("cooldowns.yml");
                config.set("cooldowns." + s, ReportCommand.cooldowns.get(s));
                fileUtils.saveFile(config, "cooldowns.yml");
            }
        }
    }
}
