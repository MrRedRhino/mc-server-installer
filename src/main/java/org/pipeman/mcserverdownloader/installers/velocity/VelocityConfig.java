package org.pipeman.mcserverdownloader.installers.velocity;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class VelocityConfig {
    public int slotCount = 100;
    public final String key;

    public void writeConfig() {
        try {
            FileWriter myWriter = new FileWriter("velocity.toml");
            myWriter.write(getConfigText());
            myWriter.close();
        } catch (IOException ignored) {}
    }
    public VelocityConfig() {
        key = genSecret();
    }

    private String getConfigText() {
        return String.format("# Config version. Do not change this\n" +
                "config-version = \"1.0\"\n" +
                "# What port should the proxy be bound to? By default, we'll bind to all addresses on port 25577.\n" +
                "bind = \"0.0.0.0:25565\"\n" +
                "# What should be the MOTD? This gets displayed when the player adds your server to\n" +
                "# their server list. Legacy color codes and JSON are accepted.\n" +
                "motd = \"&#09add3A Velocity Server\"\n" +
                "# What should we display for the maximum number of players? (Velocity does not support a cap\n" +
                "# on the number of players online.)\n" +
                "show-max-players = %s\n" +
                "# Should we authenticate players with Mojang? By default, this is on.\n" +
                "online-mode = true\n" +
                "# If client's ISP/AS sent from this proxy is different from the one from Mojang's\n" +
                "# authentication server, the player is kicked. This disallows some VPN and proxy\n" +
                "# connections but is a weak form of protection.\n" +
                "prevent-client-proxy-connections = false\n" +
                "# Should we forward IP addresses and other data to backend servers?\n" +
                "# Available options:\n" +
                "# - \"none\":        No forwarding will be done. All players will appear to be connecting\n" +
                "#                  from the proxy and will have offline-mode UUIDs.\n" +
                "# - \"legacy\":      Forward player IPs and UUIDs in a BungeeCord-compatible format. Use this\n" +
                "#                  if you run servers using Minecraft 1.12 or lower.\n" +
                "# - \"bungeeguard\": Forward player IPs and UUIDs in a format supported by the BungeeGuard\n" +
                "#                  plugin. Use this if you run servers using Minecraft 1.12 or lower, and are\n" +
                "#                  unable to implement network level firewalling (on a shared host).\n" +
                "# - \"modern\":      Forward player IPs and UUIDs as part of the login process using\n" +
                "#                  Velocity's native forwarding. Only applicable for Minecraft 1.13 or higher.\n" +
                "player-info-forwarding-mode = \"modern\"\n" +
                "# If you are using modern or BungeeGuard IP forwarding, configure a unique secret here.\n" +
                "forwarding-secret = \"%s\"\n" +
                "# Announce whether or not your server supports Forge. If you run a modded server, we\n" +
                "# suggest turning this on.\n" +
                "# \n" +
                "# If your network runs one modpack consistently, consider using ping-passthrough = \"mods\"\n" +
                "# instead for a nicer display in the server list.\n" +
                "announce-forge = false\n" +
                "# If enabled (default is false) and the proxy is in online mode, Velocity will kick\n" +
                "# any existing player who is online if a duplicate connection attempt is made.\n" +
                "kick-existing-players = false\n" +
                "# Should Velocity pass server list ping requests to a backend server?\n" +
                "# Available options:\n" +
                "# - \"disabled\":    No pass-through will be done. The velocity.toml and server-icon.png\n" +
                "#                  will determine the initial server list ping response.\n" +
                "# - \"mods\":        Passes only the mod list from your backend server into the response.\n" +
                "#                  The first server in your try list (or forced host) with a mod list will be\n" +
                "#                  used. If no backend servers can be contacted, Velocity won't display any\n" +
                "#                  mod information.\n" +
                "# - \"description\": Uses the description and mod list from the backend server. The first\n" +
                "#                  server in the try (or forced host) list that responds is used for the\n" +
                "#                  description and mod list.\n" +
                "# - \"all\":         Uses the backend server's response as the proxy response. The Velocity\n" +
                "#                  configuration is used if no servers could be contacted.\n" +
                "ping-passthrough = \"DISABLED\"\n" +
                "\n" +
                "[servers]\n" +
                "\t# Configure your servers here. Each key represents the server's name, and the value\n" +
                "\t# represents the IP address of the server to connect to.\n" +
                "\tlobby = \"127.0.0.1:30066\"\n" +
                "\tfactions = \"127.0.0.1:30067\"\n" +
                "\tminigames = \"127.0.0.1:30068\"\n" +
                "\t# In what order we should try servers when a player logs in or is kicked from a server.\n" +
                "\ttry = [\"lobby\"]\n" +
                "\n" +
                "[forced-hosts]\n" +
                "\t# Configure your forced hosts here.\n" +
                "\t\"lobby.example.com\" = [\"lobby\"]\n" +
                "\t\"factions.example.com\" = [\"factions\"]\n" +
                "\t\"minigames.example.com\" = [\"minigames\"]\n" +
                "\n" +
                "[advanced]\n" +
                "\t# How large a Minecraft packet has to be before we compress it. Setting this to zero will\n" +
                "\t# compress all packets, and setting it to -1 will disable compression entirely.\n" +
                "\tcompression-threshold = 256\n" +
                "\t# How much compression should be done (from 0-9). The default is -1, which uses the\n" +
                "\t# default level of 6.\n" +
                "\tcompression-level = -1\n" +
                "\t# How fast (in milliseconds) are clients allowed to connect after the last connection? By\n" +
                "\t# default, this is three seconds. Disable this by setting this to 0.\n" +
                "\tlogin-ratelimit = 3000\n" +
                "\t# Specify a custom timeout for connection timeouts here. The default is five seconds.\n" +
                "\tconnection-timeout = 5000\n" +
                "\t# Specify a read timeout for connections here. The default is 30 seconds.\n" +
                "\tread-timeout = 30000\n" +
                "\t# Enables compatibility with HAProxy's PROXY protocol. If you don't know what this is for, then\n" +
                "\t# don't enable it.\n" +
                "\thaproxy-protocol = false\n" +
                "\t# Enables TCP fast open support on the proxy. Requires the proxy to run on Linux.\n" +
                "\ttcp-fast-open = false\n" +
                "\t# Enables BungeeCord plugin messaging channel support on Velocity.\n" +
                "\tbungee-plugin-message-channel = true\n" +
                "\t# Shows ping requests to the proxy from clients.\n" +
                "\tshow-ping-requests = false\n" +
                "\t# By default, Velocity will attempt to gracefully handle situations where the user unexpectedly\n" +
                "\t# loses connection to the server without an explicit disconnect message by attempting to fall the\n" +
                "\t# user back, except in the case of read timeouts. BungeeCord will disconnect the user instead. You\n" +
                "\t# can disable this setting to use the BungeeCord behavior.\n" +
                "\tfailover-on-unexpected-server-disconnect = true\n" +
                "\t# Declares the proxy commands to 1.13+ clients.\n" +
                "\tannounce-proxy-commands = true\n" +
                "\t# Enables the logging of commands\n" +
                "\tlog-command-executions = false\n" +
                "\n" +
                "[query]\n" +
                "\t# Whether to enable responding to GameSpy 4 query responses or not.\n" +
                "\tenabled = false\n" +
                "\t# If query is enabled, on what port should the query protocol listen on?\n" +
                "\tport = 25577\n" +
                "\t# This is the map name that is reported to the query services.\n" +
                "\tmap = \"Velocity\"\n" +
                "\tshow-plugins = false\n" +
                "\n", slotCount, key);
    }

    private String genSecret() {
        int leftLimit = 48;
        int rightLimit = 122;
        int targetStringLength = 8;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
