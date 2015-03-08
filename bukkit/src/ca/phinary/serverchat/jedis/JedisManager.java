package ca.phinary.serverchat.jedis;

import ca.phinary.serverchat.MessageType;
import ca.phinary.serverchat.ServerChat;
import ca.phinary.serverchat.jedis.JedisListener;
import org.bukkit.entity.Player;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class JedisManager
{
    private ServerChat _plugin;
    private JedisPool _jedisPool;
    private Jedis _jedisPublisher;

    public JedisManager(ServerChat plugin)
    {
        _plugin = plugin;

        _jedisPool = new JedisPool("localhost");
        _jedisPublisher = _jedisPool.getResource();
        _jedisPublisher.getClient().setTimeoutInfinite();

        startSubscriber();
    }

    private void startSubscriber()
    {
        final Jedis jedis = _jedisPool.getResource();

        _plugin.getServer().getScheduler().runTaskAsynchronously(_plugin, new Runnable()
        {
            @Override
            public void run()
            {
                JedisListener listener = new JedisListener(_plugin);
                jedis.psubscribe(listener, "serverchat.*");
            }
        });
    }

    public void publish(final MessageType messageType, final String message)
    {
        _plugin.getServer().getScheduler().runTaskAsynchronously(_plugin, new Runnable()
        {
            @Override
            public void run()
            {
                _jedisPublisher.publish(messageType.getChannel(), message);
            }
        });
    }
}
