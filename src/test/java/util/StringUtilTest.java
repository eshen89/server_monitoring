package util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class StringUtilTest {

    @Test
    public void testConvertStringToInt(){
        assertSame(StringUtil.convertStringToInt("1"), 1);

        try{
            StringUtil.convertStringToInt("dummy");
            fail();
        }catch (NumberFormatException e){
            assertThat(e.getMessage(), is("For input string: \"dummy\""));
        }
    }

    @Test
    public void testConvertStringToUrl(){
        try {
            assertThat(StringUtil.convertStringToUrl("production"), is(Constant.PRODUCTION_URL + Constant.VERIFICATION_API_ENDPOINT));
            assertThat(StringUtil.convertStringToUrl("debugging_proxy"), is(Constant.DEBUGGING_PROXY_URL + Constant.VERIFICATION_API_ENDPOINT));
            assertThat(StringUtil.convertStringToUrl("mock_server"), is(Constant.MOCK_SERVER_URL + Constant.VERIFICATION_API_ENDPOINT));
        } catch (Exception e) {
            e.printStackTrace();
        }

        try{
            StringUtil.convertStringToUrl("anyString");
            fail();
        } catch (Exception e) {
            assertThat(e.getMessage(), is("Invalid server host."));
        }
    }

}
