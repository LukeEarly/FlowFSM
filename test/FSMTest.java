import java.util.Random;

public class FSMTest {
    public static void main(String[] args) throws FSMException{
        FSM fsm = new FSM(FSM.EndProtocol.REPEAT_STATE);

        State state1 = new State(){
            @Override
            public void run() {
                doSomething();
            }
            public void stop() {}
        }; //anonymous class
        Condition condition = new Condition(FSMTest::checkFailure); //method reference
        State state2 = new State(() -> doSomethingElse(5)); //lambda

        fsm.add(state1, condition, state2); //You can use anonymous classes, lambdas, or method references.

        //Condition negatedCondition = () -> !checkFailure()
        //fsm.add(state1, condition, state1); //These 2 lines aren't needed as it defaults to staying in its old state because of the EndProtocol above

        for (int i = 0; i < 3; i++) {
            fsm.start(state1);
            for (int j = 0; j < 5; j++) {
                try {
                    Thread.sleep(500);
                    System.out.println();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                fsm.update();
                fsm.start();
                if(!fsm.isRunning()) {
                    System.out.println("done");
                    break;
                }
            }
        }
    }
    public static void doSomething() {System.out.println("Something is done");}
    public static boolean checkFailure() {
        Random random = new Random();
        boolean b = random.nextBoolean();
        System.out.println(b);
        if(!b) {
            System.out.println("It failed! Trying again...");
            return false;
        }
        return true;
    }
    public static void doSomethingElse(int rating) {System.out.println("Something else is done. Rated " + rating + " stars!");}
}
