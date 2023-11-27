package sdm.security;

public class PermissionState {

    private final String name;
    public static final PermissionState REQUESTED = new PermissionState("REQUESTED");
    public static final PermissionState UNIX_REQUESTED = new PermissionState("UNIX_REQUESTED");
    public static final PermissionState CLAIMED = new PermissionState("CLAIMED");
    public static final PermissionState UNIX_CLAIMED = new PermissionState("UNIX_CLAIMED");
    public static final PermissionState GRANTED = new PermissionState("GRANTED");
    public static final PermissionState DENIED = new PermissionState("DENIED");

    private PermissionState(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
