package fr.itsbursty.tntrunplugin.listeners;

import fr.itsbursty.tntrunplugin.Main;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class CancelDamage implements Listener {
    @EventHandler
    public void onDamage(EntityDamageEvent e){
        if(e.getEntity() instanceof Player){
            if(e.getCause() == EntityDamageEvent.DamageCause.FALL){
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerDamageOtherPlayer(EntityDamageByEntityEvent e){
        if(e.getEntity().getType() == EntityType.PLAYER){
            e.setDamage(0);
            if(!Main.gameStarted){
                e.setCancelled(true);
            }else if(!Main.togglePvpMode) {
                e.setCancelled(true);
            }
        }
    }
}