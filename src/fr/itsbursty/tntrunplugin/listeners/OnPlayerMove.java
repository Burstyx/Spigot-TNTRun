package fr.itsbursty.tntrunplugin.listeners;

import fr.itsbursty.tntrunplugin.Main;
import fr.itsbursty.tntrunplugin.managers.GameManager;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class OnPlayerMove implements Listener {

    @EventHandler
    public void DestroySandUnderPlayer(PlayerMoveEvent e){
        Player player = e.getPlayer();
        if(player.getLocation().getY() <= 0){
            if(player.getGameMode() != GameMode.SPECTATOR){
                if(Main.gameStarted){
                    Main.playerAlive -= 1;
                    Main.playersInGameList.remove(player);
                    for(Player p : Bukkit.getServer().getOnlinePlayers()){
                        p.sendMessage(ChatColor.DARK_RED + player.getDisplayName() + ChatColor.RED + " vient de mourir !");
                    }
                    player.setGameMode(GameMode.SPECTATOR);
                    player.teleport(Main.spawnLocation);
                    if(Main.playerAlive == 1){
                        Main.winner = Main.playersInGameList.get(Main.playersInGameList.size() - 1);
                        GameManager.gameFinished();
                    }
                }
            }else if(Main.gameStarted){
                player.teleport(Main.spawnLocation);
                player.sendMessage(ChatColor.GOLD + "[TNTRun] " + ChatColor.RED + "Tu ne peux pas quitter la zone de jeu !");
            }
        }
        if(player.getLocation().getZ() <= 268.0 || player.getLocation().getX() <= -1207.0 || player.getLocation().getZ() >= 400.0 || player.getLocation().getX() >= -1077.0 || player.getLocation().getY() >= 120){
            if(Main.gameStarted && player.getGameMode() == GameMode.SPECTATOR){
                player.teleport(Main.spawnLocation);
                player.sendMessage(ChatColor.GOLD + "[TNTRun] " + ChatColor.RED + "Tu ne peux pas quitter la zone de jeu !");
            }
        }
    }
}