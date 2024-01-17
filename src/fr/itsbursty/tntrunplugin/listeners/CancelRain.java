package fr.itsbursty.tntrunplugin.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;

public class CancelRain implements Listener {
    @EventHandler
    public void cancelRain(WeatherChangeEvent e){
        e.setCancelled(true);
    }
}
