package net.zepalesque.redux.item.accessories.cape;

import com.aetherteam.aether.item.accessories.cape.CapeItem;
import net.minecraft.resources.ResourceLocation;
import net.zepalesque.redux.Redux;

public class AerboundCapeItem extends CapeItem {
    public AerboundCapeItem(String capeLocation, Properties properties) {
        super(Redux.loc(capeLocation), properties);
    }
}
