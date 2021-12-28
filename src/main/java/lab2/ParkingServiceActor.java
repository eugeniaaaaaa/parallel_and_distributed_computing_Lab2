package lab2;

import akka.actor.AbstractActor;

public class ParkingServiceActor extends AbstractActor {
    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(ParkingRequest.class, parkingRequest -> {
                    try {
                        final int slotId = ParkingSlotManager.getManager().assignFreeSlot();
                        getSender().tell(new Ticket(slotId + 1), getSelf());
                    } catch (NoFreeSlotException e) {
                        getSender().tell(e.getMessage(), getSelf());
                    }
                })
                .match(Ticket.class, ticket -> {
                    ParkingSlotManager.getManager().freeSlot(ticket.getTicketNumber() - 1);
                    System.out.println("Parking slot #" + ticket.getTicketNumber() + " is now free");
                })
                .build();
    }
}
