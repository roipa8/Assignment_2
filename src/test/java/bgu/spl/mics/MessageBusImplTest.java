package bgu.spl.mics;

import bgu.spl.mics.application.messages.AttackEvent;
import bgu.spl.mics.application.messages.testEvent;
import bgu.spl.mics.application.services.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MessageBusImplTest {
    MessageBus messagebus;

    @Before
    public void setUp() throws Exception {
        messagebus=new MessageBusImpl();
    }

    @After
    public void tearDown() throws Exception {
        messagebus=null;
    }

    @Test
    public void subscribeEvent() {
    }

    @Test
    public void subscribeBroadcast() {
    }

    @Test
    public void complete() {
        testEvent e=new testEvent(2);
        MicroService m1=new TestMicroService();
        MicroService m2=new TestMicroService();
        Future<Integer> f=m1.sendEvent(e);
        C3POMicroservice m3=new C3POMicroservice();
        messagebus.register(m3);
        messagebus.subscribeEvent(AttackEvent.class,m3);
        Message msg=messagebus.awaitMessage(m3);
    }

    @Test
    public void sendBroadcast() {
    }

    @Test
    public void sendEvent() {
        Event<Integer> e=new testEvent();
        MicroService C3PO=new C3POMicroservice();
        MicroService Han=new HanSoloMicroservice();

        Future<Integer> f=Han.sendEvent(e);
    }

    @Test
    public void register() {
    }

    @Test
    public void unregister() {
    }

    @Test
    public void awaitMessage() {
    }
}