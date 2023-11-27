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
        if (!getState().equals(PermissionState.REQUESTED) && !getState().equals(PermissionState.UNIX_REQUESTED)) {
            return;
        }
        willBeHandedBy(admin);
        if (getState().equals(PermissionState.REQUESTED)) {
            setState(PermissionState.CLAIMED);
        } else if (getState().equals(PermissionState.UNIX_REQUESTED)) {
            setState(PermissionState.UNIX_CLAIMED);
        }
    }

    public void deniedBy(SystemAdmin admin) {
        if (!getState().equals(PermissionState.CLAIMED) && !getState().equals(PermissionState.UNIX_CLAIMED)) {
            return;
        }
        if (!this.admin.equals(admin)) {
            return;
        }
        isGranted = false;
        isUnixPermissionGranted = false;
        setState(PermissionState.DENIED);
        notifyUserOfPermissionRequestResult();
    }

    public void grantedBy(SystemAdmin admin) {
        if (!getState().equals(PermissionState.CLAIMED) && !getState().equals(PermissionState.UNIX_CLAIMED)) {
            return;
        }
        if (!this.admin.equals(admin)) {
            return;
        }
        if (profile.isUnixPermissionRequired() && getState().equals(PermissionState.UNIX_CLAIMED)) {
            isUnixPermissionGranted = true;
        } else if (profile.isUnixPermissionRequired() && !isUnixPermissionGranted()) {
            setState(PermissionState.UNIX_REQUESTED);
            notifyUnixAdminOfPermissionRequest();
            return;
        }
        setState(PermissionState.GRANTED);
        isGranted = true;
        notifyUserOfPermissionRequestResult();
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

    private void setState(PermissionState permissionState) {
        this.permissionState = permissionState;
    }

    private void notifyAdminOfPermissionRequest() {
        //...
    }

    private void willBeHandedBy(SystemAdmin admin) {
        this.admin = admin;
    }

    private void notifyUnixAdminOfPermissionRequest() {
        //...
    }

    private void notifyUserOfPermissionRequestResult() {
        user.notifyRequestResult(getState());
    }
    
}
