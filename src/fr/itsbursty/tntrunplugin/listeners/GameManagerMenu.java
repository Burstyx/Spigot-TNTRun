package fr.itsbursty.tntrunplugin.listeners;

import fr.itsbursty.tntrunplugin.Main;
import fr.itsbursty.tntrunplugin.managers.GameManager;
import fr.itsbursty.tntrunplugin.managers.InventoryManager;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Objects;

public class GameManagerMenu implements Listener {

    public Player chosenPlayer;

    @EventHandler
    public void useGameManagerCompass(InventoryClickEvent e){
        Player player = (Player) e.getWhoClicked();

        if(Objects.equals(player.getOpenInventory().getTitle(), ChatColor.GOLD + "Gestion du serveur")){
            if(e.getCurrentItem().getType() == Material.TNT){
                if(Main.gameCanStart){
                    e.setCancelled(true);
                    Main.gameCanStart = false;
                    Main.gameStartIn = Main.timeBeforeBegin;
                    player.sendMessage(ChatColor.GOLD + "[TNTRun] " + ChatColor.GREEN + "Le jeu a été arrêté !");
                    player.closeInventory();
                }else {
                    e.setCancelled(true);
                    player.closeInventory();
                    if (Bukkit.getServer().getOnlinePlayers().size() >= Main.minPlayer) {
                        Main.gameCanStart = true;
                    } else {
                        player.sendMessage(ChatColor.GOLD + "[TNTRun] " + ChatColor.RED + "Il faut au moins " + Main.minPlayer + " joueurs pour lancer le jeu !");
                    }
                }
            }else if(e.getCurrentItem().getType() == Material.REDSTONE_BLOCK){
                e.setCancelled(true);
                InventoryManager.openPlayerManagerMenu(player);
            }else if(e.getCurrentItem().getType() == Material.IRON_SWORD){
                e.setCancelled(true);
                Main.togglePvpMode = !Main.togglePvpMode;
                InventoryManager.openGameManagerMenu(player);
            }
        }
        if(Objects.equals(player.getOpenInventory().getTitle(), ChatColor.GOLD + "Gestion des joueurs")){
            if(e.getCurrentItem().getType() == Material.SKULL_ITEM){
                e.setCancelled(true);
                chosenPlayer = Bukkit.getServer().getPlayer(e.getCurrentItem().getItemMeta().getDisplayName());
                if(e.getClick() == ClickType.RIGHT){
                    player.closeInventory();
                    if(!(Objects.equals(chosenPlayer.getDisplayName(), player.getDisplayName()))){
                        player.sendMessage(ChatColor.GOLD + chosenPlayer.getDisplayName() + ChatColor.RED + " a été banni du serveur !");
                        Bukkit.getBanList(BanList.Type.NAME).addBan(chosenPlayer.getDisplayName(), ChatColor.RED + "Vous avez été banni du serveur !", null, null);
                        chosenPlayer.kickPlayer(ChatColor.RED + "Vous avez été banni du serveur !");
                        GameManager.refreshPlayerManagerMenu();
                    }else{
                        player.sendMessage(ChatColor.RED + "Vous ne pouvez pas vous bannir vous même !");
                    }
                }
                if(e.getClick() == ClickType.LEFT){
                    player.closeInventory();
                    if(!Objects.equals(chosenPlayer.getDisplayName(), player.getDisplayName())){
                        player.sendMessage(ChatColor.GOLD + chosenPlayer.getDisplayName() + ChatColor.RED + " a été kick du serveur !");
                        chosenPlayer.kickPlayer(ChatColor.RED + "Vous avez été kick du serveur !");
                        GameManager.refreshPlayerManagerMenu();
                    }else{
                        player.sendMessage(ChatColor.RED + "Vous ne pouvez pas vous kick vous même !");
                    }
                }
            }
            if(e.getCurrentItem().getType() == Material.ARROW){
                e.setCancelled(true);
                GameManager.refreshPlayerManagerMenu();
            }
        }
        if(Objects.equals(player.getOpenInventory().getTitle(), ChatColor.GOLD + "Confirmer")){
            player.sendMessage("ok 1");
            if(e.getCurrentItem().getType() == Material.EMERALD_BLOCK){
                player.sendMessage("ok 2");
                e.setCancelled(true);
                if(!Objects.equals(chosenPlayer.getDisplayName(), player.getDisplayName())){

                }else{
                    player.sendMessage(ChatColor.RED + "Vous ne pouvez pas vous bannir vous même");
                }
            }else if(e.getCurrentItem().getType() == Material.REDSTONE_BLOCK){
                player.closeInventory();
                player.sendMessage(ChatColor.GOLD + "[]" + ChatColor.RED + "Requête annulé !");
            }
        }
    }

    @EventHandler
    public void openGameManagerCompass(PlayerInteractEvent e){
        Player player = e.getPlayer();

        if(player.getItemInHand().getType() == Material.COMPASS && (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK)){
            InventoryManager.openGameManagerMenu(player);
        }
    }
}
