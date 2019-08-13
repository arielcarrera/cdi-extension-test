package com.github.arielcarrera.cdi.test.config;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.enterprise.inject.Vetoed;
import javax.enterprise.inject.spi.Bean;

import com.github.arielcarrera.cdi.test.FooInterface;

@Vetoed
public class ListenerRegistry implements Serializable {

    private static final long serialVersionUID = -3115166732077559919L;

    Map<String, Bean<FooInterface>> registroEntityOperationListeners = new HashMap<String, Bean<FooInterface>>();

    public Bean<FooInterface> getRegistro(String key) {
	return registroEntityOperationListeners.get(key);
    }

    public synchronized void putIfAbsent(String key, Bean<FooInterface> value) {
	registroEntityOperationListeners.putIfAbsent(key, value);
    }
}
