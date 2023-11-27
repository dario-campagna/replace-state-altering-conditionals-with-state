package sdm.security;

public class PermissionRequested extends PermissionState {

    public PermissionRequested() {
        super("REQUESTED");
    }

    public void claimedBy(SystemAdmin admin, SystemPermission permission) {
        permission.willBeHandedBy(admin);
        permission.setState(PermissionState.CLAIMED);
    }
}
