package com.example.hospital.api.service;

/**
 * @author Shiqi
 */
public interface VideoDiagnoseService {

    public void online(int userId);

    public boolean offline(int userId);

    public void updateOpenFlag(int userId, boolean open);
}

