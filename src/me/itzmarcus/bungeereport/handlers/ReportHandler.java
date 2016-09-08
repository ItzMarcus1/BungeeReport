package me.itzmarcus.bungeereport.handlers;

import me.itzmarcus.bungeereport.Core;
import me.itzmarcus.bungeereport.utils.FileUtils;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.config.Configuration;

/**
 * Created by marcus on 07-09-2016.
 */
public class ReportHandler {

    FileUtils fileUtils = new FileUtils();

    public void sendReport(String sender, String reported, String server, String message) {
        Configuration conf = fileUtils.getConfig("data.yml");
        conf.set("report-count", conf.getInt("report-count") + 1);
        fileUtils.saveFile(conf, "data.yml");
        for(ProxiedPlayer all : ProxyServer.getInstance().getPlayers()) {
            if(all.hasPermission("report.staff")) {
                all.sendMessage("§7■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
                all.sendMessage("§8» §a§l§nReport #" + conf.getInt("report-count"));
                all.sendMessage("");
                all.sendMessage("§8» §c§lServer:§r §e" + server);
                all.sendMessage("§8» §c§lReport fra:§r §e" + sender);
                all.sendMessage("§8» §c§lReported spiller:§r §e" + reported);
                all.sendMessage("§8» §c§lGrund:§r §e" + message);
                all.sendMessage("");
                all.sendMessage("§7■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
            }
        }
    }
}
