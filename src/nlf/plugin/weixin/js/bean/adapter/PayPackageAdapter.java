package nlf.plugin.weixin.js.bean.adapter;

import java.util.HashMap;
import java.util.Map;
import nc.liat6.frame.db.entity.IBeanRule;

public class PayPackageAdapter implements IBeanRule{
  private static final Map<String,String> map = new HashMap<String,String>();
  static{
    map.put("bankType","bank_type");
    map.put("body","body");
    map.put("attach","attach");
    map.put("partner","partner");
    map.put("outTradeNo","out_trade_no");
    map.put("totalFee","total_fee");
    map.put("feeType","fee_type");
    map.put("notifyUrl","notify_url");
    map.put("spbillCreateIp","spbill_create_ip");
    map.put("timeStart","time_start");
    map.put("timeExpire","time_expire");
    map.put("transportFee","transport_fee");
    map.put("productFee","product_fee");
    map.put("goodsTag","goods_tag");
    map.put("inputCharset","input_charset");
  }

  public String getKey(String property){
    return map.get(property);
  }
}