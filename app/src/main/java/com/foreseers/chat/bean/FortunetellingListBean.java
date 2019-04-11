package com.foreseers.chat.bean;

import java.util.List;

/**
 * File description.
 *
 * @author how
 * @date 2019/4/1
 */
public class FortunetellingListBean {

    /**
     * status : success
     * data : ["外貌","性格","才能","潛在個性及心理狀況","整體建議","生活與物質品味的優劣","先天較弱的器官","容易得到的疾病","一生疾病的輕重","解壓良方","建議積福方法"]
     */

    private String status;
    private List<String> data;

    public String getStatus() { return status;}

    public void setStatus(String status) { this.status = status;}

    public List<String> getData() { return data;}

    public void setData(List<String> data) { this.data = data;}
}
