package www.nuaa.edu.cn.inanhang.Model;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 配置
 *
 * @author sun
 * @version 15/9/7
 */
public final class Preferences {
    private static final String SP_NAME = "nm-preferences";

    private final SharedPreferences mPreferences;
    private final User mUser;

    public Preferences(Context context, User user) {
        this.mPreferences = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        this.mUser = user;
        this.mUser.setUsername(getUsername());
        this.mUser.setPassword(getPassword());
        this.mUser.setMsgPush(isMsgPush());
    }

    /**
     * app是否是初次使用
     *
     * @return true表示初次使用
     */
    public boolean isUsed() {
        return mPreferences.getBoolean("first_use", false);
    }

    public void setUsed() {
        mPreferences.edit().putBoolean("first_use", true).apply();
    }

    /**
     * 获取用户名
     */
    public String getUsername() {
        return mPreferences.getString("username", null);
    }

    public void setUsername(String username) {
        mUser.setUsername(username);
        mPreferences.edit().putString("username", username).apply();
    }

    /**
     * 获取密码
     */
    public String getPassword() {
        return mPreferences.getString("password", null);
    }

    public void setPassword(String password) {
        mUser.setPassword(password);
        mPreferences.edit().putString("password", password).apply();
    }

    /**
     * 是否启用消息推送
     *
     * @return true表示启用
     */
    public boolean isMsgPush() {
        return mPreferences.getBoolean("is_push", true);
    }

    /**
     * 设置信息推送
     *
     * @param b true表示启用
     */
    public void setMsgPush(boolean b) {
        mUser.setMsgPush(b);
        mPreferences.edit().putBoolean("is_push", b).apply();
    }

    // 清除账号信息
    public void clearAccountInfo() {
        mPreferences.edit().remove("password").remove("is_push").apply();
    }
}
