package me.itzmarcus.bungeereport.commands;

import me.itzmarcus.bungeereport.handlers.ReportHandler;
import me.itzmarcus.bungeereport.utils.FileUtils;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.config.Configuration;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * Created by marcus on 08-09-2016.
 */
public class ReportCommand extends Command {

    public ReportCommand() {
        super("report");
    }

    private int cooldownMinutes = 5000;
    FileUtils fileUtils = new FileUtils();
    ReportHandler reportHandler = new ReportHandler();
    public static HashMap<String, Long> cooldowns = new HashMap<>();

    public final String PREFIX = fileUtils.getConfig("messages.yml").getString("prefix").replace("&", "§") + " ";

    @Override
    public void execute(CommandSender sender, String[] args) {
        ProxiedPlayer p = (ProxiedPlayer) sender;
        if(!cooldowns.containsKey(p.getName())) {
            Configuration config = fileUtils.getConfig("cooldowns.yml");
            cooldowns.put(p.getName(), config.getLong("cooldowns." + p.getName()));
        }
        if(args.length <= 1) {
            p.sendMessage(PREFIX + "§aBrug /report <spiller> <grund>. Du kan kun bruge Report kommandoen hver 5. minut.");
            p.sendMessage("");
            p.sendMessage(PREFIX + "§c§l§nHusk at læse vores regler inden du anmelder spillere - /regler");
            return;
        }
        if(getCooldownSeconds(p.getName()) > 0) {
            int minutes = (getCooldownSeconds(p.getName()) % 3600) / 60;
            int seconds = getCooldownSeconds(p.getName()) % 60;
            p.sendMessage(PREFIX + "§cDu kan først bruge denne kommando om §e" + minutes + " minut(ter) & " + seconds + " sekund(er)");
            return;
        }
        if(args.length >= 2) {
            ProxiedPlayer target = ProxyServer.getInstance().getPlayer(args[0]);
            if(target == null) {
                p.sendMessage(PREFIX + "§cSpilleren er ikke §c§nonline§c.");
                return;
            }

            if(target.hasPermission("report.staff")) {
                p.sendMessage(PREFIX + "§cDu kan ikke anmelde vores Staffs. Du skal oprette en tråd på vores hjemmeside.");
                return;
            }

            StringBuilder str = new StringBuilder();
            for(int x=1;x < args.length; x++) {
                str.append(args[x]).append(" ");
            }
            String reportMessage = str.toString();
            cooldowns.put(p.getName(), System.currentTimeMillis());
            reportHandler.sendReport(p.getName(), target.getName(), ProxyServer.getInstance().getPlayer(p.getName()).getServer().getInfo().getName(), reportMessage);
        }
    }

    public int getCooldownSeconds(String name) {
        Long cooldown = (cooldowns.get(name)) - System.currentTimeMillis();
        return (int) TimeUnit.MILLISECONDS.toSeconds(cooldown) + 300;
    }
}
