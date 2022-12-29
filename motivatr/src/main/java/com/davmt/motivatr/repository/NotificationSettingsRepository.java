package com.davmt.motivatr.repository;

import org.springframework.data.repository.CrudRepository;

import com.davmt.motivatr.model.NotificationSetting;

public interface NotificationSettingsRepository extends CrudRepository<NotificationSetting, Long> {
  NotificationSetting findByUserId(Long userId);
}