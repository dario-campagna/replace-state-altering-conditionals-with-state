package sdm.security;

public class PermissionClaimed extends PermissionState{
    protected PermissionClaimed() {
        super("CLAIMED");
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
        if (permission.getProfile().isUnixPermissionRequired() && !permission.isUnixPermissionGranted()) {
            permission.setState(PermissionState.UNIX_REQUESTED);
            permission.notifyUnixAdminOfPermissionRequest();
            return;
        }
        permission.setState(PermissionState.GRANTED);
        permission.setIsGranted(true);
        permission.notifyUserOfPermissionRequestResult();
    }
}
