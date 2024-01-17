package fr.itsbursty.tntrunplugin.managers;

import fr.itsbursty.tntrunplugin.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class InventoryManager {

    public static Inventory invGameManager;

    public static void openGameManagerMenu(Player player) {
        invGameManager = Bukkit.createInventory(null, 27, ChatColor.GOLD + "Gestion du serveur");
        invGameManager.clear();

        ItemStack launchGame = new ItemStack(Material.TNT);
        ItemStack playerManager = new ItemStack(Material.REDSTONE_BLOCK);
        ItemStack togglePVP = new ItemStack(Material.IRON_SWORD);

        ItemMeta launchGameMeta = launchGame.getItemMeta();
        if(Main.gameCanStart){
            launchGameMeta.setDisplayName(ChatColor.RED + "Arrêter le jeu !");
        }else{
            launchGameMeta.setDisplayName(ChatColor.GREEN + "Lancer le jeu !");
        }
        ArrayList<String> launchGameLore = new ArrayList<>();
        launchGameLore.add(ChatColor.GOLD + "Lancer le TNT Run avec les paramètres actuels");
        launchGameMeta.setLore(launchGameLore);
        launchGame.setItemMeta(launchGameMeta);

        ItemMeta playerManagerMeta = playerManager.getItemMeta();
        playerManagerMeta.setDisplayName(ChatColor.RED + "Joueurs");
        ArrayList<String> playerManagerLore = new ArrayList<>();
        playerManagerLore.add(ChatColor.GOLD + "Bannir/Exclure des joueurs");
        playerManagerMeta.setLore(playerManagerLore);
        playerManager.setItemMeta(playerManagerMeta);

        ItemMeta togglePVPMeta = togglePVP.getItemMeta();
        if(!Main.togglePvpMode){
            togglePVPMeta.setDisplayName(ChatColor.GREEN + "Activer le PVP");
        }else{
            togglePVPMeta.setDisplayName(ChatColor.RED + "Désactiver le PVP");
        }
        ArrayList<String> togglePVPLore = new ArrayList<>();
        togglePVPLore.add(ChatColor.GOLD + "Activer ou désactiver le PVP");
        togglePVPLore.add(ChatColor.GOLD + "Les joueurs pourront se taper");
        togglePVPMeta.setLore(togglePVPLore);
        togglePVP.setItemMeta(togglePVPMeta);

        invGameManager.setItem(10, launchGame);
        invGameManager.setItem(12, playerManager);
        invGameManager.setItem(14, togglePVP);
        player.openInventory(invGameManager);
    }

    public static Inventory invPlayerManager;

    public static void openPlayerManagerMenu(Player player) {
        invPlayerManager = Bukkit.createInventory(null, 27, ChatColor.GOLD + "Gestion des joueurs");

        GameManager.refreshPlayerManagerMenu();

        player.openInventory(invPlayerManager);
    }

    public static Inventory invConfirmMenu;

    public static void openConfirmMenu(Player player, String question){
        invConfirmMenu = Bukkit.createInventory(null, 36, ChatColor.GOLD + "Confirmer");
        invConfirmMenu.clear();

        ItemStack confirmMsg = new ItemStack((Material.GLASS));
        ItemStack confirmButton = new ItemStack(Material.EMERALD_BLOCK);
        ItemStack denyButton = new ItemStack(Material.REDSTONE_BLOCK);

        ItemMeta confirmMsgMeta = confirmMsg.getItemMeta();
        ItemMeta confirmButtonMeta = confirmButton.getItemMeta();
        ItemMeta denyButtonMeta = denyButton.getItemMeta();

        confirmMsgMeta.setDisplayName(ChatColor.GOLD + question);
        confirmButtonMeta.setDisplayName(ChatColor.GREEN + "Confirmer");
        denyButtonMeta.setDisplayName(ChatColor.RED + "Annuler");

        confirmMsg.setItemMeta(confirmMsgMeta);
        confirmButton.setItemMeta(confirmButtonMeta);
        denyButton.setItemMeta(denyButtonMeta);

        invConfirmMenu.setItem(13, confirmMsg);
        invConfirmMenu.setItem(21, confirmButton);
        invConfirmMenu.setItem(23, denyButton);

        player.openInventory(invConfirmMenu);
    }
}
