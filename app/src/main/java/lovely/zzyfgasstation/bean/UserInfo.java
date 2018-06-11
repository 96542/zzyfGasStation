package lovely.zzyfgasstation.bean;

/**
 * 用户信息
 */
public class UserInfo {
    private String Userid;//用户编号
    private String UserName;//用户名
    private String SessionId;//会话Id
    private String StationName;//油站名称
    private String StationNo;//油站编号
    private boolean IsLogin;
    private String pwd;

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getUserid() {
        return Userid;
    }

    public void setUserid(String userid) {
        Userid = userid;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getSessionId() {
        return SessionId;
    }

    public void setSessionId(String sessionId) {
        SessionId = sessionId;
    }

    public String getStationName() {
        return StationName;
    }

    public void setStationName(String stationName) {
        StationName = stationName;
    }

    public String getStationNo() {
        return StationNo;
    }

    public void setStationNo(String stationNo) {
        StationNo = stationNo;
    }

    public boolean isLogin() {
        return IsLogin;
    }

    public void setLogin(boolean login) {
        IsLogin = login;
    }
}
