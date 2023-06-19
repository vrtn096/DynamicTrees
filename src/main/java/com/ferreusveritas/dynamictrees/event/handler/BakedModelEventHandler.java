package com.ferreusveritas.dynamictrees.event.handler;

import com.ferreusveritas.dynamictrees.DynamicTrees;
import com.ferreusveritas.dynamictrees.block.PottedSaplingBlock;
import com.ferreusveritas.dynamictrees.models.baked.BakedModelBlockBonsaiPot;
import com.ferreusveritas.dynamictrees.models.baked.BranchBlockBakedModel;
import com.ferreusveritas.dynamictrees.models.loader.BranchBlockModelLoader;
import com.ferreusveritas.dynamictrees.models.loader.RootBlockModelLoader;
import com.ferreusveritas.dynamictrees.models.loader.ThickBranchBlockModelLoader;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.client.event.ModelEvent.BakingCompleted;
import net.minecraftforge.client.event.ModelEvent.RegisterGeometryLoaders;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * @author Harley O'Connor
 */
@Mod.EventBusSubscriber(modid = DynamicTrees.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class BakedModelEventHandler {

    public static final ResourceLocation BRANCH = DynamicTrees.location("branch");
    public static final ResourceLocation ROOT = DynamicTrees.location("root");
    public static final ResourceLocation THICK_BRANCH = DynamicTrees.location("thick_branch");

    @SubscribeEvent
    public static void onModelRegistryEvent(RegisterGeometryLoaders event) {
        // Register model loaders for baked models.
        event.register("branch", new BranchBlockModelLoader());
        event.register("root", new RootBlockModelLoader());
        event.register("thick_branch", new ThickBranchBlockModelLoader());
    }

    @SubscribeEvent
    public static void onModelModifyBakingResultResult(ModelEvent.ModifyBakingResult event) {
        // Put bonsai pot baked model into its model location.
        event.getModels().computeIfPresent(new ModelResourceLocation(PottedSaplingBlock.REG_NAME, ""), (k, val) -> new BakedModelBlockBonsaiPot(val));
    }

    @SubscribeEvent
    public static void onModelBake(BakingCompleted event) {
        // Setup branch baked models (bakes cores and sleeves).
        BranchBlockBakedModel.INSTANCES.forEach(BranchBlockBakedModel::setupModels);
        BranchBlockBakedModel.INSTANCES.clear();
    }
}