package dev.emi.trinkets.mixin;

import dev.emi.trinkets.TrinketPlayerScreenHandler;
import dev.emi.trinkets.api.TrinketInventory;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.network.packet.s2c.play.PlayerRespawnS2CPacket;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryKey;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

/**
 * Copies slot EAMs to players client-side when changing dimensions
 *
 * @author C4
 */
@Mixin(ClientPlayNetworkHandler.class)
public class ClientPlayNetworkHandlerMixin {

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;setId(I)V"), method = "onPlayerRespawn", locals = LocalCapture.CAPTURE_FAILSOFT)
    private void onPlayerRespawn(PlayerRespawnS2CPacket packet, CallbackInfo ci, RegistryKey<?> registryKey, RegistryEntry<?> registryEntry, ClientPlayerEntity clientPlayerEntity, int i, String string, ClientPlayerEntity clientPlayerEntity2) {
        if (packet.shouldKeepPlayerAttributes()) {
            TrinketInventory.copyFrom(clientPlayerEntity, clientPlayerEntity2);
            ((TrinketPlayerScreenHandler) clientPlayerEntity2.playerScreenHandler).trinkets$updateTrinketSlots(false);
        }
    }
}
