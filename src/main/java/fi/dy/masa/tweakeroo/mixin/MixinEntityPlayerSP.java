package fi.dy.masa.tweakeroo.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import com.mojang.authlib.GameProfile;
import fi.dy.masa.tweakeroo.config.FeatureToggle;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.world.World;

@Mixin(EntityPlayerSP.class)
public abstract class MixinEntityPlayerSP extends AbstractClientPlayer
{
    public MixinEntityPlayerSP(World worldIn, GameProfile playerProfile)
    {
        super(worldIn, playerProfile);
    }

    @Redirect(method = "onLivingUpdate()V",
              at = @At(value = "INVOKE",
                       target = "Lnet/minecraft/client/Minecraft;displayGuiScreen(Lnet/minecraft/client/gui/GuiScreen;)V"))
    private void onCloseGui(Minecraft mc, GuiScreen gui)
    {
        if (FeatureToggle.TWEAK_NO_PORTAL_GUI_CLOSING.getBooleanValue() == false)
        {
            mc.displayGuiScreen(gui);
        }
    }
}
