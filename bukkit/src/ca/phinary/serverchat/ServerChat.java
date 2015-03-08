package ca.phinary.serverchat;

import ca.phinary.serverchat.jedis.JedisManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class ServerChat extends JavaPlugin implements Listener
{
    private JedisManager _jedis;

    @Override
    public void onEnable()
    {
        System.out.println("Server Chat Loading...");

        _jedis = new JedisManager(this);
        getServer().getPluginManager().registerEvents(this, this);

        System.out.println("Server Chat Enabled!");
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event)
    {
        String formattedMessage = event.getPlayer().getName() + " > " + event.getMessage();
        _jedis.publish(MessageType.PlayerMessage, formattedMessage);
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event)
    {
        _jedis.publish(MessageType.PlayerStatusUpdate, event.getDeathMessage());
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event)
    {
        _jedis.publish(MessageType.PlayerStatusUpdate, event.getPlayer().getName() + " has joined server.");
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event)
    {
        _jedis.publish(MessageType.PlayerStatusUpdate, event.getPlayer().getName() + " has quit server.");
    }

}
