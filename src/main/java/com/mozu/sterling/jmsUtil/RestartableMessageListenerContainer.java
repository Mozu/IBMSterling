package com.mozu.sterling.jmsUtil;

import org.springframework.jms.listener.DefaultMessageListenerContainer;

public class RestartableMessageListenerContainer extends DefaultMessageListenerContainer {

	public boolean isNeedsRestart() {
		return isActive() && (getScheduledConsumerCount() - getPausedTaskCount() < 1);
	}

}
