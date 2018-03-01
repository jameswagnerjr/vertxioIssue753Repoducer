package com.jameswagnerjr.vertxIssue753Repoducer;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.CookieHandler;
import io.vertx.ext.web.handler.SessionHandler;
import io.vertx.ext.web.sstore.LocalSessionStore;
import io.vertx.ext.web.sstore.SessionStore;

public class Main extends AbstractVerticle {
    @Override
    public void start() throws Exception {
        super.start();

        HttpServer Server = vertx.createHttpServer();

        Router router = Router.router(vertx);

        router.route().handler(CookieHandler.create());
        SessionStore sessionStore = LocalSessionStore.create(vertx);
        router.route().handler(SessionHandler.create(sessionStore));

        router.route("/a").handler(a -> {
            a.response().end("You have a cookie. Go to page b now.");
        });

        router.route("/b").handler(b -> {
            b.response().setStatusCode(500).end("You no longer have a cookie. Why? This page errored.");
        });

        Server.requestHandler(router::accept);

        Server.listen(8080);
    }
}
