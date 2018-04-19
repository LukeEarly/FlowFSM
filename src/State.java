import java.util.ArrayList;

public class State {
    private FSMRunnable runnable;
    private FSMStoppable stoppable;
    private FSMStoppable defaultStoppable = () -> {};
    private String name;
    public ArrayList<Condition> conditions = new ArrayList<>();
    public State(FSMRunnable runnable, FSMStoppable stoppable) {
        this.runnable = runnable;
        this.stoppable = stoppable;
    }

    public State(FSMRunnable runnable) {
        this(runnable, () -> {});
    }

    public State() {
        this(() -> {});
    }

    void run() throws FSMException {
        runnable.run();
    }

    void stop() {
        stoppable.stop();
        defaultStoppable.stop();
    }
    public void setRunnable(FSMStoppable stoppable) {
        this.runnable = runnable;
    }
    public void setStoppable(FSMStoppable stoppable) {
        this.stoppable = stoppable;
    }

    public void setDefaultStoppable(FSMStoppable defaultStoppable) {
        this.defaultStoppable = defaultStoppable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

interface FSMRunnable {
    void run() throws FSMException;
}
interface FSMStoppable {
    void stop();
}
