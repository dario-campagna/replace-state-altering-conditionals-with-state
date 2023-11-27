package sdm.security;

public abstract class PermissionState {

    private final String name;
    public static final PermissionState REQUESTED = new PermissionRequested();
    public static final PermissionState UNIX_REQUESTED = new UnixPermissionRequested();
    public static final PermissionState CLAIMED = new PermissionClaimed();
    public static final PermissionState UNIX_CLAIMED = new UnixPermissionClaimed();
    public static final PermissionState GRANTED = new PermissionGranted();
    public static final PermissionState DENIED = new PermissionDenied();

    protected PermissionState(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public void claimedBy(SystemAdmin admin, SystemPermission permission) {
        if (!permission.getState().equals(PermissionState.REQUESTED) && !permission.getState().equals(PermissionState.UNIX_REQUESTED)) {
            return;
        }
        permission.willBeHandedBy(admin);
        if (permission.getState().equals(PermissionState.REQUESTED)) {
            permission.setState(PermissionState.CLAIMED);
        } else if (permission.getState().equals(PermissionState.UNIX_REQUESTED)) {
            permission.setState(PermissionState.UNIX_CLAIMED);
        }
    }

    public void deniedBy(SystemAdmin admin, SystemPermission permission) {
        if (!permission.getState().equals(PermissionState.CLAIMED) && !permission.getState().equals(PermissionState.UNIX_CLAIMED)) {
            return;
        }
        if (!permission.getAdmin().equals(admin)) {
            return;
        }
        permission.setIsGranted(false);
        permission.setIsUnixPermissionGranted(false);
        permission.setState(PermissionState.DENIED);
        permission.notifyUserOfPermissionRequestResult();
    }

    public void grantedBy(SystemAdmin admin, SystemPermission permission) {
        if (!permission.getState().equals(PermissionState.CLAIMED) && !permission.getState().equals(PermissionState.UNIX_CLAIMED)) {
            return;
        }
        if (!permission.getAdmin().equals(admin)) {
            return;
        }
        if (permission.getProfile().isUnixPermissionRequired() && permission.getState().equals(PermissionState.UNIX_CLAIMED)) {
            permission.setIsUnixPermissionGranted(true);
        } else if (permission.getProfile().isUnixPermissionRequired() && !permission.isUnixPermissionGranted()) {
            permission.setState(PermissionState.UNIX_REQUESTED);
            permission.notifyUnixAdminOfPermissionRequest();
            return;
        }
        permission.setState(PermissionState.GRANTED);
        permission.setIsGranted(true);
        permission.notifyUserOfPermissionRequestResult();
    }
}
