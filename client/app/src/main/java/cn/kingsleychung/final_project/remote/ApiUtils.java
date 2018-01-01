package cn.kingsleychung.final_project.remote;

/**
 * Created by zhougb3 on 2017/12/23.
 */

public class ApiUtils {

    private ApiUtils() {}

    public static final String BASE_URL = "http://139.199.59.246:8000/";

    public static APIService getAPIService() {

        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }
}
