package lovely.zzyfgasstation.utils;

import android.content.Context;
import android.content.SharedPreferences;


/**
 * 存储通用类，用来存储各种需要缓存的数据
 *
 * @author admin
 */
public class SpUtil {

    private static String TAG = SpUtil.class.getSimpleName();

    public static SharedPreferences sp;

    // 单例
    private static SpUtil spUtil = null;

    // 为空
    private static final String IS_NULL = "";

//    private User user;
//
//    private DeviceNowInfo deviceNowInfo;
//
//    private AccountInfo accountInfo;

    private static final String USER_INFO = "USER_INFO";

    private static final String DEVICE_NOW_INFO = "DEVICE_NOW_INFO";

    private static final String IF_PUSH_MESSAGE = "IF_PUSH_MESSAGE";

    private static final String ACCOUNT_INFO = "ACCOUNT_INFO";

//	private UserAccount userAccount;
//
//	private LocalSettings localSettings;
//
//	private DroneVersionInfo droneVersionInfo;
//
//	private NativeDroneNewVersion nativeDroneNewVersion;
//
//	private List<UserPhoto> userPhotoList;
//
//	private List<String> droneStatusInfoList;
//
//	private List<GalleryPhoto> photoList;
//
//	private List<SpecialNoFlyZone> noFlyZoneList;
//

//
//	private static final String IS_FIRST_OPENAPP = "IS_FIRST_OPENAPP";
//
//	private static final String IS_FIRST_OPENDRONE = "IS_FIRST_OPENDRONE";
//
//	private static final String USER_PHOTO = "USER_PHOTO";
//
//	private static final String USER_ACCOUNT = "USER_ACCOUNT";
//
//	private static final String DRONE_SETTING = "DRONE_SETTING";
//
//	private static final String DRONE_ORIENTATION = "DRONE_ORIENTATION";
//
//	private static final String DRONE_STATUS_INFO = "DRONE_STATUS_INFO";
//
//	private static final String IF_SHOW_DRONE = "IF_SHOW_DRONE";
//
//	private static final String DRONE_VERSION_INFO = "DRONE_VERSION_INFO";
//
//	private static final String NATIVE_DRONE_NEW_VERSION = "NATIVE_DRONE_NEW_VERSION";
//
//	private static final String DRONE_PHOTO_LIST = "DRONE_PHOTO_LIST";
//
//	private static final String GET_CURRENT_APP_VERSION = "GET_CURRENT_APP_VERSION";
//
//	private static final String GET_CURRENT_LANGUAGE = "GET_CURRENT_LANGUAGE";
//
//	private static final String GET_NO_FLY_ZONE_LIST = "GET_NO_FLY_ZONE_LIST";
//

    /**
     * 构造方法
     *
     * @return
     */
    public static SpUtil getInstance() {
        if (spUtil == null) {
            synchronized (SpUtil.class) {
                if (spUtil == null) {
                    spUtil = new SpUtil();
                }
            }
        }
        return spUtil;
    }


    public static void initSp(Context context) {
        sp = context.getSharedPreferences(Constant.SP_NAME, 0);
    }

	//保存是否推送消息
	public void setIfPushMessage(boolean ifPushMsg) {
		SharedPreferences.Editor editor = sp.edit();
		editor.putBoolean(IF_PUSH_MESSAGE, ifPushMsg);
		editor.commit();
	}

	//获取是否推送消息
	public boolean ifPushMessage() {
		return sp.getBoolean(IF_PUSH_MESSAGE, true);
	}
	//调试阶段 固定id
	public String getTestUserId(){


        return "1709051536310100021597";
    }


    //应用分类存储
    public void setTypes(String json){
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("type", json);
        editor.commit();
    }
    public String getTypes(){
       return sp.getString("type","");
    }

//    /**
//     * 是否登录过
//     *
//     * @return
//     */
//    public boolean isLogin() {
//        user = getUser();
//        if (user == null || TextUtils.isEmpty(user.getTelephone())) {
//            return false;
//        } else {
//            return true;
//        }
//    }
//
//    /**
//     * 获取User
//     */
//    public User getUser() {
//        if (user == null) {
//            Object object = JsonUtils.getObjectFromSharePreferences(
//                    USER_INFO, User.class);
//            if (object instanceof String) {
//                String objString = (String) object;
//                if (objString.equals(IS_NULL)) {
//                    LogUtil.i("获取用户信息为空！");
//                } else {
//                    LogUtil.i("获取的用户信息为空！且有错误！空信息为：" + objString);
//                }
//                user = null;
//            } else if (object instanceof User) {
//                user = (User) object;
//            }
//        }
//        return user;
//    }
//
//    /**
//     * 设置User
//     */
//    public void setUser(User user) {
//        this.user = user;
//        if (null != user) {
//            JsonUtils.saveObjectToSharePreferences(user, USER_INFO);
//        } else {
//            SharedPreferences.Editor editor = sp.edit();
//            editor.remove(USER_INFO);
//            editor.commit();
//        }
//    }
//
//    /**
//     * 获取保存的实时数据
//     *
//     * @return
//     */
//    public AccountInfo getAccountInfo() {
//        if (accountInfo == null) {
//            Object object = JsonUtils.getObjectFromSharePreferences(
//                    ACCOUNT_INFO, AccountInfo.class);
//            if (object instanceof String) {
//                String objString = (String) object;
//                if (objString.equals(IS_NULL)) {
//                    LogUtil.i("账户信息为空！");
//                } else {
//                    LogUtil.i("获取的账户信息为空！且有错误！空信息为：" + objString);
//                }
//                accountInfo = null;
//            } else if (object instanceof AccountInfo) {
//                accountInfo = (AccountInfo) object;
//            }
//        }
//        return accountInfo;
//    }
//
//    /**
//     * 保存飞控参数信息
//     */
//    public void setAccountInfo(AccountInfo accountInfo) {
//        this.accountInfo = accountInfo;
//        if (null != accountInfo) {
//            JsonUtils.saveObjectToSharePreferences(accountInfo, ACCOUNT_INFO);
//        } else {
//            SharedPreferences.Editor editor = sp.edit();
//            editor.remove(ACCOUNT_INFO);
//            editor.commit();
//        }
//    }
//
//    /**
//     * 获取保存的实时数据
//     *
//     * @return
//     */
//    public DeviceNowInfo getDeviceNowInfo() {
//        if (deviceNowInfo == null) {
//            Object object = JsonUtils.getObjectFromSharePreferences(
//                    DEVICE_NOW_INFO, DeviceNowInfo.class);
//            if (object instanceof String) {
//                String objString = (String) object;
//                if (objString.equals(IS_NULL)) {
//                    LogUtil.i("实时数据信息为空！");
//                } else {
//                    LogUtil.i("获取的实时数据信信息为空！且有错误！空信息为：" + objString);
//                }
//                deviceNowInfo = null;
//            } else if (object instanceof DeviceNowInfo) {
//                deviceNowInfo = (DeviceNowInfo) object;
//            }
//        }
//        return deviceNowInfo;
//    }
//
//    /**
//     * 保存实时数据
//     */
//    public void setDeviceNowInfo(DeviceNowInfo deviceNowInfo) {
//        this.deviceNowInfo = deviceNowInfo;
//        if (null != deviceNowInfo) {
//            JsonUtils.saveObjectToSharePreferences(deviceNowInfo, DEVICE_NOW_INFO);
//        } else {
//            SharedPreferences.Editor editor = sp.edit();
//            editor.remove(DEVICE_NOW_INFO);
//            editor.commit();
//        }
//    }


//
//	/**
//	 * 获取User账户信息
//	 */
//	public UserAccount getUserAccount() {
//		if (userAccount == null) {
//			Object object = JsonUtils.getObjectFromSharePreferences(
//					USER_ACCOUNT, UserAccount.class);
//			if (object instanceof String) {
//				String objString = (String) object;
//				if (objString.equals(IS_NULL)) {
//					LogUtils.i(TAG, "获取用户信息为空！");
//				} else {
//					LogUtils.i(TAG, "获取的用户信息为空！且有错误！空信息为：" + objString);
//				}
//				userAccount = null;
//			} else if (object instanceof UserAccount) {
//				userAccount = (UserAccount) object;
//			}
//		}
//		return userAccount;
//	}
//
//	/**
//	 * 设置User账户信息
//	 */
//	public void setUserAccount(UserAccount userAccount) {
//		this.userAccount = userAccount;
//		if (null != userAccount) {
//			JsonUtils.saveObjectToSharePreferences(userAccount, USER_ACCOUNT);
//		} else {
//			SharedPreferences.Editor editor = sp.edit();
//			editor.remove(USER_ACCOUNT);
//			editor.commit();
//		}
//	}
//
//	/**
//	 * 保存用户头像索引
//	 */
//	public void setUserPhotoList(List<UserPhoto> userPhotoList) {
//		this.userPhotoList = userPhotoList;
//		if (null != userPhotoList) {
//			JsonUtils.saveListToSharePreferences(userPhotoList,
//					USER_PHOTO);
//		} else {
//			Editor editor = sp.edit();
//			editor.remove(USER_PHOTO);
//			editor.commit();
//		}
//	}
//
//	/**
//	 * 获取用户头像索引
//	 */
//	public List<UserPhoto> getUserPhotoList() {
//		Type type = new TypeToken<List<UserPhoto>>() {}.getType();
//		List resut = JsonUtils.getListFromSharePreferences(USER_PHOTO, type);
//		return resut;
//	}
//
//	/**
//	 * 获取飞控参数信息
//	 *
//	 * @return
//	 */
//	public LocalSettings getLocalSettings() {
//		if (localSettings == null) {
//			Object object = JsonUtils.getObjectFromSharePreferences(
//					DRONE_SETTING, LocalSettings.class);
//			if (object instanceof String) {
//				String objString = (String) object;
//				if (objString.equals(IS_NULL)) {
//					LogUtils.i(TAG, "飞控参数信息为空！");
//				} else {
//					LogUtils.i(TAG, "获取的飞控参数信息为空！且有错误！空信息为：" + objString);
//				}
//				localSettings = null;
//			} else if (object instanceof LocalSettings) {
//				localSettings = (LocalSettings) object;
//			}
//		}
//		return localSettings;
//	}
//
//	/**
//	 * 保存飞控参数信息
//	 */
//	public void setLocalSettings(LocalSettings localSettings) {
//		this.localSettings = localSettings;
//		if (null != localSettings) {
//			JsonUtils.saveObjectToSharePreferences(localSettings, DRONE_SETTING);
//		} else {
//			SharedPreferences.Editor editor = sp.edit();
//			editor.remove(DRONE_SETTING);
//			editor.commit();
//		}
//	}
//
//
//	/**
//	 * 保存飞机数据信息
//	 */
//	public void setDroneStatusInfoList(List<String> droneStatusInfoList) {
//		this.droneStatusInfoList = droneStatusInfoList;
//		if (null != droneStatusInfoList) {
//			JsonUtils.saveListToSharePreferences(droneStatusInfoList,
//					DRONE_STATUS_INFO);
//		} else { // 删除user信息
//			Editor editor = sp.edit();
//			editor.remove(DRONE_STATUS_INFO);
//			editor.commit();
//		}
//	}
//
//	/**
//	 * 获取飞机数据信息
//	 */
//	public List<String> getDroneStatusInfoList() {
//		Type type = new TypeToken<List<String>>() {}.getType();
//		List resut = JsonUtils.getListFromSharePreferences(DRONE_STATUS_INFO, type);
//		return resut;
//	}
//
//	//保存是否显示飞机状态信息
//	public void setShowStatus(boolean isFirstOpen) {
//		Editor editor = sp.edit();
//		editor.putBoolean(IF_SHOW_DRONE, isFirstOpen);
//		editor.commit();
//	}
//
//	//获取是否显示飞机状态信息
//	public boolean getIfShowStatus() {
//		return sp.getBoolean(IF_SHOW_DRONE, false);
//	}
//
//	/**
//	 * 获取飞机版本信息
//	 *
//	 * @return
//	 */
//	public DroneVersionInfo getDroneVersionInfo() {
//		if (droneVersionInfo == null) {
//			Object object = JsonUtils.getObjectFromSharePreferences(
//					DRONE_VERSION_INFO, DroneVersionInfo.class);
//			if (object instanceof String) {
//				String objString = (String) object;
//				if (objString.equals(IS_NULL)) {
//					LogUtils.i(TAG, "飞机版本信息为空！");
//				} else {
//					LogUtils.i(TAG, "飞机版本信息不空！且有错误！空信息为：" + objString);
//				}
//				droneVersionInfo = null;
//			} else if (object instanceof DroneVersionInfo) {
//				droneVersionInfo = (DroneVersionInfo) object;
//			}
//		}
//		return droneVersionInfo;
//	}
//
//	/**
//	 * 保存飞机版本信息
//	 */
//	public void setDroneVersionInfo(DroneVersionInfo droneVersionInfo) {
//		this.droneVersionInfo = droneVersionInfo;
//		if (null != droneVersionInfo) {
//			JsonUtils.saveObjectToSharePreferences(droneVersionInfo, DRONE_VERSION_INFO);
//		} else {
//			SharedPreferences.Editor editor = sp.edit();
//			editor.remove(DRONE_VERSION_INFO);
//			editor.commit();
//		}
//	}
//
//	/**
//	 * 获取当前本地下载过的最新的飞机版本
//	 *
//	 * @return
//	 */
//	public NativeDroneNewVersion getNativeDroneNewVersion() {
//		if (nativeDroneNewVersion == null) {
//			Object object = JsonUtils.getObjectFromSharePreferences(
//					NATIVE_DRONE_NEW_VERSION, NativeDroneNewVersion.class);
//			if (object instanceof String) {
//				String objString = (String) object;
//				if (objString.equals(IS_NULL)) {
//					LogUtils.i(TAG, "飞机版本信息为空！");
//				} else {
//					LogUtils.i(TAG, "飞机版本信息不空！且有错误！空信息为：" + objString);
//				}
//				nativeDroneNewVersion = null;
//			} else if (object instanceof NativeDroneNewVersion) {
//				nativeDroneNewVersion = (NativeDroneNewVersion) object;
//			}
//		}
//		return nativeDroneNewVersion;
//	}
//
//	/**
//	 * 保存本地下载过的最新的飞机版本
//	 */
//	public void setNativeDroneNewVersion(NativeDroneNewVersion nativeDroneNewVersion) {
//		this.nativeDroneNewVersion = nativeDroneNewVersion;
//		if (null != nativeDroneNewVersion) {
//			JsonUtils.saveObjectToSharePreferences(nativeDroneNewVersion, NATIVE_DRONE_NEW_VERSION);
//		} else {
//			SharedPreferences.Editor editor = sp.edit();
//			editor.remove(NATIVE_DRONE_NEW_VERSION);
//			editor.commit();
//		}
//	}
//
//	/**
//	 * 为了防止飞机照片列表数据过大，无法通过intent传递，而临时性保存下来
//	 */
//	public void setPhotoList(List<GalleryPhoto> photoList) {
//		this.photoList = photoList;
//		if (null != photoList) {
//			JsonUtils.saveListToSharePreferences(photoList,
//					DRONE_PHOTO_LIST);
//		} else { // 删除user信息
//			Editor editor = sp.edit();
//			editor.remove(DRONE_PHOTO_LIST);
//			editor.commit();
//		}
//	}
//
//	/**
//	 * 获取飞机照片列表
//	 */
//	public List<GalleryPhoto> getPhotoList() {
//
//		Type type = new TypeToken<List<GalleryPhoto>>() {}.getType();
//		List resut = JsonUtils.getListFromSharePreferences(DRONE_PHOTO_LIST, type);
//		return resut;
//	}
//
//	/**
//	 * 保存特殊禁飞区域列表
//	 */
//	public void setNoFlyZoneList(List<SpecialNoFlyZone> noFlyZoneList) {
//		this.noFlyZoneList = noFlyZoneList;
//		if (null != noFlyZoneList) {
//			JsonUtils.saveListToSharePreferences(noFlyZoneList,
//					GET_NO_FLY_ZONE_LIST);
//		} else {
//			Editor editor = sp.edit();
//			editor.remove(GET_NO_FLY_ZONE_LIST);
//			editor.commit();
//		}
//	}
//
//	/**
//	 * 获取特殊禁飞区列表
//	 */
//	public List<SpecialNoFlyZone> getNoFlyZoneList() {
//		Type type = new TypeToken<List<SpecialNoFlyZone>>() {}.getType();
//		List resut = JsonUtils.getListFromSharePreferences(GET_NO_FLY_ZONE_LIST, type);
//		return resut;
//	}
//
//	//获取保存的app版本号
//	public String getAppVersion() {
//		return sp.getString(GET_CURRENT_APP_VERSION, "0");
//	}
//
//	//保存当前app版本号
//	public void setAppVersion(String appVersion) {
//		String version = "0";
//		if(!TextUtils.isEmpty(appVersion)){
//			version = appVersion;
//		}
//		Editor editor = sp.edit();
//		editor.putString(GET_CURRENT_APP_VERSION, version);
//		editor.commit();
//	}
//
//	//获取保存的当前语言
//	public String getCurrentLanguage() {
//		return sp.getString(GET_CURRENT_LANGUAGE, "0");
//	}
//
//	//保存当前用户选择的语言
//	public void setCurrentLanguage(String currentLanguage) {
//		String language = "0";
//		if(!TextUtils.isEmpty(currentLanguage)){
//			language = currentLanguage;
//		}
//		Editor editor = sp.edit();
//		editor.putString(GET_CURRENT_LANGUAGE, language);
//		editor.commit();
//	}

}
