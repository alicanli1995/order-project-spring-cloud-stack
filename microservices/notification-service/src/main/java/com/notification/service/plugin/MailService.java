package com.notification.service.plugin;

import com.notification.service.domain.OrderEvent;

public interface MailService {
    void sendMail(OrderEvent event);
}
