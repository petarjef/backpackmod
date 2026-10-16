package backpack.modid.client;

import backpack.modid.Backpackmod;
import backpack.modid.OpenBackpackPayload;
import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.KeyMapping;

public class BackpackmodClient implements ClientModInitializer {

	private static final KeyMapping.Category CATEGORY = KeyMapping.Category.register(
			Backpackmod.id("backpack"));

	private static KeyMapping openKey;

	@Override
	public void onInitializeClient() {
		openKey = KeyMappingHelper.registerKeyMapping(new KeyMapping(
				"key.backpackmod.open_backpack",
				InputConstants.KEY_B,
				CATEGORY));

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			while (openKey.consumeClick()) {
				if (client.player != null) {
					ClientPlayNetworking.send(new OpenBackpackPayload());
				}
			}
		});
	}
}