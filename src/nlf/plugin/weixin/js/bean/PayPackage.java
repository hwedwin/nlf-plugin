package nlf.plugin.weixin.js.bean;

public class PayPackage{
  /** 银行通道类型，固定为WX */
  private final String bankType = "WX";
  /** 商品描述，必填，128字节以下 */
  private String body;
  /** 附加数据，非必填，128字节以下 */
  private String attach;
  /** 商户号，必填，财付通商户号partnerId */
  private String partner;
  /** 商户订单号，必填，商户系统内部的订单号，32 个字符内、可包含字母；确保在商户系统唯一 */
  private String outTradeNo;
  /** 订单总金额，必填，单位为分 */
  private String totalFee;
  /** 支付币种，必填，取值：1（人民币），暂只支持1； */
  private String feeType = "1";
  /** 通知URL，必填，在支付完成后，接收微信通知支付结果的URL，需给绝对路径， 255 字符内 */
  private String notifyUrl;
  /** 订单生成的机器IP，必填，15字节以下，指用户浏览器端IP，不是商户服务器IP，格式为IPV4； */
  private String spbillCreateIp;
  /** 交易起始时间，非必填，订单生成时间，格式为yyyyMMddHHmmss，如2009 年12 月25 日9 点10 分10 秒表示为20091225091010，时区为GMT+8 beijing；该时间取自商户服务器； */
  private String timeStart;
  /** 交易结束时间，非必填，订单失效时间，格式为yyyyMMddHHmmss，如2009 年12 月27 日9 点10 分10 秒表示为20091227091010，时区为GMT+8 beijing；该时间取自商户服务器； */
  private String timeExpire;
  /** 物流费用，非必填，单位为分。如果有值，必须保证transport_fee + product_fee=total_fee； */
  private String transportFee;
  /** 商品费用，非必填，商品费用，单位为分。如果有值，必须保证transport_fee + product_fee=total_fee； */
  private String productFee;
  /** 商品标记，非必填，优惠券时可能用到 */
  private String goodsTag;
  /** 传入参数字符编码，必填，取值范围："GBK"、"UTF-8" */
  private String inputCharset = "UTF-8";

  public String getBody(){
    return body;
  }

  public void setBody(String body){
    this.body = body;
  }

  public String getAttach(){
    return attach;
  }

  public void setAttach(String attach){
    this.attach = attach;
  }

  public String getPartner(){
    return partner;
  }

  public void setPartner(String partner){
    this.partner = partner;
  }

  public String getOutTradeNo(){
    return outTradeNo;
  }

  public void setOutTradeNo(String outTradeNo){
    this.outTradeNo = outTradeNo;
  }

  public String getTotalFee(){
    return totalFee;
  }

  public void setTotalFee(String totalFee){
    this.totalFee = totalFee;
  }

  public String getNotifyUrl(){
    return notifyUrl;
  }

  public void setNotifyUrl(String notifyUrl){
    this.notifyUrl = notifyUrl;
  }

  public String getSpbillCreateIp(){
    return spbillCreateIp;
  }

  public void setSpbillCreateIp(String spbillCreateIp){
    this.spbillCreateIp = spbillCreateIp;
  }

  public String getTimeStart(){
    return timeStart;
  }

  public void setTimeStart(String timeStart){
    this.timeStart = timeStart;
  }

  public String getTimeExpire(){
    return timeExpire;
  }

  public void setTimeExpire(String timeExpire){
    this.timeExpire = timeExpire;
  }

  public String getTransportFee(){
    return transportFee;
  }

  public void setTransportFee(String transportFee){
    this.transportFee = transportFee;
  }

  public String getProductFee(){
    return productFee;
  }

  public void setProductFee(String productFee){
    this.productFee = productFee;
  }

  public String getGoodsTag(){
    return goodsTag;
  }

  public void setGoodsTag(String goodsTag){
    this.goodsTag = goodsTag;
  }

  public String getInputCharset(){
    return inputCharset;
  }

  public void setInputCharset(String inputCharset){
    this.inputCharset = inputCharset;
  }

  public String getBankType(){
    return bankType;
  }

  public String getFeeType(){
    return feeType;
  }

  public void setFeeType(String feeType){
    this.feeType = feeType;
  }
}