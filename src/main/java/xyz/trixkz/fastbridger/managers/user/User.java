package xyz.trixkz.fastbridger.managers.user;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import lombok.Getter;
import lombok.Setter;
import org.bson.Document;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.trixkz.fastbridger.Main;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class User {

    @Getter private MongoCollection<Document> mongoCollection = Main.getInstance().getDatabaseManager().getMongoDatabase().getCollection("users");
    @Getter private static HashMap<UUID, User> users = new HashMap<UUID, User>();
    @Getter private static UUID uuid;
    @Getter private String name;
    @Getter @Setter private String block;
    @Getter @Setter private List<String> blocks = new ArrayList<String>();
    private int coinsBalance;
    @Getter @Setter private double personalBest;
    @Getter @Setter private boolean loaded;
    private static Player player;
    @Getter private Timer timer;

    public User(UUID uuid, String name) {
        this.uuid = uuid;
        this.name = name;

        load();
    }

    public void load() {
        new Thread(() -> {
            Document document = mongoCollection.find(Filters.eq("uuid", uuid.toString())).first();

            if (document == null) {
                save();
            }

            if (name == null) {
                name = document.getString("name");
            }

            try {
                block = document.getString("block");
                blocks = document.getList("blocks", String.class);
                if (!blocks.contains("SANDSTONE")) {
                    blocks.add("SANDSTONE");
                }
                coinsBalance = document.getInteger("coinsBalance");
                personalBest = document.getDouble("personalBest");
            } catch (NullPointerException exception) {

            }
            player = Main.getInstance().getServer().getPlayer(uuid);
            loaded = true;
        }).start();
    }

    public void save() {
        new Thread(() -> {
            Document document = new Document()
                    .append("uuid", uuid.toString())
                    .append("name", name)
                    .append("block", block == null ? "SANDSTONE" : block)
                    .append("blocks", blocks)
                    .append("coinsBalance", coinsBalance)
                    .append("personalBest", personalBest);
            mongoCollection.replaceOne(Filters.eq("uuid", uuid.toString()), document, new ReplaceOptions().upsert(true));
        }).start();
    }

    public static Player getPlayer() {
        player = Main.getInstance().getServer().getPlayer(uuid);

        return player;
    }

    public static User getByUUID(UUID uuid) {
        if (users.containsKey(uuid)) {
            return users.get(uuid);
        }

        return null;
    }

    public void startTimer() {
        if (this.timer == null) {
            this.timer = new Timer(Main.getInstance());
        }
    }

    public void stopTimer() {
        if (this.timer != null) {
            this.timer.cancel();
            this.timer = null;
        }
    }

    public double getElapsedTime() {
        return this.timer.getElapsedTime();
    }

    public boolean hasCoins(int coins) {
        return coinsBalance >= coins;
    }

    public void setCoinsBalance(int coins) {
        coinsBalance = coins;
    }

    public void addCoinsBalance(int coins) {
        coinsBalance += coins;
    }

    public void removeCoinsBalance(int coins) {
        this.coinsBalance = Math.max(coinsBalance-coins, 0);
    }

    public int getCoinsBalance() {
        return coinsBalance;
    }
}

class Timer extends BukkitRunnable {

    private Main main;

    private double elapsedTime = 0.00D;

    public Timer(Main main) {
        this.main = main;

        this.runTaskTimer(main, 0, 1);
    }

    public void run() {
        this.elapsedTime += 0.05;
    }

    public double getElapsedTime() {
        return elapsedTime;
    }
}