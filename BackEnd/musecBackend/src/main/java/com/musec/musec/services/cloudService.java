package com.musec.musec.services;

import org.springframework.web.multipart.MultipartFile;

public interface cloudService {
    String uploadSong(MultipartFile song) throws RuntimeException;
    String uploadAlbumPic(MultipartFile pic) throws RuntimeException;
    String uploadProfilePic(MultipartFile pic) throws RuntimeException;
}
