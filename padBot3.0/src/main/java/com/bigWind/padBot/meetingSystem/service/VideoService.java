package com.bigWind.padBot.meetingSystem.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class VideoService {

    @Value("${video.storage.location}")
    private String storageLocation;

    public Path getVideoPath(String fileName) {
        return Paths.get(storageLocation, fileName);
    }
}

