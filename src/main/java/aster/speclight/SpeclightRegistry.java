package aster.speclight;

import com.simibubi.create.content.kinetics.base.BlockBreakingKineticBlockEntity;
import de.dafuqs.spectrum.api.item_group.ItemGroupIDs;
import de.dafuqs.spectrum.registries.SpectrumItemGroups;
import dev.onyxstudios.cca.api.v3.block.BlockComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.block.BlockComponentInitializer;
import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistryV3;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;

public class SpeclightRegistry implements BlockComponentInitializer {

    public static final Item ENLIGHTENMENT_STAFF = Registry.register(Registries.ITEM, new Identifier("speclight:enlightenment_staff"),
            new EnlightenmentStaff(new FabricItemSettings().maxCount(1)));


    public static final ComponentKey<UUIDComponent> UUID_COMPONENT =
            ComponentRegistryV3.INSTANCE.getOrCreate(
                    new Identifier("speclight:uuid_comp"),
                    UUIDComponent.class
            );

    @Override
    public void registerBlockComponentFactories(BlockComponentFactoryRegistry factory) {
    factory.registerFor(BlockBreakingKineticBlockEntity.class, UUID_COMPONENT,
            EnlightenmentAttachComponent::new);
    }


    public static void invoke(){
        ItemGroupEvents.modifyEntriesEvent(RegistryKey.of(Registries.ITEM_GROUP.getKey(), ItemGroupIDs.SUBTAB_EQUIPMENT)).register(entries -> {
            // addEquipmentEntry in Spectrum just does entries.add() with default visibility,
            // which is PARENT_AND_SEARCH_TABS — the standard add() call does the same
            entries.add(SpeclightRegistry.ENLIGHTENMENT_STAFF);
        });
    }


}
