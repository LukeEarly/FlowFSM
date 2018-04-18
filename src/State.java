public class State {
    private Runnable runnable;
    private Runnale stopable;
    public State(Runnable runnable, Runnable stoppable) {
        this.runnable = runnable;
        this.stopable = stoppable;
    }
    public State(Runnable runnable) {
        Runnable stoppable = () -> ;
        this(runnable, stoppable;
    }
    void run() throws FSMException {
        runnable.run();
    }
    void stop(){
        stoppable.run();
    }
}
