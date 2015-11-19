package www.nuaa.edu.cn.inanhang.Model;

import java.io.Serializable;

/**
 * @author sun
 * @version 15/9/16
 */
public class Lock implements Serializable {
    private final String lockID;
    private final int lockType;
    private final String lockStartTime;
    private final String lockEndTime;
    private final int hasLockPermission;
    private final String lockName;

    public Lock(String lockID, int lockType, String lockStartTime, String lockEndTime,
                int hasLockPermission, String lockName) {
        this.lockID = lockID;
        this.lockType = lockType;
        this.lockStartTime = lockStartTime;
        this.lockEndTime = lockEndTime;
        this.hasLockPermission = hasLockPermission;
        this.lockName = lockName;
    }

    public String getLockID() {
        return lockID;
    }

    public int getLockType() {
        return lockType;
    }

    public String getLockStartTime() {
        return lockStartTime;
    }

    public String getLockEndTime() {
        return lockEndTime;
    }

    public boolean isHasLockPermission() {
        return hasLockPermission == 1;
    }

    public String getLockName() {
        return lockName;
    }

    @Override
    public String toString() {
        return "{" +
                "lockID='" + lockID + '\'' +
                ", lockType=" + lockType +
                ", lockStartTime='" + lockStartTime + '\'' +
                ", lockEndTime='" + lockEndTime + '\'' +
                ", hasLockPermission=" + hasLockPermission +
                ", lockName=" + hasLockPermission +
                '}';
    }
}
