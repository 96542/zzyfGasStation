package lovely.zzyfgasstation.bean;

import android.os.Parcel;
import android.os.Parcelable;


public class ChargeBean implements Parcelable{

    private String CardNo; //加油卡号
    private long FlowNo; //交易流水号
    private String MachTime; //加油时间
    private float Money;  //加油金额
    private String OilCode; //油品代码
    private String OilName; //油品名称
    private float Price; //单价
    private float Qty; //加油升数
    private String Terminal; //油抢号

    public ChargeBean() {
    }

    public ChargeBean(String cardNo, long flowNo, String machTime, float money, String oilCode, String oilName, float price, float qty, String terminal) {
        CardNo = cardNo;
        FlowNo = flowNo;
        MachTime = machTime;
        Money = money;
        OilCode = oilCode;
        OilName = oilName;
        Price = price;
        Qty = qty;
        Terminal = terminal;
    }

    protected ChargeBean(Parcel in) {
        CardNo = in.readString();
        FlowNo = in.readLong();
        MachTime = in.readString();
        Money = in.readFloat();
        OilCode = in.readString();
        OilName = in.readString();
        Price = in.readFloat();
        Qty = in.readFloat();
        Terminal = in.readString();
    }

    public static final Creator<ChargeBean> CREATOR = new Creator<ChargeBean>() {
        @Override
        public ChargeBean createFromParcel(Parcel in) {
            return new ChargeBean(in);
        }

        @Override
        public ChargeBean[] newArray(int size) {
            return new ChargeBean[size];
        }
    };

    public String getCardNo() {
        return CardNo;
    }

    public void setCardNo(String cardNo) {
        CardNo = cardNo;
    }

    public long getFlowNo() {
        return FlowNo;
    }

    public void setFlowNo(int flowNo) {
        FlowNo = flowNo;
    }

    public String getMachTime() {
        return MachTime;
    }

    public void setMachTime(String machTime) {
        MachTime = machTime;
    }

    public float getMoney() {
        return Money;
    }

    public void setMoney(float money) {
        Money = money;
    }

    public String getOilCode() {
        return OilCode;
    }

    public void setOilCode(String oilCode) {
        OilCode = oilCode;
    }

    public String getOilName() {
        return OilName;
    }

    public void setOilName(String oilName) {
        OilName = oilName;
    }

    public float getPrice() {
        return Price;
    }

    public void setPrice(float price) {
        Price = price;
    }

    public float getQty() {
        return Qty;
    }

    public void setQty(float qty) {
        Qty = qty;
    }

    public String getTerminal() {
        return Terminal;
    }

    public void setTerminal(String terminal) {
        Terminal = terminal;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(CardNo);
        dest.writeLong(FlowNo);
        dest.writeString(MachTime);
        dest.writeFloat(Money);
        dest.writeString(OilCode);
        dest.writeString(OilName);
        dest.writeFloat(Price);
        dest.writeFloat(Qty);
        dest.writeString(Terminal);
    }
}
