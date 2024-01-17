package fr.itsbursty.tntrunplugin.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

public class CancelTntExplosion implements Listener {
    @EventHandler
    public void cancelExplosion(EntityExplodeEvent e){
        e.setCancelled(true);
    }
}
