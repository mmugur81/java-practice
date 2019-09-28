package practice;

import java.util.*;
import java.util.stream.Collectors;


public class BloombergTest {
    public static void main(String args[]) throws Exception {
        Scanner scanner = new Scanner(System.in);
        String commands = scanner.next();

        System.out.println(run(commands));
    }

    public static String run(String commands) {
        ParkingLot parkingLot = new ParkingLot();

        List<Command> commandList = extractCommands(commands);
        commandList.forEach(command -> executeCommand(command, parkingLot));

        return parkingLot.display();
    }

    private static List<Command> extractCommands(String commands) {
        return Arrays
                .stream(commands.split(";"))
                .map(Command::fromStringToken)
                .collect(Collectors.toList());
    }

    public static void executeCommand(Command command, ParkingLot parkingLot) {
        switch (command.getType()) {
            case PARK:
                parkingLot.park(command.getCarNumber());
                break;

            case UN_PARK:
                parkingLot.unPark(command.getTicketNumber());
                break;

            case COMPACT:
                parkingLot.compact();
                break;
        }
    }

    private static class ParkingLot {
        private int currentTicketNumber = 5000;
        private Map<Integer, Command> lot = new LinkedHashMap<>();
        private Integer capacity = 10;

        public ParkingLot() {
            for (int i = 0; i < capacity; i++) {
                lot.put(i, null);
            }
        }

        public void park(String carNumber) {
            if (lot.size() == capacity) {
                // Can't park more
                return;
            }

            // find first space
            for (int i = 0; i < 10; i++) {
                if (lot.get(i) == null) {
                    lot.put(currentTicketNumber, new Command(Command.Type.PARK, carNumber));
                }
            }

            currentTicketNumber ++;
        }

        public void unPark(Integer ticketNumber) {
            lot.put(ticketNumber, null);
        }

        public void compact() {
            for (Integer ticketNumber : lot.keySet()) {
                //todo needs refactoring
                if (lot.get(ticketNumber).equals("")) {
                    lot.remove(ticketNumber);
                }
            }
        }

        public String display() {
            Iterator<Map.Entry<Integer, Command>> iterator = lot.entrySet().iterator();
            StringBuilder output = new StringBuilder();

            for (int i = 0; i < 10; i++) {
                if (output.length() > 0) {
                    output.append(',');
                }
                if (iterator.hasNext()) {
                    output .append(iterator.next().getValue());
                }
            }

            return output.toString();
        }
    }


    private static class Command {
        private final Type type;
        private final String carNumber;
        private final Integer ticketNumber;

        public Command(Type type, String carNumberOrTicketNumber) {
            this.type = type;

            if (type == Type.PARK) {
                this.carNumber = carNumberOrTicketNumber;
                this.ticketNumber = 0;
            } else if (type == Type.UN_PARK) {
                this.ticketNumber = Integer.valueOf(carNumberOrTicketNumber);
                this.carNumber = "";
            } else {
                this.carNumber = "";
                this.ticketNumber = 0;
            }
        }

        public static Command fromStringToken(String token) {
            Type type = Type.fromChar(token.charAt(0));
            String carNumberOrTicketNumber = token.substring(1);

            return new Command(type, carNumberOrTicketNumber);
        }

        public Type getType() {
            return type;
        }

        public String getCarNumber() {
            return carNumber;
        }

        public Integer getTicketNumber() {
            return ticketNumber;
        }

        private enum Type {
            PARK('p'),
            UN_PARK('u'),
            COMPACT('c');

            private final char character;

            Type(char c) {
                this.character = c;
            }

            public static Type fromChar(char c) {
                for (Type value : Type.values()) {
                    if (value.getCharacter() == c) {
                        return value;
                    }
                }
                return null;
            }

            public char getCharacter() {
                return character;
            }
        }
    }
}