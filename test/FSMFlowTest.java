import java.util.Random;

public class FSMFlowTest {
        static FSM fsm;
        public static void main(String[] args) {
            State state1 = new State(FSMFlowTest::doSomething1);
            State state2 = new State(FSMFlowTest::doSomething2);
            State state3 = new State(FSMFlowTest::doSomething3);
            State state4 = new State(FSMFlowTest::doSomething4);
            State state5 = new State(FSMFlowTest::doSomething5);
            //state1.setName("State1");
            //state2.setName("State2");
            //state3.setName("State3");
            //state4.setName("State4");
            //state5.setName("State5");
            Condition condition1 = new Condition(FSMFlowTest::checkSomething1);
            Condition condition2 = new Condition(FSMFlowTest::checkSomething2);
            fsm = new FSM(FSM.EndProtocol.STOP);
            fsm.add(state1, condition1, state2, state3)
                    .add(state2, state4)
                    .add(state3, condition2, state5, state3)
                    .startFlow(state1);
    }
    public static void doSomething1() {
        System.out.println("1");
    }
    public static void doSomething2() {
        System.out.println("2");
    }
    public static void doSomething3() {
        System.out.println("3");
    }
    public static void doSomething4() throws FSMException {
        System.out.println("4");
        throw new FSMException("STOP");
    }
    public static void doSomething5() throws FSMException {
        System.out.println("5");
        throw new FSMException("STOP");
    }
    public static boolean checkSomething1() {
        Random random = new Random();
        boolean bool = random.nextBoolean();
        //bool = false;
        System.out.println("checkSomething1: " + bool);
        return bool;
    }
    public static boolean checkSomething2() {
        Random random = new Random();
        boolean bool = random.nextBoolean();
        System.out.println("checkSomething2: " + bool);
        return bool;
    }
    public static void waitToStop() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        fsm.stop();
    }

    /*
    |------------------------------------- FLOW CHART --------------------------------------------|
    |  |--------|         /----------\         |--------|        |--------|                       |
    |  | state1 | -----> ( condition1 ) -YES-> | state2 | -----> | state4 | ---> STOP             |
    |  |--------|         \----------/         |--------|        |--------|                       |
    |                         \      |--------|         /----------\         |--------|           |
    |                          NO--> | state3 | -----> ( condition2 ) -YES-> | state5 | ---> STOP |
    |                                |--------|         \----------/         |--------|           |
    |                                      \                   /                                  |
    |                                       <-----<-----<-----NO                                  |
    |---------------------------------------------------------------------------------------------|
    */
}
