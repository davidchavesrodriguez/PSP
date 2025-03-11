package gal.dchaves.raffle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SharedRaffle {
    private final List<Participant> participants = new ArrayList<>();

    // Add a participant
    public synchronized void addParticipant(Participant participant) {
        participants.add(participant);
        System.out.println("New participant: " + participant);
    }

    // Pick a winner
    public synchronized Participant selectWinner() {
        if (participants.isEmpty()) {
            return null;
        }

        Random random = new Random();
        int winnerIndex = random.nextInt(participants.size());
        return participants.get(winnerIndex);
    }

    // List participants
    public synchronized List<Participant> getParticipants() {
        return new ArrayList<>(participants);
    }


}
