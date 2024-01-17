package fr.itsbursty.tntrunplugin.commands;

import fr.itsbursty.tntrunplugin.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ToggleLaunching implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player)sender;
            if(Main.gameCanStart){
                Main.gameCanStart = false;
                Main.gameStartIn = Main.timeBeforeBegin;
                player.sendMessage(ChatColor.GOLD + "[TNTRun] " + ChatColor.GREEN + "Vous avez empêché le jeu de démarré !");
            }else{
                Main.gameCanStart = true;
                player.sendMessage(ChatColor.GOLD + "[TNTRun] " + ChatColor.GREEN + "Le jeu peut maintenant démarré !");
            }
        }
        return true;
    }
}
