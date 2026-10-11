package com.lnaik8556.builderbotmod;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public
class BuilderBotCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(literal("builder")
        .then(literal("spawn").executes(BuilderBotCommand::spawnBuilderBot))
        .then(literal("build")
        .then(argument("structure", StringArgumentType.string()).executes(BuilderBotCommand::buildStructure))
        .then(literal("prompt").then(argument("description", StringArgumentType.greedyString()).executes(BuilderBotCommand::buildFromPrompt))))
        .then(literal("stop").executes(BuilderBotCommand::stopBuilding))
        .then(literal("resume").executes(BuilderBotCommand::resumeBuilding))
        .then(literal("despawn").executes(BuilderBotCommand::despawnBuilderBot)));
    }

    private static int spawnBuilderBot(CommandContext<ServerCommandSource> context) {
        ServerPlayerEntity player = context.getSource().getPlayer();
        if (player != null) {
            BuilderBotEntity builderBot = new BuilderBotEntity(BuilderBotMod.BUILDER_BOT, player.getWorld());
            builderBot.refreshPositionAndAngles(player.getX(), player.getY(), player.getZ(), player.getYaw(), player.getPitch());
            player.getWorld().spawnEntity(builderBot);
            context.getSource().sendFeedback(() -> Text.literal("BuilderBot spawned!"), true);
        }
        return 1;
    }

    private static int buildStructure(CommandContext<ServerCommandSource> context) {
        String structure = StringArgumentType.getString(context, "structure");
        context.getSource().sendFeedback(() -> Text.literal("Building structure: " + structure), true);
        return 1;
    }

    private static int buildFromPrompt(CommandContext<ServerCommandSource> context) {
        String description = StringArgumentType.getString(context, "description");
        context.getSource().sendFeedback(() -> Text.literal("Building from prompt: " + description), true);
        return 1;
    }

    private static int stopBuilding(CommandContext<ServerCommandSource> context) {
        context.getSource().sendFeedback(() -> Text.literal("Building stopped"), true);
        return 1;
    }

    private static int resumeBuilding(CommandContext<ServerCommandSource> context) {
        context.getSource().sendFeedback(() -> Text.literal("Building resumed"), true);
        return 1;
    }

    private static int despawnBuilderBot(CommandContext<ServerCommandSource> context) {
        context.getSource().sendFeedback(() -> Text.literal("BuilderBot despawned"), true);
        return 1;
    }
}
