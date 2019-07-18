package com.foreseers.chat.bean;

import java.util.List;

/**
 * File description.
 *
 * @author how
 * @date 2019/7/15
 */
public class AttentionData {

    private boolean praiseState;
    private boolean volumeState;
    private boolean shareCountState;
    private List<String> imgList;

    public AttentionData(List<String> imgList) {
        this.imgList = imgList;
    }

    public List<String> getImgList() {
        return imgList;
    }

    public void setImgList(List<String> imgList) {
        this.imgList = imgList;
    }

    public boolean isPraiseState() {
        return praiseState;
    }

    public void setPraiseState(boolean praiseState) {
        this.praiseState = praiseState;
    }

    public boolean isVolumeState() {
        return volumeState;
    }

    public void setVolumeState(boolean volumeState) {
        this.volumeState = volumeState;
    }

    public boolean isShareCountState() {
        return shareCountState;
    }

    public void setShareCountState(boolean shareCountState) {
        this.shareCountState = shareCountState;
    }


}
