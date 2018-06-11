package lovely.zzyfgasstation.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 油枪列表类
 */
public class oilGunList implements Parcelable{
    private String OilNo;
    private String OilType;

    public oilGunList() {
    }

    public oilGunList(String oilNo, String oilType) {
        OilNo = oilNo;
        OilType = oilType;
    }

    protected oilGunList(Parcel in) {
        OilNo = in.readString();
        OilType = in.readString();
    }

    public static final Creator<oilGunList> CREATOR = new Creator<oilGunList>() {
        @Override
        public oilGunList createFromParcel(Parcel in) {
            return new oilGunList(in);
        }

        @Override
        public oilGunList[] newArray(int size) {
            return new oilGunList[size];
        }
    };

    public String getOilNo() {
        return OilNo;
    }

    public void setOilNo(String oilNo) {
        OilNo = oilNo;
    }

    public String getOilType() {
        return OilType;
    }

    public void setOilType(String oilType) {
        OilType = oilType;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(OilNo);
        dest.writeString(OilType);
    }
}
