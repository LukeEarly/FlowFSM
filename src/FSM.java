import java.util.ArrayList;

public class FSM {
    private ArrayList<State> states = new ArrayList<>();
    private State currentState;
    private boolean running = false;
    private Thread flowThread;
    public enum EndProtocol {
        REPEAT_STATE, REPEAT_CHECK, STOP
    }
    private EndProtocol endProtocol;
    public FSM(EndProtocol endProtocol) {
        this.endProtocol = endProtocol;
    }
    public void start(State state) throws FSMException{
        if(running) throw new FSMException("Already running");
        if(!states.contains(state)) throw new FSMException("State not found");
        currentState = state;
        running = true;
        currentState.run();
    }
    public void start() throws FSMException {start(currentState);}
    public void startFlow(State state) {
        try {
            if (running) throw new FSMException("Already running");
            if (!states.contains(state)) throw new FSMException("State not found");
            currentState = state;
            if (flowThread == null) {
                flowThread = new Thread(this::runFlow);
                flowThread.start();
            }
        }catch (FSMException ex) {ex.printStackTrace();}
    }
    public void runFlow() {
        try {
            running = true;
            while (running) {
                currentState.run();
                update();
            }
        }catch (FSMException ex) {
            if("STOP".equals(ex.getMessage())) stop();
            else ex.printStackTrace();
        }
    }
    public void update() {
        State oldState = currentState;
        if(running) {
            if(currentState.conditions.isEmpty()) {
                stop();
                return;
            }
            for (Condition condition : currentState.conditions) {
                if (condition.evaluate()) {
                    currentState = condition.yes;
                    return;
                }else if(condition.no != null) {
                    currentState = condition.no;
                    return;
                }
            }
        }
        if(oldState.equals(currentState)) {
            if(endProtocol == EndProtocol.STOP) {
                stop();
            }
            if(endProtocol == EndProtocol.REPEAT_STATE) {
                return;
            }
            if(endProtocol == EndProtocol.REPEAT_CHECK) {
                update();
            }
        }
    }
    public void stop() {
        currentState.stop();
        currentState = null;
        running = false;
    }
    public void addState(State state) {
        if(!states.contains(state)) {
            states.add(state);
        }
    }
    public FSM add(State before, Condition condition, State after) {
        addState(before);
        addState(after);
        before.conditions.add(condition);
        condition.yes = after;
        return this;
    }
    public FSM add(State before, Condition condition, State yes, State no) {
        add(before, condition, yes);
        condition.no = no;
        return this;
    }
    public FSM add(State before, State after) {
        add(before, new Condition(() -> true), after);
        return this;
    }
    public ArrayList getStates() {
        return states;
    }

    public boolean isRunning() {
        return running;
    }

    public void setDefaultStop(FSMStoppable stoppable) {
        for(State s:states) {
            s.setDefaultStoppable(stoppable);
        }
    }
}
