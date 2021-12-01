package de.whsminecraft.Adventskalendar;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class DataStore {
    private Set<Player> players;
    private LocalDate currentSet;
    private Plugin plugin;

    public DataStore(Plugin plugin) {
        this.plugin = plugin;
        currentSet = LocalDate.now();
        players = new HashSet<>();
    }

    public boolean alreadyOpenedToday(Player player) {
        return players.contains(player);
    }

    public void markPlayer(Player player) {
        if (!LocalDate.now().equals(currentSet))
            players.clear();

        players.add(player);
    }
}
