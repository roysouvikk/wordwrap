package com.test.wrapper;
import com.test.wrapper.StringWrapper;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestStringWrapper {

    @Test
    public void TestWrapperForValidLongStr(){
        StringWrapper st = new StringWrapper();

        st.maxLength = 23;
        String out = st.wrapAString("Design a word wrap micro service which provides functionality.");
        String expected = "Design a word wrap " + System.lineSeparator() + "micro service which " +
                System.lineSeparator() + "provides functionality.";
        assertEquals(out, expected);
    }

    @Test
    public void TestWrapperForNullString(){
        StringWrapper st = new StringWrapper();

        st.maxLength = 23;
        String out = st.wrapAString(null);
        String expected = null;
        assertEquals(out, expected);
    }

    @Test
    public void TestWrapperForShortString(){
        StringWrapper st = new StringWrapper();

        st.maxLength = 23;
        String out = st.wrapAString("test a string");
        String expected = "test a string";
        assertEquals(out, expected);
    }

}
