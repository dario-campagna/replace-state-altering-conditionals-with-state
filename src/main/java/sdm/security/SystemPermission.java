package sdm.security;

public class SystemPermission {

    private final SystemProfile profile;
    private final SystemUser user;
    private SystemAdmin admin;
    private boolean isGranted;
    private boolean isUnixPermissionGranted;
    private String state;

    public static final String REQUESTED = "REQUESTED";
    public static final String UNIX_REQUESTED = "UNIX_REQUESTED";
    public static final String CLAIMED = "CLAIMED";
    public static final String UNIX_CLAIMED = "UNIX_CLAIMED";
    public static final String GRANTED = "GRANTED";
    public static final String DENIED = "DENIED";

    public SystemPermission(SystemUser requester, SystemProfile profile) {
        this.user = requester;
        this.profile = profile;
        state = REQUESTED;
        isGranted = false;
        isUnixPermissionGranted = false;
        notifyAdminOfPermissionRequest();
    }

    public void claimedBy(SystemAdmin admin) {
        if (!state.equals(REQUESTED) && !state.equals(UNIX_REQUESTED)) {
            return;
        }
        willBeHandedBy(admin);
        if (state.equals(REQUESTED)) {
            state = CLAIMED;
        } else if (state.equals(UNIX_REQUESTED)) {
            state = UNIX_CLAIMED;
        }
    }

    public void deniedBy(SystemAdmin admin) {
        if (!state.equals(CLAIMED) && !state.equals(UNIX_CLAIMED)) {
            return;
        }
        if (!this.admin.equals(admin)) {
            return;
        }
        isGranted = false;
        isUnixPermissionGranted = false;
        state = DENIED;
        notifyUserOfPermissionRequestResult();
    }

    public void grantedBy(SystemAdmin admin) {
        if (!state.equals(CLAIMED) && !state.equals(UNIX_CLAIMED)) {
            return;
        }
        if (!this.admin.equals(admin)) {
            return;
        }
        if (profile.isUnixPermissionRequired() && state.equals(UNIX_CLAIMED)) {
            isUnixPermissionGranted = true;
        } else if (profile.isUnixPermissionRequired() && !isUnixPermissionGranted()) {
            state = UNIX_REQUESTED;
            notifyUnixAdminOfPermissionRequest();
            return;
        }
        state = GRANTED;
        isGranted = true;
        notifyUserOfPermissionRequestResult();
    }

    public String getState() {
        return state;
    }

    public boolean isGranted() {
        return isGranted;
    }

    public boolean isUnixPermissionGranted() {
        return isUnixPermissionGranted;
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
        user.notifyRequestResult(state);
    }
}
