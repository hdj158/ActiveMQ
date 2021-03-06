package com.mq.controller;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.websocket.Extension;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;  

public class GetHttpSessionConfigurator extends ServerEndpointConfig.Configurator {
	@Override
    public <T> T getEndpointInstance(Class<T> clazz)
            throws InstantiationException {
        try {
            return clazz.newInstance();
        } catch (IllegalAccessException e) {
            InstantiationException ie = new InstantiationException();
            ie.initCause(e);
            throw ie;
        }
    }


    @Override
    public String getNegotiatedSubprotocol(List<String> supported,
            List<String> requested) {

        for (String request : requested) {
            if (supported.contains(request)) {
                return request;
            }
        }
        return "";
    }


    @Override
    public List<Extension> getNegotiatedExtensions(List<Extension> installed,
            List<Extension> requested) {

        List<Extension> result = new ArrayList<>();
        for (Extension request : requested) {
            if (installed.contains(request)) {
                result.add(request);
            }
        }
        return result;
    }


    @Override
    public boolean checkOrigin(String originHeaderValue) {
        return true;
    }

    @Override
    public void modifyHandshake(ServerEndpointConfig config, 
                                HandshakeRequest request, 
                                HandshakeResponse response)
    {
        HttpSession httpSession = (HttpSession)request.getHttpSession();
        config.getUserProperties().put(HttpSession.class.getName(),httpSession);
    }
}
