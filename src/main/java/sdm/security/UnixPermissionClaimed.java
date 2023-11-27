package sdm.security;

public class UnixPermissionClaimed extends PermissionState {
    protected UnixPermissionClaimed() {
        super("UNIX_CLAIMED");
    }

    @Override
    public void deniedBy(SystemAdmin admin, SystemPermission permission) {
        if (!permission.getAdmin().equals(admin)) {
            return;
        }
        permission.setIsGranted(false);
        permission.setIsUnixPermissionGranted(false);
        permission.setState(PermissionState.DENIED);
        permission.notifyUserOfPermissionRequestResult();
    }

    @Override
    public void grantedBy(SystemAdmin admin, SystemPermission permission) {
        if (!permission.getAdmin().equals(admin)) {
            return;
        }
        permission.setIsUnixPermissionGranted(true);
        permission.setState(PermissionState.GRANTED);
        permission.setIsGranted(true);
        permission.notifyUserOfPermissionRequestResult();
    }
}
