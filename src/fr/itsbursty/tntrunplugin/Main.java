package fr.itsbursty.tntrunplugin;

import com.mysql.jdbc.Connection;
import fr.itsbursty.tntrunplugin.commands.ToggleLaunching;
import fr.itsbursty.tntrunplugin.listeners.*;
import fr.itsbursty.tntrunplugin.managers.GameManager;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class Main extends JavaPlugin {

    private Connection connection;
    public String host, db, user, pass;
    public int port;

    public static boolean togglePvpMode;
    public static int timeBeforeBegin;
    public static int gameStartIn;
    public static boolean gameStarted;
    public static int playersInGame;
    public static boolean gameCanStart;
    public static int minPlayer;
    public static int playerAlive;
    public static Player winner;
    public static Location spawnLocation;
    public static ArrayList<Player> playersInGameList = new ArrayList<>();
    public static ArrayList<Location> playerGameSpawn;

    public static Main instance;

    @Override
    public void onEnable() {
        instance = this;

        spawnLocation = new Location(getServer().getWorld("world"), -1143, 62, 331);

        playerGameSpawn = new ArrayList<>();
        playerGameSpawn.add(new Location(getServer().getWorld("world"), -1160, 44, 325));
        playerGameSpawn.add(new Location(getServer().getWorld("world"), -1153, 44, 321));
        playerGameSpawn.add(new Location(getServer().getWorld("world"), -1142, 44, 318));
        playerGameSpawn.add(new Location(getServer().getWorld("world"), -1137, 44, 324));
        playerGameSpawn.add(new Location(getServer().getWorld("world"), -1130, 44, 331));
        playerGameSpawn.add(new Location(getServer().getWorld("world"), -1132, 44, 339));
        playerGameSpawn.add(new Location(getServer().getWorld("world"), -1140, 44, 342));
        playerGameSpawn.add(new Location(getServer().getWorld("world"), -1150, 44, 344));
        playerGameSpawn.add(new Location(getServer().getWorld("world"), -1155, 44, 334));

        togglePvpMode = false;
        playerAlive = getServer().getOnlinePlayers().size();
        timeBeforeBegin = 11;
        minPlayer = 2;
        gameCanStart = false;
        gameStartIn = 11;
        gameStarted = false;
        playersInGame = getServer().getOnlinePlayers().size();
        winner = null;

        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "Le plugin s'est correctement lancé !");
        getServer().getPluginManager().registerEvents(new OnJoin(), this);
        getServer().getPluginManager().registerEvents(new OnPlayerMove(), this);
        getServer().getPluginManager().registerEvents(new CancelDamage(), this);
        getServer().getPluginManager().registerEvents(new GameManagerMenu(), this);
        getServer().getPluginManager().registerEvents(new CancelRain(), this);
        getServer().getPluginManager().registerEvents(new CancelTntExplosion(), this);
        getCommand("launchgame").setExecutor(new ToggleLaunching());
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "Nombre de joueur en ligne : " + getServer().getOnlinePlayers().size());
        getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                if (playersInGame >= minPlayer && !gameStarted && gameCanStart) {
                    gameStartIn -= 1;
                    for (Player player : getServer().getOnlinePlayers()) {
                        if(gameStartIn > 0){
                            //player.sendMessage(ChatColor.GREEN + "Lancement dans : " + ChatColor.GOLD + gameStartIn);
                            PlayerConnection playerCon = ((CraftPlayer)player.getPlayer()).getHandle().playerConnection;
                            IChatBaseComponent text = IChatBaseComponent.ChatSerializer.a("{'text' : ' " + ChatColor.GOLD + gameStartIn + " '}");
                            PacketPlayOutTitle titlePacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, text, 0, 20, 0);
                            playerCon.sendPacket(titlePacket);
                        }
                        if(gameStartIn == 0){
                            PlayerConnection playerCon = ((CraftPlayer)player.getPlayer()).getHandle().playerConnection;
                            PacketPlayOutTitle titlePacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, IChatBaseComponent.ChatSerializer.a("{'text':' " + ChatColor.GREEN + "C'est parti !" + " '}}"), 0, 20, 0);
                            playerCon.sendPacket(titlePacket);
                        }
                    }
                    if (gameStartIn == 0) {
                        new BukkitRunnable(){
                            @Override
                            public void run() {
                                gameStarted = true;
                                gameStartIn = timeBeforeBegin;
                            }
                        }.runTaskLater(instance, 40L);
                        GameManager.launchGame();
                    }
                }
            }
        }, 0L, 20L);
        getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                for(Player p : playersInGameList){
                    if(p.getGameMode() != GameMode.SPECTATOR){
                        if(p.getLocation().subtract(0, 1, 0).getBlock().getType() == Material.SAND){
                            Block block = p.getLocation().subtract(0, 1, 0).getBlock();
                            deleteSand(block);
                        }else if(p.getLocation().subtract(0, 1, 0).getBlock().getType() == Material.AIR) {
                            if (p.getLocation().subtract(1, 1, 0).getBlock().getType() == Material.SAND) {
                                Block block = p.getLocation().subtract(1, 1, 0).getBlock();
                                deleteSand(block);
                            } else if (p.getLocation().subtract(-1, 1, 0).getBlock().getType() == Material.SAND) {
                                Block block = p.getLocation().subtract(-1, 1, 0).getBlock();
                                deleteSand(block);
                            } else if (p.getLocation().subtract(0, 1, 1).getBlock().getType() == Material.SAND) {
                                Block block = p.getLocation().subtract(0, 1, 1).getBlock();
                                deleteSand(block);
                            } else if (p.getLocation().subtract(0, 1, -1).getBlock().getType() == Material.SAND) {
                                Block block = p.getLocation().subtract(0, 1, -1).getBlock();
                                deleteSand(block);
                            } else if (p.getLocation().subtract(1, 1, -1).getBlock().getType() == Material.SAND) {
                                Block block = p.getLocation().subtract(1, 1, -1).getBlock();
                                deleteSand(block);
                            } else if (p.getLocation().subtract(-1, 1, -1).getBlock().getType() == Material.SAND) {
                                Block block = p.getLocation().subtract(-1, 1, -1).getBlock();
                                deleteSand(block);
                            } else if (p.getLocation().subtract(-1, 1, 1).getBlock().getType() == Material.SAND) {
                                Block block = p.getLocation().subtract(-1, 1, 1).getBlock();
                                deleteSand(block);
                            } else if (p.getLocation().subtract(1, 1, 1).getBlock().getType() == Material.SAND) {
                                Block block = p.getLocation().subtract(1, 1, 1).getBlock();
                                deleteSand(block);
                            }
                        }
                    }
                }
            }
        }, 0L, 2L);
    }

    public void deleteSand(Block block){
        new BukkitRunnable(){
            @Override
            public void run() {
                if(Main.gameStarted){
                    block.setType(Material.AIR);
                    block.getLocation().subtract(0, 1, 0).getBlock().setType(Material.AIR);
                }
            }
        }.runTaskLater(instance, 5L);
    }

    public void mysqlSetup () {

    }

    @Override
    public void onDisable () {
        getServer().getConsoleSender().sendMessage(ChatColor.RED + "Le plugin s'est arrêté !");
    }
}