package com.musec.musec.services.implementations;

import com.dropbox.core.DbxException;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.musec.musec.services.cloudService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class cloudServiceImpl implements cloudService {
    private final DbxClientV2 client;

    public cloudServiceImpl(DbxClientV2 client) {
        this.client = client;
    }

    @Override
    public String uploadSong(MultipartFile song) throws RuntimeException{
        try {
            FileMetadata meta = client
                    .files()
                    .uploadBuilder(String.format("/songs/%s", song.getOriginalFilename()))
                    .uploadAndFinish(song.getInputStream());
            return meta.getPathDisplay();
        } catch (DbxException | IOException e) {
            throw new RuntimeException("Error: Couldn't upload the song.");
        }
    }

    @Override
    public String uploadAlbumPic(MultipartFile pic) throws RuntimeException {
        try {
            FileMetadata meta = client
                    .files()
                    .uploadBuilder(String.format("/album-pictures/%s", pic.getOriginalFilename()))
                    .uploadAndFinish(pic.getInputStream());
            return meta.getPathDisplay();
        } catch (DbxException | IOException e) {
            throw new RuntimeException("Error: Couldn't upload the album picture.");
        }
    }

    @Override
    public String uploadProfilePic(MultipartFile pic) throws RuntimeException {
        try {
            FileMetadata meta = client
                    .files()
                    .uploadBuilder(String.format("/profile-pictures/%s", pic.getOriginalFilename()))
                    .uploadAndFinish(pic.getInputStream());
            return meta.getPathDisplay();
        } catch (DbxException | IOException e) {
            throw new RuntimeException("Error: Couldn't upload the album picture.");
        }
    }

    @Override
    public String returnDirectLinkOfFile(String path) throws DbxException {
        return client.sharing().createSharedLinkWithSettings(path).getUrl().replace("dl=0", "raw=1");
    }

    @Override
    public void deleteFile(String filePath) throws DbxException {
        client.files().deleteV2(filePath);
    }
}
