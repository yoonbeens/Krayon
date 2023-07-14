package com.krayon.backend.socket;

import com.krayon.backend.socket.service.ConversionJson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.BinaryWebSocketHandler;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import javax.websocket.server.ServerEndpointConfig;
import java.io.IOException;
import java.util.*;

@Service
@Slf4j

@ServerEndpoint("/socket/chatt")
public class WebSocketChat {
    //이페이지에 접속해있는 유저 세션을 담은 리스트 (Set)
    private static Set<Session> clients = Collections.synchronizedSet(new HashSet<Session>());

    //데이터값을 JSON 형태로 변환해주는 클래스 선언
    ConversionJson c = new ConversionJson();
    

    @OnOpen
    public void onOpen(Session session) throws IOException {
        log.info("open session : {}, clients={}", session.toString(), clients);
        Map<String, List<String>> res = session.getRequestParameterMap();
//        String id = res.get("id").get(0);
        String id = "1234";
        log.info("시작");
//        log.info(session.getId());
//        log.info(session.getContainer().toString());
//        log.info(session.getOpenSessions().toString());
//        log.info("메세지 핸들러"+session.getMessageHandlers().toString());
//
//
//        log.info(session.getUserProperties().toString());
//        log.info(session.getRequestURI().toString());
//        log.info(session.getPathParameters().toString());
//        log.info(session.getId());
//        log.info(session.getId());
        if(!clients.contains(session)) {
            clients.add(session);
            log.info("session open : {}", session);
        }else{
            log.info("이미 연결된 session");
        }
        log.info("res={}", res);
        log.info(Arrays.toString(clients.toArray()));
        System.out.println("id = " + id);
        String conversion = c.conversion("open",clients,id,"시스템");





        for( Session s :clients){
            s.getBasicRemote().sendText(conversion);
        }
    }

    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        log.info("receive message : {}", message);

        for (Session s : clients) {
            log.info("send data : {}", message);
            s.getBasicRemote().sendText(message);

        }
    }
    @OnMessage



    @OnClose
    public void onClose(Session session) {
        log.info("session close : {}", session);
//        clients.
//        Map<String, List<String>> map = session.getRequestParameterMap();
//        String id = map.get("id").get(0);
        clients.remove(session);
        log.info(clients.toString());
//        String message = c.conversion("close",clients, id, "시스템");

        for (Session s : clients) {
//            log.info("send data : {}", session.get);

//            try {
//                s.getBasicRemote().sendText(message);
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
        }
    }
}