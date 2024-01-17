package fr.itsbursty.tntrunplugin.managers;

import fr.itsbursty.tntrunplugin.Main;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Random;

public class GameManager {

    public static void launchGame(){
        Main.playersInGameList.clear();
        Random random = new Random();
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Le jeu va commencer !");
        for(Player player : Bukkit.getServer().getOnlinePlayers()){
            player.sendMessage(ChatColor.GOLD + "[TNTRun] " + ChatColor.GREEN + "Le jeu commence !");
            player.teleport(Main.playerGameSpawn.get(random.nextInt(9)));
            player.setGameMode(GameMode.ADVENTURE);
            player.getInventory().clear();
            player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 1));
            Main.playersInGameList.add(player);
        }

        Main.playerAlive = Bukkit.getServer().getOnlinePlayers().size();
    }

    public static void gameFinished(){
        Main.winner.sendMessage(ChatColor.GOLD + "[TNTRun] " + ChatColor.GREEN + "Vous avez gagné !");
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Partie terminée !");
        for(Player p : Bukkit.getServer().getOnlinePlayers()){
            p.sendMessage(ChatColor.GOLD + "[TNTRun] " + ChatColor.GREEN + Main.winner.getDisplayName() + " vient de gagner la partie !");
            p.setGameMode(GameMode.ADVENTURE);
            p.teleport(Main.spawnLocation);
            p.getActivePotionEffects().clear();
            if(p.isOp()){
                ItemStack gameManagerCompass = new ItemStack(Material.COMPASS);
                ItemMeta gameManagerCompassMeta = gameManagerCompass.getItemMeta();

                gameManagerCompassMeta.setDisplayName(ChatColor.BLUE + "Game Manager");
                ArrayList<String> loreArray = new ArrayList<>();
                loreArray.add(ChatColor.GOLD + "Paramètrez votre serveur mini-jeu ici !");
                gameManagerCompassMeta.setLore(loreArray);
                gameManagerCompass.setItemMeta(gameManagerCompassMeta);
                p.getInventory().setItem(4, gameManagerCompass);
            }
        }
        GameManager.resetMap();
        Main.gameStartIn = Main.timeBeforeBegin;
        Main.gameStarted = false;
        Main.gameCanStart = false;
    }

    public static void refreshPlayerManagerMenu(){
        InventoryManager.invPlayerManager.clear();

        for(Player p : Bukkit.getServer().getOnlinePlayers()){
            ItemStack playerSkull = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
            SkullMeta playerSkullMeta = (SkullMeta) playerSkull.getItemMeta();

            playerSkullMeta.setDisplayName(p.getDisplayName());
            ArrayList<String> playerSkullLore = new ArrayList<>();
            playerSkullLore.add(ChatColor.RED + "Clique droite bannir");
            playerSkullLore.add(ChatColor.RED + "Clique gauche exclure");
            playerSkullMeta.setLore(playerSkullLore);
            playerSkullMeta.setOwner(p.getName());
            playerSkull.setItemMeta(playerSkullMeta);

            InventoryManager.invPlayerManager.addItem(playerSkull);
        }
        ItemStack refresh = new ItemStack(Material.ARROW);
        ItemMeta refreshMeta = refresh.getItemMeta();

        refreshMeta.setDisplayName(ChatColor.GOLD + "Rafraichir");
        refresh.setItemMeta(refreshMeta);

        InventoryManager.invPlayerManager.setItem(26, refresh);
    }

    public static void resetMap(){
        //Etage du haut
        Location loc = new Location(Bukkit.getWorld("world"), -1163, 42, 312);
        for(int i = 0; i != 41; i++){
            for(int k = 0; k != 40; k++){
                loc.setX(-1163 + k);
                loc.getBlock().setType(Material.TNT);
            }
            loc.setZ(312 + i);
        }
        Location loc2 = new Location(Bukkit.getWorld("world"), -1163, 43, 312);
        for(int i = 0; i != 41; i++){
            for(int k = 0; k != 40; k++){
                loc2.setX(-1163 + k);
                loc2.getBlock().setType(Material.SAND);
            }
            loc2.setZ(312 + i);
        }

        //Etage du milieu
        Location loc3 = new Location(Bukkit.getWorld("world"), -1163, 22, 312);
        for(int i = 0; i != 41; i++){
            for(int k = 0; k != 40; k++){
                loc3.setX(-1163 + k);
                loc3.getBlock().setType(Material.TNT);
            }
            loc3.setZ(312 + i);
        }
        Location loc4 = new Location(Bukkit.getWorld("world"), -1163, 23, 312);
        for(int i = 0; i != 41; i++){
            for(int k = 0; k != 40; k++){
                loc4.setX(-1163 + k);
                loc4.getBlock().setType(Material.SAND);
            }
            loc4.setZ(312 + i);
        }

        //Etage du bas
        Location loc5 = new Location(Bukkit.getWorld("world"), -1163, 2, 312);
        for(int i = 0; i != 41; i++){
            for(int k = 0; k != 40; k++){
                loc5.setX(-1163 + k);
                loc5.getBlock().setType(Material.TNT);
            }
            loc5.setZ(312 + i);
        }
        Location loc6 = new Location(Bukkit.getWorld("world"), -1163, 3, 312);
        for(int i = 0; i != 41; i++){
            for(int k = 0; k != 40; k++){
                loc6.setX(-1163 + k);
                loc6.getBlock().setType(Material.SAND);
            }
            loc6.setZ(312 + i);
        }
    }
}
