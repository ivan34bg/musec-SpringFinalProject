package com.musec.musec.services;

import org.springframework.web.multipart.MultipartFile;

public interface cloudService {
    String uploadSong(MultipartFile song) throws Exception;
    String uploadAlbumPic(MultipartFile pic) throws Exception;
}
