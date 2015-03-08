package ca.phinary.serverchat.jedis;

import ca.phinary.serverchat.MessageType;
import ca.phinary.serverchat.ServerChat;
import org.bukkit.Bukkit;
import redis.clients.jedis.JedisPubSub;

public class JedisListener extends JedisPubSub
{
    private ServerChat _plugin;

    public JedisListener(ServerChat plugin)
    {
        _plugin = plugin;
    }

    @Override
    public void onMessage(String channel, final String message)
    {

    }

    @Override
    public void onPMessage(String pattern, String channel, final String message)
    {
        final MessageType messageType = MessageType.fromChannel(channel);

        // Ignore messages from channels we dont know about
        if (messageType == null || !messageType.shouldDisplay())
            return;

        _plugin.getServer().getScheduler().runTask(_plugin, new Runnable()
        {
            @Override
            public void run()
            {
                Bukkit.broadcastMessage(messageType.getChatPrefix() + message);
            }
        });
    }

    @Override
    public void onSubscribe(String s, int i)
    {

    }

    @Override
    public void onUnsubscribe(String s, int i)
    {

    }

    @Override
    public void onPUnsubscribe(String s, int i)
    {

    }

    @Override
    public void onPSubscribe(String s, int i)
    {

    }
}
