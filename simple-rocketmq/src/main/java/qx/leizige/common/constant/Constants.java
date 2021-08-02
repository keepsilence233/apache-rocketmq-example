package qx.leizige.common.constant;

public class Constants {


    public final static String DEFAULT_PRODUCT = "default_product";

    /**
     * topic:表示一类消息的集合,每个主题包含若干条消息,每条消息只能属于一个主题,是RocketMQ进行消息订阅的基本单位
     */
    public final static String ITEM_TOPIC = "item_topic";
    public final static String ORDER_TOPIC = "order_topic";


    /**
     * Tag:为消息设置的标志,用于同一主题下区分不同类型的消息
     */
    public final static String ADD_ITEM_TAG = "add_item_tag";
    public final static String UPDATE_ITEM_PRICE_TAG = "update_item_price_tag";

    public final static String ADD_ORDER_TAG = "add_order_tag";

}
