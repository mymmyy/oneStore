package cn.onestore.domain;

import java.util.List;

/**
 * Created by 明柯 on 2017/4/19.
 * User 类的扩展类
 */
public class WrapUser extends User{
    private Shop shop;
    private  List<ReceiverAddress> receiverAddresses;

    public List<ReceiverAddress> getReceiverAddresses() {
        return receiverAddresses;
    }

    public void setReceiverAddresses(List<ReceiverAddress> receiverAddresses) {
        this.receiverAddresses = receiverAddresses;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }




}
