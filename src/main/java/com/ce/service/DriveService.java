package com.ce.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.ce.exception.DriveException;
import com.google.api.services.drive.model.File;
/**
 * 
 * @author DKP
 * This interface is used to declare required specifications to access Drive operations
 *
 */

@Service
public interface DriveService {
/**
 * 
 * @param googleFolderIdParent
 * @param contentType
 * @param customFileName
 * @param uploadStreamContent
 * @return
 * @throws DriveException
 */
public File uploadFile(String googleFolderIdParent, String contentType,String customFileName,  java.io.File uploadFile) throws DriveException ;

/**
 * 
 * @throws DriveException
 */
public void downloadFile(String fileOrfolderid)throws DriveException ;
/**
 * 
 * @param subFolderName
 * @return
 * @throws DriveException
 */
public  List<File> getGoogleRootFoldersByName(String subFolderName) throws DriveException;
/**
 * 
 * @param fileNameLike
 * @return
 * @throws DriveException
 */
public List<File> getGoogleFilesByName(String fileNameLike) throws DriveException;
/**
 * 
 * @param folderIdParent
 * @param folderName
 * @return
 * @throws DriveException
 */
public  File createGoogleFolder(String folderIdParent, String folderName) throws DriveException;
/**
 * 
 * @param parentFolder
 * @return
 * @throws DriveException
 */
public  List<File> getGoogleSubFolder(String parentFolder) throws DriveException;
/**
 * 
 * @return
 * @throws DriveException
 */
public  List<File> getGoogleAllFilesAndFolders() throws DriveException;
/**
 * 
 * @return
 * @throws DriveException
 */
public  List<File> getFolderList() throws DriveException;

public  void authenticate() throws DriveException;




}
