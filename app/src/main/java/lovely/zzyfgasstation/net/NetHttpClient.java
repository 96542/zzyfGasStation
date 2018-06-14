package lovely.zzyfgasstation.net;


/**
 * Created by admin on 2016/6/17.
 */
public interface NetHttpClient {

    //接口请求地址   s1550l1032.iok.la
//    String NET_SERVER = "http://222.137.19.163:4545"; //测试地址
    String NET_SERVER = "http://s1550l1032.iok.la:4545"; //测试地址

    //登录
    String LOGIN = NET_SERVER + "/login";

    //注销
    String UN_LOGIN = NET_SERVER + "/unlogin";

    //获取油枪列表
    String GET_OILGUN = NET_SERVER + "/getgunlist";

    //获取某个油枪的未结账列表
    String GET_NO_PAY_LIST = NET_SERVER + "/tradelist";

    //获取错误订单(现金支付)
    String GET_CASH_LIST = NET_SERVER + "/tradelistCase";


}
