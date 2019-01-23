package com.pragmatic;
import org.testng.annotations.*;




public class TestForControllers {

    @BeforeClass
    public void beforeClsaa() {
        System.out.println("Before customer controller");
    }

    @AfterClass
    public void afterClsaa() {
        System.out.println("after customer controller");
    }

    @BeforeMethod
    public void beforeMethod ()    {
        System.out.println("before admin controlle");
    }

    @Test
    public void testMethod1 () {
        System.out.println("After admin controller");
    }

    @Test
    public void testMethod2 () {
        System.out.println("before vehicle controller");
    }

    @Test
    public void testMethod3 () {
        System.out.println("after vehicle controller");
    }

    @Test
    public void testMethod4 () {
        System.out.println("before part controller");
    }

    @AfterMethod
    public void afterMethod () {
        System.out.println("after part controller");
    }


}
