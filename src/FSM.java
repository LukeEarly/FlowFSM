import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class FSM {
    private Map<State, Map<Condition, State>> map = new HashMap<>();
    private State currentState;
    private boolean running = false;

    public void start(State state) throws FSMException{
        if(!map.containsKey(state)) throw new FSMException("State not found");
        currentState = state;
        running = true;
        currentState.run();
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
        currentState = null;
        running = false;
    }
    public void addState(State state) {
        if(!map.containsKey(state)) {
            map.put(state, new LinkedHashMap<>());
        }
    }
    public void add(State before, Condition condition, State after) {
        addState(before);
        addState(after);
        map.get(before).put(condition, after);
    }
    public Map<State, Map<Condition, State>> getMap() {
        return map;
    }

    public boolean isRunning() {
        return running;
    }
}
