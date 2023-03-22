package net.runelite.client.plugins.g_nmzhelper;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.Range;

@ConfigGroup("G_NmzHelper")
public interface G_NmzHelperConfiguration extends Config {
    //enable or disable prayer drinker
    @ConfigItem(
            keyName = "DrinkPrayer",
            name = "Drink Prayer or Restore Potion",
            description = "Enable drinking prayer/restore",
            position = 1
    )
    default boolean DrinkPrayer(){return true;}
    
    
    
    //setting the min pray point to drink
    @Range(
            min = 1,
            max = 99
    )
    @ConfigItem(
            keyName = "MinPray",
            name = "Minimun Pray Point",
            description = "Minimun prayer level to drink",
            position = 5
    )
    default int MinPray() {
        return 15;
    }

    
    
    //setting the max pray pot to drink
    @Range(
            min = 1,
            max = 100
    )
    @ConfigItem(
            keyName = "MaxPray",
            name = "Maximun Prayer Point",
            description = "Highest Pray to consider Drinking prayer potion",
            position = 7
    )
    default int MaxPray(){return 25;}

    //enable to drink overload
    @ConfigItem(
            keyName = "DrinkOverLoad",
            name = "Reactive overload when it is over",
            description = "Enable to drink overload",
            position = 10
    )
    default boolean DrinkOverload(){return false;}
    
    

}
