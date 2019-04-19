package com.foreseers.chat.view.item;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.foreseers.chat.adapter.FortunetellingUserAdapter;

/**
 * File description.
 *
 * @author how
 * @date 2019/3/22
 */
public class Level1Item implements MultiItemEntity {
    public String name;
    public String age;
    public int lifeuserid;
    public int self;

    public Level1Item(String name, String age,int lifeuserid,int self) {
        this.age = age;
        this.name = name;
        this.lifeuserid=lifeuserid;
        this.self=self;
    }

    @Override
    public int getItemType() {
        return FortunetellingUserAdapter.TYPE_LEVEL_1;
    }
}