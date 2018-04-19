public class State {
    private FSMRunnable runnable;
    private FSMStoppable stoppable;

    public State(FSMRunnable runnable, FSMStoppable stoppable) {
        this.runnable = runnable;
        this.stoppable = stoppable;
    }

    public State(FSMRunnable runnable) {
        this(runnable, () -> {});
    }

    void run() throws FSMException {
        runnable.run();
    }

    void stop() {
        stoppable.stop();
    }
}

interface FSMRunnable {
    void run() throws FSMException;
}
interface FSMStoppable {
    void stop();
}
