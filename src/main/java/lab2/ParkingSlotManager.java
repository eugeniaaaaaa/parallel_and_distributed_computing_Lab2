package lab2;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Stream;

public class ParkingSlotManager {
    private static final ParkingSlotManager manager = new ParkingSlotManager();

    public static ParkingSlotManager getManager() {
        return manager;
    }

    private final AtomicBoolean[] parkingSlots = Stream.generate(AtomicBoolean::new).limit(5).toArray(AtomicBoolean[]::new);

    public int assignFreeSlot() throws NoFreeSlotException {
        for (int i = 0; i < parkingSlots.length; i++) {
            if (parkingSlots[i].compareAndSet(false, true)) {
                return i;
            }
        }
        throw new NoFreeSlotException("Parking is full, please, try later");
    }


    public void freeSlot(int slotIdx) {
        parkingSlots[slotIdx].set(false);
    }
}
