package org.jeecg.modules.cable.api;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.jeecg.modules.cable.entity.hand.Address;
import org.jeecg.modules.cable.tools.CommonFunction;
import org.json.JSONObject;

@Slf4j
public class AddressApi {
    private static final OkHttpClient client = new OkHttpClient().newBuilder().build();

    @SneakyThrows
    public static Address getAddress(String textJson) {
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(
                textJson, mediaType);
        Request request = new Request.Builder()
                .url("https://aip.baidubce.com/rpc/2.0/nlp/v1/address?" +
                        "access_token=" + getToken())
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        String result = response.body().string();
        return CommonFunction.getGson().fromJson(result, Address.class);
    }

    //getToken
    @SneakyThrows
    public static String getToken() {
        FormBody formBodyToken = new FormBody.Builder()
                .add("client_id", "GdMtwn5Zqo1nXoIsUrT0KaPk")
                .add("client_secret", "kXPvCIfdOpAka5zWDRPHDvgSjVKUn3MI")
                .add("grant_type", "client_credentials")
                .build();
        Request request = new Request.Builder()
                .url("https://aip.baidubce.com/oauth/2.0/token")
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .post(formBodyToken)
                .build();
        Response response = client.newCall(request).execute();
        return new JSONObject(response.body().string()).getString("access_token");
    }
}
