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
}
