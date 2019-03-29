package com.foreseers.chat.view.item;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.foreseers.chat.adapter.FortunetellingUserAdapter;

/**
 * File description.
 *
 * @author how
 * @date 2019/3/22
 */
public class Level0Item extends AbstractExpandableItem<Level1Item> implements MultiItemEntity {
    public String title;
    public int color;

    public Level0Item( String title,int color) {
        this.color = color;
        this.title = title;
    }

    @Override
    public int getItemType() {
        return FortunetellingUserAdapter.TYPE_LEVEL_0;
    }

    @Override
    public int getLevel() {
        return 0;
    }
}
