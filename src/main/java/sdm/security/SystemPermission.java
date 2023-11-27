package sdm.security;

public class SystemPermission {

    private final SystemProfile profile;
    private final SystemUser user;
    private SystemAdmin admin;
    private boolean isGranted;
    private boolean isUnixPermissionGranted;
    private PermissionState permissionState;
    
    public SystemPermission(SystemUser requester, SystemProfile profile) {
        this.user = requester;
        this.profile = profile;
        setState(PermissionState.REQUESTED);
        isGranted = false;
        isUnixPermissionGranted = false;
        notifyAdminOfPermissionRequest();
    }

    public void claimedBy(SystemAdmin admin) {
        permissionState.claimedBy(admin, this);
    }

    public void deniedBy(SystemAdmin admin) {
        permissionState.deniedBy(admin, this);
    }

    public void grantedBy(SystemAdmin admin) {
        permissionState.grantedBy(admin, this);
    }

    public PermissionState getState() {
        return permissionState;
    }

    public boolean isGranted() {
        return isGranted;
    }

    public boolean isUnixPermissionGranted() {
        return isUnixPermissionGranted;
    }

    public SystemAdmin getAdmin() {
        return admin;
    }

    public SystemProfile getProfile() {
        return profile;
    }

    public void setIsGranted(boolean isGranted) {
        this.isGranted = isGranted;
    }

    public void setIsUnixPermissionGranted(boolean isUnixPermissionGranted) {
        this.isUnixPermissionGranted = isUnixPermissionGranted;
    }

    void setState(PermissionState permissionState) {
        this.permissionState = permissionState;
    }

    void willBeHandedBy(SystemAdmin admin) {
        this.admin = admin;
    }

    void notifyUnixAdminOfPermissionRequest() {
        //...
    }

    void notifyUserOfPermissionRequestResult() {
        user.notifyRequestResult(getState());
    }

    private void notifyAdminOfPermissionRequest() {
        //...
    }
}
