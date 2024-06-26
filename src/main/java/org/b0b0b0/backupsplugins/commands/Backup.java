package org.b0b0b0.backupsplugins.commands;

import org.b0b0b0.backupsplugins.BackupsPlugins;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Backup {

    private final BackupsPlugins plugin;

    public Backup(BackupsPlugins plugin) {
        this.plugin = plugin;
    }

    public boolean runBackup(CommandSender sender) {
        if (sender instanceof Player && !sender.hasPermission("backupplugin.backup")) {
            sender.sendMessage("You do not have permission to use this command.");
            return true;
        }

        File pluginsFolder = new File("plugins");
        String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
        File backupZip = new File(pluginsFolder, "backupsplugins/backups/" + timestamp + ".zip");

        List<String> ignoredFolders = plugin.getIgnoredFolders();

        CompletableFuture.runAsync(() -> {
            try {
                if (!backupZip.getParentFile().exists() && !backupZip.getParentFile().mkdirs()) {
                    sender.sendMessage("Could not create backup directory!");
                    return;
                }

                File[] pluginFolders = Objects.requireNonNull(pluginsFolder.listFiles(File::isDirectory));
                int totalFolders = pluginFolders.length;
                int completedFolders = 0;

                try (FileOutputStream fos = new FileOutputStream(backupZip);
                     ZipOutputStream zos = new ZipOutputStream(fos)) {

                    for (File pluginFolder : pluginFolders) {
                        if (!ignoredFolders.contains(pluginFolder.getName())) {
                            zipDirectory(pluginFolder, pluginFolder.getName(), zos);
                            completedFolders++;
                            int progress = (completedFolders * 100) / totalFolders;
                            Bukkit.getLogger().info("Backup progress: " + progress + "%");
                        }
                    }
                }

                Bukkit.getScheduler().runTask(plugin, () ->
                        sender.sendMessage("Configs have been backed up to " + backupZip.getPath())
                );
            } catch (IOException e) {
                Bukkit.getScheduler().runTask(plugin, () ->
                        sender.sendMessage("Failed to create backup zip file")
                );
                e.printStackTrace();
            }
        });

        return true;
    }

    private void zipDirectory(File folder, String parentFolder, ZipOutputStream zos) throws IOException {
        for (File file : Objects.requireNonNull(folder.listFiles())) {
            if (file.isDirectory()) {
                zipDirectory(file, parentFolder + "/" + file.getName(), zos);
                continue;
            }
            // Ignore .jar files
            if (file.getName().endsWith(".jar")) {
                continue;
            }
            try (FileInputStream fis = new FileInputStream(file)) {
                ZipEntry zipEntry = new ZipEntry(parentFolder + "/" + file.getName());
                zos.putNextEntry(zipEntry);

                byte[] bytes = new byte[1024];
                int length;
                while ((length = fis.read(bytes)) >= 0) {
                    zos.write(bytes, 0, length);
                }
                zos.closeEntry();
            }
        }
    }
}
