package cc.polyfrost.islandtils.mixin.transformers;

import cc.polyfrost.islandtils.Islandtils;
import cc.polyfrost.islandtils.utils.ServerUtils;
import net.minecraft.client.resource.ClientBuiltinResourcePackProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.net.URL;
import java.util.concurrent.CompletableFuture;

@Mixin(ClientBuiltinResourcePackProvider.class)
public class ResourcePackProviderInject {
    @Inject(
            method = "download",
            at = @At(value = "HEAD")
    )
    public void downloadMixin(URL url, String packSha1, boolean closeAfterDownload, CallbackInfoReturnable<CompletableFuture<?>> cir) {
        if (ServerUtils.isOnMCCIsland()) {
            Islandtils.PACK_URL = url.toString();

        }
    }
}
