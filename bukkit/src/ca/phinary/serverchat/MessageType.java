package ca.phinary.serverchat;

import org.bukkit.ChatColor;

public enum MessageType
{
    ClientMessage("serverchat.clientmessage", ChatColor.WHITE.toString(), true),
    PlayerMessage("serverchat.playermessage", "", false),
    ClientStatusUpdate("serverchat.clientstatusupdate", ChatColor.AQUA.toString(), true),
    PlayerStatusUpdate("serverchat.playerstatusupdate", "", false),
    ErrorMessage("serverchat.errormessage", ChatColor.RED.toString(), true);

    private String _channel;
    private String _chatPrefix;
    private boolean _shouldDisplay;

    MessageType(String channel, String chatPrefix, boolean shouldDisplay)
    {
        _channel = channel;
        _chatPrefix = chatPrefix;
        _shouldDisplay = shouldDisplay;
    }

    @Override
    public String toString()
    {
        return getChannel();
    }

    public String getChannel()
    {
        return _channel;
    }

    public String getChatPrefix()
    {
        return _chatPrefix;
    }

    public boolean shouldDisplay()
    {
        return _shouldDisplay;
    }

    public static MessageType fromChannel(String channel)
    {
        for (MessageType type : MessageType.values())
        {
            if (type.getChannel().equalsIgnoreCase(channel))
                return type;
        }
        return null;
    }
}
