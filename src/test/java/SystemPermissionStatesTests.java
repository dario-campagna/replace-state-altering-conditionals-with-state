import org.junit.jupiter.api.Test;
import sdm.security.SystemAdmin;
import sdm.security.SystemPermission;
import sdm.security.SystemUser;
import sdm.security.UnixSystemAdmin;

import static org.junit.jupiter.api.Assertions.*;

public class SystemPermissionStatesTests {

    private final SystemPermission simplePermission = new SystemPermission(new SystemUser(), () -> false);
    private final SystemAdmin admin = new SystemAdmin();

    @Test
    void grantedByUNIXPermissionNotRequired() {
        simplePermission.grantedBy(admin);
        assertEquals(SystemPermission.REQUESTED, simplePermission.getState(), "requested");
        assertFalse(simplePermission.isGranted(), "not granted");
        simplePermission.claimedBy(admin);
        simplePermission.grantedBy(admin);
        assertEquals(SystemPermission.GRANTED, simplePermission.getState(), "granted");
        assertTrue(simplePermission.isGranted(), "granted");
    }

    @Test
    void deniedBy() {
        simplePermission.claimedBy(admin);
        assertEquals(SystemPermission.CLAIMED, simplePermission.getState(), "claimed");
        simplePermission.deniedBy(admin);
        assertEquals(SystemPermission.DENIED, simplePermission.getState(), "denied");
        assertFalse(simplePermission.isGranted(), "not granted");
    }

    @Test
    void grantedByUNIXPermissionRequired() {
        SystemPermission unixPermission = new SystemPermission(new SystemUser(), () -> true);
        unixPermission.grantedBy(admin);
        assertEquals(SystemPermission.REQUESTED, unixPermission.getState(), "requested");
        assertFalse(unixPermission.isGranted(), "not granted");
        unixPermission.claimedBy(admin);
        assertEquals(SystemPermission.CLAIMED, unixPermission.getState(), "claimed");
        unixPermission.grantedBy(admin);
        assertEquals(SystemPermission.UNIX_REQUESTED, unixPermission.getState(),"unix requested");
        UnixSystemAdmin unixSystemAdmin = new UnixSystemAdmin();
        unixPermission.claimedBy(unixSystemAdmin);
        assertEquals(SystemPermission.UNIX_CLAIMED, unixPermission.getState(), "unix claimed");
        unixPermission.grantedBy(unixSystemAdmin);
        assertEquals(SystemPermission.GRANTED, unixPermission.getState(), "granted");
        assertTrue(unixPermission.isGranted(), "granted");
        assertTrue(unixPermission.isUnixPermissionGranted(), "unix granted");
    }
}
