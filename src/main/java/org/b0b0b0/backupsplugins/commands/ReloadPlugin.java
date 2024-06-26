package org.b0b0b0.backupsplugins.commands;

import org.b0b0b0.backupsplugins.BackupsPlugins;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReloadPlugin {

    private final BackupsPlugins plugin;

    public ReloadPlugin(BackupsPlugins plugin) {
        this.plugin = plugin;
    }

    public boolean reloadConfig(CommandSender sender) {
        if (sender instanceof Player && !sender.hasPermission("backupplugin.reload")) {
            sender.sendMessage("You do not have permission to use this command.");
            return true;
        }

        plugin.reloadConfig();
        plugin.loadIgnoredFolders();
        sender.sendMessage("Backup plugin configuration reloaded.");
        return true;
    }
}
