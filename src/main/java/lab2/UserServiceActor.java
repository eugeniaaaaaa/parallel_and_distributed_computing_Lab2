package lab2;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.pattern.Patterns;

import java.time.Duration;

public class UserServiceActor extends AbstractActor {
    private final ActorRef parkingManager = getContext().actorOf(Props.create(ParkingServiceActor.class));

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(ParkingRequest.class, parkingRequest -> {
                    Object response = Patterns.ask(parkingManager, parkingRequest, Duration.ofSeconds(5)).toCompletableFuture().get();
                    if (response instanceof Ticket) {
                        System.out.println("Got ticket #" + ((Ticket)response).getTicketNumber());
                    } else {
                        System.out.println(response);
                    }
                })
                .match(Ticket.class, ticket -> {
                    parkingManager.tell(ticket, getSelf());
                })
                .build();

    }
}
