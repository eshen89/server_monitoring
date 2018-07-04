package util;

public class StringUtil {

    public static String convertStringToUrl(String url) throws Exception{

        String server_host = null;

        switch (url){
            case "production": server_host = Constant.PRODUCTION_URL;
            break;
            case "debugging_proxy": server_host = Constant.DEBUGGING_PROXY_URL;
            break;
            case "mock_server": server_host = Constant.MOCK_SERVER_URL;
            break;
        }

        if(null == server_host) throw new Exception("Invalid server host.");

        return server_host + Constant.VERIFICATION_API_ENDPOINT;
    }

    public static int convertStringToInt(String interval){

        return Integer.valueOf(interval);
    }

}
