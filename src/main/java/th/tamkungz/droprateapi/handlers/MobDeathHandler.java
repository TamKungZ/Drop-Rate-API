package th.tamkungz.droprateapi.handlers;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import th.tamkungz.droprateapi.DropRateMain;
import th.tamkungz.droprateapi.api.DropRateAPI;

import java.util.List;
import java.util.Random;

@Mod.EventBusSubscriber(modid = DropRateMain.MOD_ID)
public class MobDeathHandler {
    private static final Random RANDOM = new Random();

    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {
        LivingEntity entity = event.getEntityLiving();
        World world = entity.getCommandSenderWorld();

        // Get ALL DropData entries for this mob
        List<DropRateAPI.DropData> dropDataList = DropRateAPI.getDropData(entity.getType());

        for (DropRateAPI.DropData dropData : dropDataList) {
            // Check each entry's rate individually
            if (RANDOM.nextInt(100) < dropData.rate) {
                for (Item item : dropData.items) {
                    // Generate random amount between min and max
                    int amount = RANDOM.nextInt(
                        dropData.maxAmount - dropData.minAmount + 1
                    ) + dropData.minAmount;

                    ItemStack itemStack = new ItemStack(item, amount); // Set stack size
                    ItemEntity itemEntity = new ItemEntity(world, entity.getX(), entity.getY(), entity.getZ(), itemStack);
                    world.addFreshEntity(itemEntity);
                }
            }
        }
    }
}