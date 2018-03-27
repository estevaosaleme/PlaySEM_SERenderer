package br.ufes.inf.lprm.sensoryeffect.renderer.service.websocket;

import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

public class SERendererWebSocketServlet extends WebSocketServlet {
	private static final long serialVersionUID = -4075779848130709558L;

	@Override
    public void configure(WebSocketServletFactory factory)
    {
        factory.register(SERendererWebSocketServletService.class);
    }
}