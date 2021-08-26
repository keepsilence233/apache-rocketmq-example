package qx.leizige.common.constants;

public class MqConstants {


    /**
     * topic:表示一类消息的集合,每个主题包含若干条消息,每条消息只能属于一个主题,是RocketMQ进行消息订阅的基本单位
     */
    public final static String ACCOUNT_TOPIC = "account_topic";


    /**
     * Tag:为消息设置的标志,用于同一主题下区分不同类型的消息
     */
    public final static String TRANSFER_ACCOUNT_TAG = "transfer_accounts_tag";

}
