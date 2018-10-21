package com.ce.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.ce.test.util.DriveUtils;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;

public class GetSubFolders {
 
    // com.google.api.services.drive.model.File
    public static final List<File> getGoogleSubFolders(String googleFolderIdParent) throws IOException {
 
        Drive driveService = DriveUtils.getDriveService();
 
        String pageToken = null;
        List<File> list = new ArrayList<File>();
 
        String query = null;
        if (googleFolderIdParent == null) {
            query = " mimeType = 'application/vnd.google-apps.folder' " //
                    + " and 'root' in parents";
        } else {
            /*query = " mimeType = 'application/vnd.google-apps.folder' " //
                    + " and ' " +"1UqFzNc0dL5Bbdjv79pV7OTEGPuIzCNSd"+" ' in parents";*/
    
        	query = " name = '" + "ABC" + "' " 
                    + " and mimeType = 'application/vnd.google-apps.folder' " ;
        	//query =" 'root' in parents and mimeType != 'application/vnd.google-apps.folder' and trashed = false";
        }
 
        /*do {
            FileList result = driveService.files().list().setQ(query).setSpaces("drive") //
                    // Fields will be assigned values: id, name, createdTime
                    .setFields("nextPageToken, files(id, name, createdTime)")//
                    .setPageToken(pageToken).execute();
            for (File file : result.getFiles()) {
                list.add(file);
            }
            pageToken = result.getNextPageToken();
        } while (pageToken != null);*/
        do {
            FileList result = driveService.files().list().setQ(query)//
                    // Fields will be assigned values: id, name, createdTime
                    .setFields("nextPageToken, files(id, name, createdTime)")//
                    .setPageToken(pageToken).execute();
            for (File file : result.getFiles()) {
                list.add(file);
            }
            pageToken = result.getNextPageToken();
        } while (pageToken != null);
        //
        return list;
    }
 
    // com.google.api.services.drive.model.File
    public static final List<File> getGoogleRootFolders() throws IOException {
        return getGoogleSubFolders("1UqFzNc0dL5Bbdjv79pV7OTEGPuIzCNSd");
    }
 
    public static void main(String[] args) throws IOException {
 
        List<File> googleRootFolders = getGoogleRootFolders();
        for (File folder : googleRootFolders) {
 
            System.out.println("Folder ID: " + folder.getId() + " --- Name: " + folder.getName());
        }
     
        Drive driveService = DriveUtils.getDriveService();
        String pageToken = null;
        FileList result = driveService.files().list()    
        		.setQ("'root' in parents and mimeType != 'application/vnd.google-apps.folder' and trashed = false")
        		.setSpaces("drive")
        		.setFields("nextPageToken, files(id, name, parents)")
        		.setPageToken(pageToken)  
        		.execute();
        
        }
 
}