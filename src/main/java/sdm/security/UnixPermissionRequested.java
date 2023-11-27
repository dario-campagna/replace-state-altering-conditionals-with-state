package sdm.security;

public class UnixPermissionRequested extends PermissionState {

    public UnixPermissionRequested() {
        super("UNIX_REQUESTED");
    }

    public void claimedBy(SystemAdmin admin, SystemPermission permission) {
        permission.willBeHandedBy(admin);
        permission.setState(PermissionState.UNIX_CLAIMED);
    }
}
