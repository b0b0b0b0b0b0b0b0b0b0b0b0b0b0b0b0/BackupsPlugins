package org.b0b0b0.backupsplugins.commands;

import org.b0b0b0.backupsplugins.BackupsPlugins;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BpcCommand implements CommandExecutor, TabCompleter {

    private final BackupsPlugins plugin;

    public BpcCommand(BackupsPlugins plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 1) {
            sender.sendMessage("Usage: /bpc <run|reload>");
            return false;
        }

        switch (args[0].toLowerCase()) {
            case "run":
                return new Backup(plugin).runBackup(sender);

            case "reload":
                return new ReloadPlugin(plugin).reloadConfig(sender);

            default:
                sender.sendMessage("Usage: /bpc <run|reload>");
                return false;
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            return Arrays.asList("run", "reload");
        }
        return new ArrayList<>();
    }
}
