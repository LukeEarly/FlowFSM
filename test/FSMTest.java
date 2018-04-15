import java.util.Random;

public class FSMTest {
    public static void main(String[] args) throws FSMException{
        FSM fsm = new FSM();

        State state1 = new State() {
            @Override
            public void enter() {
                doSomething();
            }
        }; //anonymous class
        Condition condition = FSMTest::checkFailure; //method reference
        State state2 = () -> doSomethingElse(5); //lambda

        fsm.add(state1, condition, state2); //You can use anonymous classes, lambdas, or method references.

        //Condition negatedCondition = () -> !checkFailure()
        //fsm.add(state1, condition, state1); //These 2 lines isn't needed as it defaults to staying in its old state

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
