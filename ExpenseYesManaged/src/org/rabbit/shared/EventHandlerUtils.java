package org.rabbit.shared;

import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.ui.TextBox;

public class EventHandlerUtils {

	private static class EventHandlerHolder {

		public static EventHandlerUtils eventHandlerUtils = new EventHandlerUtils();
	}

	private EventHandlerUtils() {

	}

	public static EventHandlerUtils getInstance() {
		return EventHandlerHolder.eventHandlerUtils;
	}

	private KeyUpHandler keyUpHandler = new KeyUpHandler() {

		@Override
		public void onKeyUp(KeyUpEvent event) {
			// TODO Auto-generated method stub
			String input = ((TextBox) event.getSource()).getText();
			if (!input.matches("[0-9.]*")) {
				// show some error
				input = input.replaceAll("[a-zA-Z]", "");

				((TextBox) event.getSource()).setText(input);
				return;
			}
		}

	};

	public KeyUpHandler getKeyUpHandler() {
		return keyUpHandler;
	}

}
