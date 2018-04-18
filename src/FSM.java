import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class FSM {
    private Map<State, Map<Condition, State>> map = new HashMap<>();
    private State currentState;
    private boolean running = false;
    private Thread flowThread;

    public void start(State state) throws FSMException{
        if(running) throw new FSMException("Already running");
        if(!map.containsKey(state)) throw new FSMException("State not found");
        currentState = state;
        running = true;
        currentState.run();
    }
    public void startFlow(State state) throws FSMException {
        try {
            if (running) throw new FSMException("Already running");
            if (!map.containsKey(currentState)) throw new FSMException("State not found");
            currentState = state;
            if (flowThread == null) {
                flowThread = new Thread();
            }
        }catch (FSMException ex) {
            if("STOP".equals(ex.getMessage())) stop();
        }
    }
    private void runFlow() throws FSMException{
        running = true;
        while (running) {
            currentState.run();
            update();
        }
    }
    public void update() throws FSMException{
        if(running) {
            if(map.get(currentState).isEmpty()) {
                stop();
                return;
            }
            for (Condition condition : map.get(currentState).keySet()) {
                if (condition.evaluate()) {
                    currentState = map.get(currentState).get(condition);
                    break;
                }
            }
        }
        start(currentState);
    }
    public void stop() {
        currentState.stop();
        currentState = null;
        running = false;
    }
    public void addState(State state) {
        if(!map.containsKey(state)) {
            map.put(state, new LinkedHashMap<>());
        }
    }
    public FSM add(State before, Condition condition, State after) {
        addState(before);
        addState(after);
        map.get(before).put(condition, after);
        return this;
    }
    public FSM add(State before, Condition condition, State yes, State no) {
        add(before, condition, yes);
        Condition negated = () -> !condition.evaluate();
        add(before, condition, no);
        return this;
    }
    public Map<State, Map<Condition, State>> getMap() {
        return map;
    }

    public boolean isRunning() {
        return running;
    }
}
