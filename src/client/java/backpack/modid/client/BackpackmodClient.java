package backpack.modid.client;

import backpack.modid.Backpackmod;
import backpack.modid.OpenBackpackPayload;
import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityRenderLayerRegistrationCallback;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.fabricmc.fabric.api.client.screen.v1.ScreenKeyboardEvents;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.player.AvatarRenderer;

public class BackpackmodClient implements ClientModInitializer {

	private static final KeyMapping.Category CATEGORY = KeyMapping.Category.register(
			Backpackmod.id("backpack"));

	private static KeyMapping openKey;

	@SuppressWarnings({"unchecked", "rawtypes"})
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

		ScreenEvents.AFTER_INIT.register((client, screen, scaledWidth, scaledHeight) -> {
			if (screen instanceof AbstractContainerScreen<?>) {
				ScreenKeyboardEvents.allowKeyPress(screen).register((s, keyEvent) -> {
					if (openKey.matches(keyEvent) && s.getTitle().getString().equals("Backpack")) {
						if (client.player != null) {
							client.player.closeContainer();
						}
						return false;
					}
					return true;
				});
			}
		});

		LivingEntityRenderLayerRegistrationCallback.EVENT.register(
				(entityType, entityRenderer, registrationHelper, context) -> {
					if (entityRenderer instanceof AvatarRenderer<?> avatarRenderer) {
						registrationHelper.register(new BackpackLayer((RenderLayerParent) avatarRenderer));
					}
				});
	}
}