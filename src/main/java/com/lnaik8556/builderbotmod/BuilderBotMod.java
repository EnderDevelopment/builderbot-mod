package com.lnaik8556.builderbotmod;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public
class BuilderBotMod implements ModInitializer {
    public static final String MOD_ID = "builderbotmod";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static final EntityType<BuilderBotEntity> BUILDER_BOT = Registry.register(
    Registries.ENTITY_TYPE,
    new Identifier(MOD_ID, "builder_bot"),
    FabricEntityTypeBuilder.create(SpawnGroup.MISC, BuilderBotEntity::new)
    .dimensions(EntityDimensions.fixed(0.6f, 1.8f)).build()
    );

    @Override
    public void onInitialize() {
        FabricDefaultAttributeRegistry.register(BUILDER_BOT, BuilderBotEntity.createMobAttributes());
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            BuilderBotCommand.register(dispatcher);
        });
        LOGGER.info("BuilderBot Mod initialized");
    }
}
