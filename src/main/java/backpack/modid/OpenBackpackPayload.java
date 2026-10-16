package backpack.modid;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public record OpenBackpackPayload() implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<OpenBackpackPayload> TYPE =
            new CustomPacketPayload.Type<>(Backpackmod.id("open_backpack"));

    public static final StreamCodec<RegistryFriendlyByteBuf, OpenBackpackPayload> CODEC =
            StreamCodec.unit(new OpenBackpackPayload());

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}