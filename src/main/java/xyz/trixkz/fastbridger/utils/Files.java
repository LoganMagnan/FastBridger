package xyz.trixkz.fastbridger.utils;

import lombok.Getter;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import xyz.trixkz.fastbridger.Main;

import java.io.File;
import java.io.IOException;

public class Files {

    private Main main;

    @Getter public File configFile;
    @Getter private FileConfiguration config;

    @Getter public File arenasConfigFile;
    @Getter private FileConfiguration arenasConfig;

    public Files(Main main) {
        this.main = main;
    }

    public void loadConfig() {
        if (!this.main.getDataFolder().exists()) {
            this.main.getDataFolder().mkdirs();
        }

        configFile = new File(main.getDataFolder(), "settings.yml");

        if (!configFile.exists()) {
            this.main.saveResource("settings.yml", false);
        }

        config = YamlConfiguration.loadConfiguration(configFile);

        arenasConfigFile = new File(main.getDataFolder(), "arenas.yml");

        if (!arenasConfigFile.exists()) {
            this.main.saveResource("arenas.yml", false);
        }

        arenasConfig = YamlConfiguration.loadConfiguration(arenasConfigFile);
    }

    public FileConfiguration loadUser(Player player) {
        File hookDataf = new File(main.getDataFolder() + File.separator + "Players" + File.separator + player.getUniqueId() + ".yml");

        if (!hookDataf.exists()) {
            hookDataf.getParentFile().mkdirs();

            try {
                hookDataf.createNewFile();
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }

        FileConfiguration hookData = new YamlConfiguration();

        try {
            hookData.load(hookDataf);
        } catch (IOException exception) {
            exception.printStackTrace();
        } catch (InvalidConfigurationException exception) {
            exception.printStackTrace();
        }

        return hookData;
    }

    public void saveUser(Player player, FileConfiguration yml) {
        File folder = new File(main.getDataFolder() + File.separator + "Players");
        File file = new File(folder, File.separator + player.getUniqueId() + ".yml");

        try {
            yml.save(file);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}