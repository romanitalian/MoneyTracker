package net.romanitalian.moneytrackerapp.rest;

import org.androidannotations.annotations.rest.Get;
import org.androidannotations.annotations.rest.Rest;

@Rest(rootUrl = "http://62.109.17.114/", converters = MessageConverter.class, interceptors = AuthInterceptor.class)
public interface RestClient {
    @Get("/auth?login={login}&password={password}")
    AuthResult login(CharSequence login, CharSequence password);

    @Get("/transactions")
    TransactionsResult getTransactions();
}
