# RepositoryPattern
Android project demo MVVM, Repository, Retrofit, Room, Rx

## Add Client Request

```
val httpClient = OkHttpClient.Builder()
httpClient.addInterceptor(object: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response{
        var request  = chain.request().newBuilder()
                .addHeader("Authorization", "Token "+ UserManager.retrieveUserToken())
                .build()
        return chain.proceed(request)
    }
})
```

## Retrofit Add

```
.client(httpClient.build())
```
