package de.whsminecraft.Adventskalendar;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class DataStore {
    private Set<UUID> players;
    private LocalDate currentSet;
    private Plugin plugin;

    public DataStore(Plugin plugin) {
        this.plugin = plugin;
        currentSet = LocalDate.now();
        players = new HashSet<>();
    }

    public boolean alreadyOpenedToday(Player player) {
        return players.contains(player.getUniqueId());
    }

    public void markPlayer(Player player) {
        if (!LocalDate.now().equals(currentSet))
            players.clear();

        players.add(player.getUniqueId());
    }

    public List<OfflinePlayer> getPlayersFromToday() {
        return players.stream().map(uuid -> Bukkit.getOfflinePlayer(uuid)).collect(Collectors.toList());
    }
}
