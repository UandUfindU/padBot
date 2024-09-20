package com.bigWind.padBot.meetingSystem.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamResource;
import java.nio.file.StandardOpenOption;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRange;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.io.InputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import com.bigWind.padBot.meetingSystem.service.VideoService;

@RestController
public class VideoController {

    private final VideoService videoService;
     private static final Logger logger = LoggerFactory.getLogger(VideoController.class);
    public VideoController(VideoService videoService) {
        this.videoService = videoService;
    }

    @GetMapping("/video/{fileName}")
    public ResponseEntity<Resource> getVideo(@PathVariable String fileName, 
                                             @RequestHeader(value = "Range", required = false) String rangeHeader) {
        try {
            Path videoPath = videoService.getVideoPath(fileName);
            Resource videoResource = new UrlResource(videoPath.toUri());

            if (!videoResource.exists()) {
                return ResponseEntity.notFound().build();
            }

            long videoLength = Files.size(videoPath);
            HttpHeaders headers = new HttpHeaders();

            if (rangeHeader != null && rangeHeader.startsWith("bytes=")) {
                HttpRange httpRange = HttpRange.parseRanges(rangeHeader).get(0);
                long rangeStart = httpRange.getRangeStart(videoLength);
                long rangeEnd = httpRange.getRangeEnd(videoLength);
                long rangeLength = Math.min(rangeEnd - rangeStart + 1, videoLength);

                headers.add("Content-Range", "bytes " + rangeStart + "-" + rangeEnd + "/" + videoLength);
                headers.add(HttpHeaders.CONTENT_LENGTH, String.valueOf(rangeLength));
                headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE);

                InputStream inputStream = Files.newInputStream(videoPath, StandardOpenOption.READ);
                inputStream.skip(rangeStart); // 跳过到指定的字节位置

                return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                        .headers(headers)
                        .body(new InputStreamResource(inputStream));
            } else {
		logger.info("Video requested: {}", fileName);
                headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE);
                headers.add(HttpHeaders.CONTENT_LENGTH, String.valueOf(videoLength));
              
                return ResponseEntity.ok()
                        .headers(headers)
                        .body(videoResource);
            }
        } catch (IOException e) {
              logger.error("Error occurred while processing video request", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
