package net.romanitalian.moneytrackerapp.rest;

import org.androidannotations.annotations.rest.Get;
import org.androidannotations.annotations.rest.Post;
import org.androidannotations.annotations.rest.Rest;

// @Rest(rootUrl = "http://62.109.17.114/", converters = MessageConverter.class, interceptors = AuthInterceptor.class)
@Rest(rootUrl = "http://romanitalian.myjino.ru/", converters = MessageConverter.class, interceptors = AuthInterceptor.class)
public interface RestClient {
    @Get("/auth?login={login}&password={password}")
    AuthResult login(CharSequence login, CharSequence password);

    @Get("/auth?login={login}&password={password}&register=1")
    RegisterResult register(CharSequence login, CharSequence password);

    @Post("/categories/add?title={title}")
    Result addCategory(String title);

    @Get("/transactions")
    TransactionsResult getTransactions();

    @Post("transactions/add?sum={sum}&comment={comment}&category_id={category_id}&tr_date={date}")
    Result addTransaction(float sum, String comment, int category_id, String date);
}
