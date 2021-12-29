package com.musec.musec.services;

import com.dropbox.core.DbxException;
import org.springframework.web.multipart.MultipartFile;

public interface CloudService {
    String uploadSong(MultipartFile song) throws RuntimeException;
    String uploadAlbumPic(MultipartFile pic) throws RuntimeException;
    String uploadProfilePic(MultipartFile pic) throws RuntimeException;
    String returnDirectLinkOfFile(String path) throws DbxException;
    void deleteFile(String filePath) throws DbxException;
}
