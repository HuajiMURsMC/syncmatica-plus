package ch.endte.syncmatica.litematica.gui;

import ch.endte.syncmatica.Context;
import ch.endte.syncmatica.ServerPlacement;
import ch.endte.syncmatica.communication.ClientCommunicationManager;
import ch.endte.syncmatica.communication.ExchangeTarget;
import ch.endte.syncmatica.communication.PacketType;
import ch.endte.syncmatica.litematica.LitematicManager;
import fi.dy.masa.malilib.gui.GuiBase;
import fi.dy.masa.malilib.gui.Message;
import fi.dy.masa.malilib.gui.button.ButtonBase;
import fi.dy.masa.malilib.gui.button.IButtonActionListener;
import io.netty.buffer.Unpooled;
import net.minecraft.network.PacketByteBuf;

public class ButtonListenerRemove implements IButtonActionListener {
    private final ServerPlacement placement;
    private final GuiBase messageDisplay;

    public ButtonListenerRemove(final ServerPlacement placement, final GuiBase messageDisplay) {
        this.placement = placement;
        this.messageDisplay = messageDisplay;
    }

    @Override
    public void actionPerformedWithButton(final ButtonBase button, final int mouseButton) {
        if (!GuiBase.isShiftDown()) {
            messageDisplay.addMessage(Message.MessageType.ERROR, "syncmatica.error.remove_without_shift");
            return;
        }
        button.setEnabled(false);
        final Context con = LitematicManager.getInstance().getActiveContext();
        final ExchangeTarget server = ((ClientCommunicationManager) con.getCommunicationManager()).getServer();
        final PacketByteBuf packetBuf = new PacketByteBuf(Unpooled.buffer());
        packetBuf.writeUuid(placement.getId());
        server.sendPacket(PacketType.REMOVE_SYNCMATIC.identifier, packetBuf, LitematicManager.getInstance().getActiveContext());
    }
}
