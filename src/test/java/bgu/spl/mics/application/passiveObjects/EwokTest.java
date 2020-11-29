package bgu.spl.mics.application.passiveObjects;

import static org.junit.Assert.*;

public class EwokTest {
    Ewok ewok;

    @org.junit.Before
    public void setUp() throws Exception {
        ewok=new Ewok(0,true);
    }

    @org.junit.After
    public void tearDown() throws Exception {
        ewok=null;
    }

    @org.junit.Test
    public void acquire() {
        ewok.acquire();
        assertFalse(ewok.available);
    }

    @org.junit.Test
    public void release() {
        ewok.available = false;
        ewok.release();
        assertTrue(ewok.available);
    }
}