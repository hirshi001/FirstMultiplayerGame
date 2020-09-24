package com.hirshi001.multiplayerrotmg.gamepieces.entityloaders.itemloaders;

import com.hirshi001.multiplayerrotmg.gamepieces.items.ItemEntity;
import com.hirshi001.multiplayerrotmg.util.identifiable.IdentifiableObject;
import io.netty.buffer.ByteBuf;

public abstract class ItemLoader extends IdentifiableObject {


    public abstract ItemEntity spawn(ByteBuf b);
}
