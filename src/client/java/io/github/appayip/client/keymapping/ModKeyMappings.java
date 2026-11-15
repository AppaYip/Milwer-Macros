package io.github.appayip.client.keymapping;

import com.mojang.blaze3d.platform.InputConstants;
import io.github.appayip.MilwerMacros;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.minecraft.client.KeyMapping;

public class ModKeyMappings {
    private static final String ID = MilwerMacros.MOD_ID;

    public static final KeyMapping GUI_SOLVER = KeyMappingHelper.registerKeyMapping(
            new KeyMapping(
                    "key." + ID + ".gui_solver",
                    InputConstants.Type.KEYBOARD,
                    InputConstants.KEY_R,
                    KeyMapping.Category.MISC
            )
    );



    public static void register() {
        MilwerMacros.LOGGER.info("Registering KeyMappings");
    }
}
