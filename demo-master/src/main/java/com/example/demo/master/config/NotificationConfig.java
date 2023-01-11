package com.example.demo.master.config;

import de.codecentric.boot.admin.server.domain.entities.InstanceRepository;
import de.codecentric.boot.admin.server.notify.CompositeNotifier;
import de.codecentric.boot.admin.server.notify.Notifier;
import de.codecentric.boot.admin.server.notify.RemindingNotifier;
import de.codecentric.boot.admin.server.notify.filter.FilteringNotifier;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import java.time.Duration;
import java.util.Collections;
import java.util.List;

/**
 * <p>
 * <h2>简述</h2>
 * 	   <ol>无</ol>
 *   <h2>功能描述</h2>
 * 	   <ol>无</ol>
 *   <h2>修改历史</h2>
 *     <ol>无</ol>
 * </p>
 *
 * @author wangjisen
 * @version 1.0
 * @date 2023/1/10 17:07
 */

//@Configuration
public class NotificationConfig {

        private InstanceRepository instanceRepository;
        private ObjectProvider<List<Notifier>> provider;

        public NotificationConfig(InstanceRepository instanceRepository, ObjectProvider<List<Notifier>> provider) {
            this.instanceRepository = instanceRepository;
            this.provider = provider;
        }

        @Bean
        public FilteringNotifier filteringNotifier() {
            CompositeNotifier compositeNotifier = new CompositeNotifier(this.provider.getIfAvailable(Collections::emptyList));
            return new FilteringNotifier(compositeNotifier, this.instanceRepository);
        }

        @Bean
        @Primary
        public RemindingNotifier remindingNotifier() {
            RemindingNotifier remindingNotifier = new RemindingNotifier(filteringNotifier(), this.instanceRepository);
            //配置每隔多久提示
            remindingNotifier.setReminderPeriod(Duration.ofMinutes(1));
            //配置每隔多久检查
            remindingNotifier.setCheckReminderInverval(Duration.ofSeconds(10));
            return remindingNotifier;
        }

}
