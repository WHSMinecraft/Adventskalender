package de.whsminecraft.Adventskalendar;


import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.time.LocalDate;

public class Main extends JavaPlugin implements Listener {
    private DataStore dataStore;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        getServer().getPluginManager().registerEvents(this, this);

        dataStore = new DataStore(this);

        CommandAdvent cmd2 = new CommandAdvent(this, dataStore);
        this.getCommand("advent").setExecutor(cmd2);



        getLogger().info("Plugin was successfully enabled.");
    }

    @Override
    public void onDisable() {
        getLogger().info("Plugin was successfully disabled.");
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {BaseComponent message = new TextComponent("");
        if (dataStore.alreadyOpenedToday(e.getPlayer()))
            return;

        // Max width of chat console
        TextComponent divider = new TextComponent("-----------------------------------------------------");
        divider.setColor(ChatColor.DARK_RED);

        int day = LocalDate.now().getDayOfMonth();

        TextComponent title = new TextComponent("Advent, Advent!");
        TextComponent text = new TextComponent("Öffne das " + day + ". Türchen des Adventskalendars:");
        TextComponent cmd = new TextComponent("/advent");

        title.setColor(ChatColor.DARK_GREEN);
        text.setColor(ChatColor.DARK_GREEN);
        cmd.setColor(ChatColor.GOLD);

        cmd.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, cmd.getText()));
        cmd.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("Was könnte es heute sein?")));



        message.addExtra(divider);
        message.addExtra("\n");
        message.addExtra(title);
        message.addExtra("\n");
        message.addExtra(text);
        message.addExtra("\n");
        message.addExtra(cmd);
        message.addExtra("\n");
        message.addExtra(divider);


        e.getPlayer().spigot().sendMessage(message);
    }
}
