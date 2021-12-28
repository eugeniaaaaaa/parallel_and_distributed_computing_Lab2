package lab2;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.pattern.Patterns;

import java.time.Duration;

public class Startup {
    public static void main(String[] args) {
        new Startup().run();
    }


    private final ActorSystem parkingSystem;
    private final ActorRef userService;

    public Startup() {
        parkingSystem = ActorSystem.create("ParkingLog");
        parkingSystem.actorOf(Props.create(ParkingServiceActor.class), "SlotMonitor");
        userService = parkingSystem.actorOf(Props.create(UserServiceActor.class), "Attendant");
    }

    private void run() {
        userService.tell(new ParkingRequest(), ActorRef.noSender());
        userService.tell(new ParkingRequest(), ActorRef.noSender());
        userService.tell(new ParkingRequest(), ActorRef.noSender());
        userService.tell(new ParkingRequest(), ActorRef.noSender());
        userService.tell(new ParkingRequest(), ActorRef.noSender());
        userService.tell(new ParkingRequest(), ActorRef.noSender());
        userService.tell(new ParkingRequest(), ActorRef.noSender());
        userService.tell(new Ticket(2), ActorRef.noSender());
        userService.tell(new ParkingRequest(), ActorRef.noSender());
        userService.tell(new ParkingRequest(), ActorRef.noSender());
        userService.tell(new Ticket(3), ActorRef.noSender());
        userService.tell(new Ticket(4), ActorRef.noSender());
        userService.tell(new ParkingRequest(), ActorRef.noSender());
        userService.tell(new ParkingRequest(), ActorRef.noSender());
        userService.tell(new ParkingRequest(), ActorRef.noSender());

        parkingSystem.terminate();
    }
}
