package com.seungho.reactor;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.File;
import java.util.List;

public class ServerInitializer {

  public void startServer() {
    int port = 6000;
    System.out.println("Server ON : " + port);

    Reactor reactor = new Reactor(port);

    try {
      Serializer serializer = new Persister();
      File source = new File("HandlerList.xml");
      ServerListData serverList = serializer.read(ServerListData.class, source);

      for (HandlerListData handlerListData : serverList.getServer()) {
        if ("server1".equals(handlerListData.getName())) {
          List<HandlerData> handlerList = handlerListData.getHandler();
          for (HandlerData handler : handlerList) {
            try {
              reactor.registerHandler(
                      handler.getHeader(),
                      (EventHandler) Class.forName("com.seungho.reactor." + handler.getHandler()).newInstance()
              );
            } catch (InstantiationError e) {
              e.printStackTrace();
            } catch (IllegalAccessException e) {
              e.printStackTrace();
            } catch (ClassNotFoundException e) {
              e.printStackTrace();
            }
          }
          break;
        }

      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    reactor.startServer();
  }

}
