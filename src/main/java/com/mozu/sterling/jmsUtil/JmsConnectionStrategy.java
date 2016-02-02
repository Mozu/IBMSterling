package com.mozu.sterling.jmsUtil;

import javax.jms.JMSException;

import com.mozu.sterling.model.Setting;

public interface JmsConnectionStrategy {
	JmsResourceSetting getJmsResourceSettings(Setting setting, Integer tenantId)
			throws JMSException;
}
