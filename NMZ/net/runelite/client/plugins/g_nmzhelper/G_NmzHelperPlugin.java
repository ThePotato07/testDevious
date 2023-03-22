package net.runelite.client.plugins.g_nmzhelper;

import com.google.inject.Provides;
import javax.inject.Inject;
import org.pf4j.Extension;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.config.ConfigManager;
import lombok.extern.slf4j.Slf4j;
import net.unethicalite.api.items.Inventory;
import net.runelite.api.events.GameTick;
import net.runelite.api.Client;
import net.runelite.api.Skill;

@Extension
@PluginDescriptor(
        name = "G_NmzHelper",
        description = "help with drinking prayer in nmz",
        enabledByDefault = false
)
@Slf4j
public class G_NmzHelperPlugin extends Plugin {
    //Injecting the configuration
    @Inject
    private G_NmzHelperConfiguration config;
    @Inject
    private Client client;

    //gett the configuration
    @Provides
    G_NmzHelperConfiguration provideconfig(ConfigManager configmanager) {
        return configmanager.getConfig(G_NmzHelperConfiguration.class);
    }
    
    
    //variables
    private int drinkPrayer;
    
    

    @Override
    protected void startUp() {
        log.info("Starting G_NmzHelper");
        
        
        if (config.DrinkPrayer()) {
            log.info("Drinking prayer potion is  On");
            drinkPrayer = getNewDrinkPrayer();
        }
        if (config.DrinkOverload()) {
            log.info("Drinking overload is On");
        }

    }

    private void onGameTick(GameTick gametick) {

        if(shouldDrinkPrayer()){
            drinkPrayer();
        }
        
    }

    private void drinkPrayer() {

        if (inventoryContainPray()) {
            Inventory.getFirst(x -> x.getName().contains("Prayer potion(")).interact("Drink");
            drinkPrayer = getNewDrinkPrayer();
            return;
        }

        if(inventoryContainRestore()){
            Inventory.getFirst(x -> x.getName().contains("Super restore(")).interact("Drink"); 
            drinkPrayer = getNewDrinkPrayer();
        }
        
    }

    private boolean shouldDrinkPrayer() {
        if (config.DrinkPrayer() == false) {
            return false;
        }
        if (client.getBoostedSkillLevel(Skill.PRAYER) <= drinkPrayer) {
            return true;
        }
        if (client.getBoostedSkillLevel(Skill.PRAYER) <= config.MinPray()) {
            return true;
        }

        return false;
    }

    private boolean inventoryContainPray() {
        return Inventory.contains(x -> x.getName().contains("Prayer Potion("));
    }

    private boolean inventoryContainRestore() {
        return Inventory.contains(x -> x.getName().contains("Super restore("));
    }

    private int getNewDrinkPrayer(){
        int newPray = (int) (Math.random()* (config.MaxPray() - config.MinPray() +1) + config.MinPray());
        log.info("New prayer point to drink is: " + newPray);
        return newPray;
        
    }
    
}
