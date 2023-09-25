package VirtualComputer.Hardware;

public class Disk {

    private final String PREFIX = "[DISK]";
    private Motherboard motherboard;

    public Disk(Motherboard mb) {
        motherboard = mb;
        debug("Constructing...");
    }

    public Motherboard getMotherboard() {
        return motherboard;
    }

    public void setMotherboard(Motherboard mb) {
        motherboard = mb;
    }

    private void out(String n) {
        System.out.println(PREFIX + " " + n);
    }

    private void debug(String n) {
        if( motherboard.isDebugMode() ) System.out.println("[DEBUG] " + PREFIX + " " + n);
    }

}
