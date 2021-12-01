package de.whsminecraft.Adventskalendar;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;


public class CommandAdvent implements CommandExecutor {
    private Plugin plugin;
    private DataStore dataStore;

    public CommandAdvent(Plugin plugin, DataStore dataStore) {
        this.plugin = plugin;
        this.dataStore = dataStore;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (commandSender instanceof BlockCommandSender) {
            return false;
        }

        if (args.length > 1) {
            return false;
        }

        Player sender = (Player) commandSender;
        String playerName = sender.getDisplayName();


        if (dataStore.alreadyOpenedToday(sender)) {
            plugin.getLogger().info("Door was already opened today by " + playerName);
            sender.sendMessage(ChatColor.DARK_RED + "Du hast das heutige Türchen bereits geöffnet");
            return true;
        }

        if (sender.getInventory().firstEmpty() == -1) {
            plugin.getLogger().info("Full inventory for " + playerName);
            sender.sendMessage(ChatColor.DARK_RED + "Du hast momentan keinen Platz im Inventar frei. Versuch es nochmal.");
            return true;
        }

        int day = LocalDate.now().getDayOfMonth();

        if (day < 1 || 24 < day) {
            plugin.getLogger().info("It's no longer christmas: Day is " + day);
            sender.sendMessage(ChatColor.DARK_RED + "Die Zeit des Adventskalendars ist nun leider vorbei :-(");
            return true;
        }

        String item = plugin.getConfig().getConfigurationSection("items").getString("" + day);

        if (item == null) {
            plugin.getLogger().severe("No item in the advent calendar for day " + day);
            sender.sendMessage(ChatColor.DARK_RED + "Leider hat der Admin vergessen für diesen Tag etwas in den Adventskalendar zu tun. Schreib ihm dass er's verkackt hat.");
            return true;
        }

        String giveCommand = "give " + sender.getDisplayName() + " " + item;

        plugin.getLogger().info("Giving item: " + giveCommand);

        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), giveCommand);

        plugin.getLogger().info("Gave item to " + playerName);
        sender.sendMessage(ChatColor.DARK_GREEN + "Bis zum nächsten Mal!");

        dataStore.markPlayer(sender);

        return true;
    }
}
