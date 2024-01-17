package fr.itsbursty.tntrunplugin.listeners;

import fr.itsbursty.tntrunplugin.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class OnJoin implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        Player player = e.getPlayer();
        Main.playersInGame += 1;
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + player.getDisplayName() + " vient de rejoindre (" + Main.playersInGame + ")");
        for(Player p : Bukkit.getServer().getOnlinePlayers()){
            p.sendMessage(ChatColor.GOLD + "[TNTRun] " + ChatColor.GREEN + player.getDisplayName() + " vient de rejoindre [" + Main.playersInGame + "/" + Main.minPlayer + "]");
        }
        if(player.isOp()){
            ItemStack gameManagerCompass = new ItemStack(Material.COMPASS);
            ItemMeta gameManagerCompassMeta = gameManagerCompass.getItemMeta();

            gameManagerCompassMeta.setDisplayName(ChatColor.BLUE + "Game Manager");
            ArrayList<String> loreArray = new ArrayList<>();
            loreArray.add(ChatColor.GOLD + "Paramètrez votre serveur mini-jeu ici !");
            gameManagerCompassMeta.setLore(loreArray);
            gameManagerCompass.setItemMeta(gameManagerCompassMeta);
            player.getInventory().setItem(4, gameManagerCompass);
        }else{
            player.setGameMode(GameMode.ADVENTURE);
            player.teleport(Main.spawnLocation);
        }
        if(Main.gameStarted){
            player.setGameMode(GameMode.SPECTATOR);
        }
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e){
        Player player = e.getPlayer();
        Main.playersInGame -= 1;
        Main.gameStartIn = Main.timeBeforeBegin;
        Main.gameCanStart = false;
        Bukkit.getConsoleSender().sendMessage(ChatColor.RED + player.getDisplayName() + " vient de quitter (" + Main.playersInGame + ")");
        for(Player p : Bukkit.getServer().getOnlinePlayers()){
            p.sendMessage(ChatColor.GOLD + "[TNTRun] " + ChatColor.RED + player.getDisplayName() + " vient de quitter [" + Main.playersInGame + "/" + Main.minPlayer + "]");
        }
        if(Main.gameStarted){
            Main.playersInGameList.remove(player);
            Main.playerAlive -= 1;
        }
    }
}
