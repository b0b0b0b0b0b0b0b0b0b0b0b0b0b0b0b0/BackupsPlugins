package org.b0b0b0.backupsplugins;

import org.b0b0b0.backupsplugins.commands.BpcCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public final class BackupsPlugins extends JavaPlugin {

    private List<String> ignoredFolders;

    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();
        loadIgnoredFolders();

        // Register the bpc command and its tab completer
        getCommand("bpc").setExecutor(new BpcCommand(this));
        getCommand("bpc").setTabCompleter(new BpcCommand(this));
    }

    public void loadIgnoredFolders() {
        ignoredFolders = getConfig().getStringList("ignore-folders");
    }

    public List<String> getIgnoredFolders() {
        return ignoredFolders;
    }
}
