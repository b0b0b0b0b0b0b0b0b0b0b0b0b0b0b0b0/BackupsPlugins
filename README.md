# BackupsPlugins

BackupsPlugins is a Minecraft plugin designed to back up your server's plugin configurations and reload the plugin configuration without restarting the server. It provides a main command `bpc` with two subcommands: `run` and `reload`.

## Features
- **Backup Configurations**: Back up all plugin configurations into a timestamped ZIP file.
- **Reload Configuration**: Reload the plugin configuration without restarting the server.
- **Customizable Ignored Folders**: Specify folders to ignore during backups via the configuration file.

## Commands
### Main Command: `bpc`
- **Subcommands**:
  - **`run`**: Runs the backup process.
  - **`reload`**: Reloads the plugin configuration.

#### Usage
- `/bpc run`: Initiates the backup process.
- `/bpc reload`: Reloads the plugin configuration.

## Permissions
- `backupplugin.use`: Allows the player to use the `bpc` command (default: op).
- `backupplugin.backup`: Allows the player to run backups with `/bpc run` (default: op).
- `backupplugin.reload`: Allows the player to reload the configuration with `/bpc reload` (default: op).

## Configuration
The default configuration file `config.yml` includes a list of folders to ignore during the backup process.

```yaml
# ======================================
#  BackupsPlugins Configuration File
# ======================================

# List of folders to ignore during backup
ignore-folders:
  - backups # Folder of the plugin itself. Removing it may cause errors.
  - .paper-remapped # Remapper folder for Paper on 1.20.6+. Removing it may cause errors.
```

### Installation
1. Download the latest version of BackupsPlugins.
2. Place the `BackupsPlugins.jar` file in the `plugins` folder of your Minecraft server.
3. Start the server to generate the default configuration file.
4. Customize the `config.yml` file located in the `plugins/BackupsPlugins` folder to specify folders to ignore during backups.
