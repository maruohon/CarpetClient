package carpetclient.pluginchannel;


import carpetclient.coders.EDDxample.VillageMarker;
import carpetclient.rules.CarpetRules;
import com.google.common.collect.ImmutableList;
import com.mumfrey.liteloader.core.ClientPluginChannels;
import com.mumfrey.liteloader.core.PluginChannels.ChannelPolicy;
import net.minecraft.network.PacketBuffer;

/*
Plugin channel class to implement a client server communication between carpet client and carpet server.
 */
public class CarpetPluginChannel {
    public static final String CARPET_CHANNEL_NAME = "CarpetClient";
    public static final ImmutableList CARPET_PLUGIN_CHANNEL = ImmutableList.of(CARPET_CHANNEL_NAME);
    
    public static final int GUI_ALL_DATA = 0;
    public static final int RULE_REQUEST = 1;
    public static final int VILLAGE_MARKERS = 2;

    /**
     * Packate receiver method to handle incoming messages.
     *
     * @param channel incoming channel or packet name.
     * @param data    incoming data from server.
     */
    public static void packatReceiver(String channel, PacketBuffer data) {
        if (CARPET_CHANNEL_NAME.contains(channel)) {
//          System.out.println("Package Echoed properly + " + data.readString(1000));
            handleData(data);
        }
    }

    /**
     * Handler for the incoming pakets from the server.
     *
     * @param data Data that is recieved from the server.
     */
    private static void handleData(PacketBuffer data) {
        int type = data.readInt();

        if (GUI_ALL_DATA == type) {
            CarpetRules.setAllRules(data);
        }
        if (RULE_REQUEST == type) {
            CarpetRules.ruleData(data);
        }
        if (VILLAGE_MARKERS == type) {
            VillageMarker.villageUpdate(data);
        }
    }

    /**
     * Packet sending method to send data to the server.
     *
     * @param data The data that is being sent to the server.
     */
    public static void packatSender(PacketBuffer data) {
        ClientPluginChannels.sendMessage(CARPET_CHANNEL_NAME, data, ChannelPolicy.DISPATCH_ALWAYS);
    }
}
