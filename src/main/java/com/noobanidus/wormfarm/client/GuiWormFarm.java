package com.noobanidus.wormfarm.client;

import com.noobanidus.wormfarm.common.ContainerWormFarm;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiFurnace;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.io.IOException;

public class GuiWormFarm extends GuiContainer {

    private static final ResourceLocation background = new ResourceLocation("wormfarm:textures/gui/worm_gui.png");
    private static final int height = 176, width = 176, scale = 256;

    private final ContainerWormFarm container;

    private final Minecraft mc = Minecraft.getMinecraft();

    public GuiWormFarm(ContainerWormFarm inventorySlotsIn) {
        super(inventorySlotsIn);

        container = inventorySlotsIn;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.enableColorMaterial();;
        this.mc.getTextureManager().bindTexture(background);

        drawModalRectWithCustomSizedTexture(guiLeft, guiTop, 0, 0, scale, scale, scale, scale);
    }

    @Override
    public void initGui() {
        super.initGui();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);
    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
    }
}
