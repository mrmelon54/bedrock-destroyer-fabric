package net.onpointcoding.bedrockdestroyer;

import net.fabricmc.api.ModInitializer;

public class BedrockDestroyer implements ModInitializer {
    private static BedrockDestroyer instance;

    @Override
    public void onInitialize() {
        instance = this;


    }

    public static BedrockDestroyer getInstance() {
        return instance;
    }
}
