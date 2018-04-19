public class Condition {
    private FSMConditional fsmConditional;
    public State yes;
    public State no;
    public Condition(FSMConditional fsmConditional) {
        this.fsmConditional = fsmConditional;
    }
    public boolean evaluate() {
        return fsmConditional.evaluate();
    }
}

interface FSMConditional {
    boolean evaluate();
}
