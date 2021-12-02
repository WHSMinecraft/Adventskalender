package de.whsminecraft.Adventskalender;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class DataStore {
    private Set<UUID> playersFromToday;
    private LocalDate currentDay;
    private Plugin plugin;

    public DataStore(Plugin plugin) {
        this.plugin = plugin;
        currentDay = LocalDate.now();
        playersFromToday = new HashSet<>();
    }

    public boolean alreadyOpenedToday(Player player) {
        checkAndUpdate();
        return playersFromToday.contains(player.getUniqueId());
    }

    public void markPlayer(Player player) {
        checkAndUpdate();
        playersFromToday.add(player.getUniqueId());
    }

    public List<OfflinePlayer> getPlayersFromToday() {
        checkAndUpdate();
        return playersFromToday.stream().map(uuid -> Bukkit.getOfflinePlayer(uuid)).collect(Collectors.toList());
    }

    private void checkAndUpdate() {
        // LocalDate only contains the date, not the time of day, so unequal means different day
        if (!LocalDate.now().equals(currentDay))
            playersFromToday.clear();
    }
}
