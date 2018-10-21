package com.ce.service;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ce.exception.DriveException;
import com.ce.exception.ErrorCodes;
import com.ce.test.util.DriveUtils;
import com.google.api.client.http.AbstractInputStreamContent;
import com.google.api.client.http.FileContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;

@Service
public class DriveServiceImpl implements DriveService {
	private static Logger logger = LoggerFactory.getLogger(DriveServiceImpl.class);

	@Override
	public File uploadFile(String googleFolderIdParent, String contentType, String customFileName,
			java.io.File uploadFile) throws DriveException {
		File file = null;
		try {
			AbstractInputStreamContent uploadStreamContent = new FileContent(contentType, uploadFile);

			File fileMetadata = new File();
			fileMetadata.setName(customFileName);

			List<String> parents = Arrays.asList(googleFolderIdParent);
			fileMetadata.setParents(parents);
			//
			Drive driveService = DriveUtils.getDriveService();

			file = driveService.files().create(fileMetadata, uploadStreamContent)
					.setFields("id, webContentLink, webViewLink, parents").execute();

		} catch (IOException IOE) {
			throw new DriveException(ErrorCodes.FILE_INPUT_OUTPUT_EXCEPTION, IOE.getLocalizedMessage());
		} catch (Exception ex) {
			throw new DriveException(ErrorCodes.INTERNAL_ERROR);
		}
		return file;
	}

	@Override
	public void downloadFile(String fileOrfolderid) throws DriveException {
		logger.info("downloadFile() In::fileOrfolderid:" + fileOrfolderid);
		try {
			Drive driveService = DriveUtils.getDriveService();
			OutputStream outputStream = new ByteArrayOutputStream();
			driveService.files().get(fileOrfolderid).executeMediaAndDownloadTo(outputStream);
			/*
			 * OutputStream outputStream = new ByteArrayOutputStream();
			 * driveService.files().export(fileOrfolderid,
			 * "text/plain").executeMediaAndDownloadTo(outputStream);
			 */ } catch (IOException IOE) {
			throw new DriveException(ErrorCodes.FILE_INPUT_OUTPUT_EXCEPTION, IOE.getLocalizedMessage());
		} catch (Exception ex) {
			throw new DriveException(ErrorCodes.INTERNAL_ERROR);
		}
	}

	@Override
	public List<File> getGoogleRootFoldersByName(String subFolderName) throws DriveException {
		List<File> list = new ArrayList<File>();
		try {
			logger.info("getGoogleRootFoldersByName() In:subFolderName:" + subFolderName);
			Drive driveService = DriveUtils.getDriveService();
			String pageToken = null;
			String googleFolderIdParent = null;

			String query = null;
			if (googleFolderIdParent == null) {
				query = " name = '" + subFolderName + "' " //
						+ " and mimeType = 'application/vnd.google-apps.folder' " //
						+ " and 'root' in parents";
			} else {
				query = " name = '" + subFolderName + "' " //
						+ " and mimeType = 'application/vnd.google-apps.folder' " //
						+ " and '" + googleFolderIdParent + "' in parents";
			}

			do {
				FileList result = driveService.files().list().setQ(query).setSpaces("drive") //
						.setFields("nextPageToken, files(id, name, createdTime)")//
						.setPageToken(pageToken).execute();
				for (File file : result.getFiles()) {
					list.add(file);
				}
				pageToken = result.getNextPageToken();
			} while (pageToken != null);
			//

		} catch (IOException IOE) {
			throw new DriveException(ErrorCodes.FILE_INPUT_OUTPUT_EXCEPTION, IOE.getLocalizedMessage());
		} catch (Exception ex) {
			throw new DriveException(ErrorCodes.INTERNAL_ERROR);
		}
		logger.info("File found:" + list.size());
		return list;
	}

	@Override
	public List<File> getGoogleFilesByName(String fileNameLike) throws DriveException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public File createGoogleFolder(String folderIdParent, String folderName) throws DriveException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<File> getGoogleSubFolder(String parentFolder) throws DriveException {
		List<File> files = null;
		try {
			logger.info("getGoogleSubFolder() In:parentFolder" + parentFolder);
			Drive driveService = DriveUtils.getDriveService();

			String pageToken = null;
			List<File> list = new ArrayList<File>();

			String query = null;
			if (StringUtils.isEmpty(parentFolder)) {
				query = " mimeType = 'application/vnd.google-apps.folder' " //
						+ " and 'root' in parents";
			} else {
				query = "  '" + parentFolder + "' in parents";
			}

			do {
				FileList result = driveService.files().list().setQ(query).setSpaces("drive") //
						// Fields will be assigned values: id, name, createdTime
						.setFields("nextPageToken, files(id, name, createdTime)")//
						.setPageToken(pageToken).execute();
				for (File file : result.getFiles()) {
					list.add(file);
				}
				pageToken = result.getNextPageToken();
			} while (pageToken != null);
			files = list;
		} catch (IOException IOE) {
			throw new DriveException(ErrorCodes.FILE_INPUT_OUTPUT_EXCEPTION, IOE.getLocalizedMessage());
		} catch (Exception ex) {
			throw new DriveException(ErrorCodes.INTERNAL_ERROR);
		}
		logger.info("File found:" + files.size());
		return files;
	}

	@Override
	public List<File> getGoogleAllFilesAndFolders() throws DriveException {
		List<File> files = null;
		try {
			logger.info("getGoogleAllFilesAndFolders() In::");
			Drive driveService = DriveUtils.getDriveService();

			String pageToken = null;
			List<File> list = new ArrayList<File>();
			do {
				FileList result = driveService.files().list().setFields("nextPageToken, files(id, name)")
						.setPageToken(pageToken).execute();
				for (File file : result.getFiles()) {
					list.add(file);
				}
				pageToken = result.getNextPageToken();
			} while (pageToken != null);
			files = list;
		} catch (IOException IOE) {
			throw new DriveException(ErrorCodes.FILE_INPUT_OUTPUT_EXCEPTION, IOE.getLocalizedMessage());
		} catch (Exception ex) {
			throw new DriveException(ErrorCodes.INTERNAL_ERROR);
		}
		logger.info("File found:" + files.size());
		logger.info("getGoogleAllFilesAndFolders() Exit::");
		return files;
	}

	@Override
	public List<File> getFolderList() throws DriveException {
		logger.info("getFolderList() In::");
		  List<File> files=null;
		try{
			String query1 = " mimeType = 'application/vnd.google-apps.folder' " //
					+ " and 'root' in parents";
	       
			Drive driveService = DriveUtils.getDriveService();
			 FileList result = driveService.files().list().setQ(query1).setFields("nextPageToken, files(id, name)").execute();
		        
			 files= result.getFiles();
		}catch (IOException IOE) {
			throw new DriveException(ErrorCodes.FILE_INPUT_OUTPUT_EXCEPTION, IOE.getLocalizedMessage());
		} catch (Exception ex) {
			throw new DriveException(ErrorCodes.INTERNAL_ERROR);
		}
		logger.info("getFolderList() Exit::");
		return files;
	}

	@Override
	public void authenticate() throws DriveException {
		logger.info("authenticate() In::");
		try{
	       
			Drive driveService = DriveUtils.getDriveService();
				
		}catch (FileNotFoundException FNFE) {
			throw new DriveException(ErrorCodes.AUTHENTICATION_FAILED, FNFE.getLocalizedMessage());
		}
		catch (IOException IOE) {
			throw new DriveException(ErrorCodes.FILE_INPUT_OUTPUT_EXCEPTION, IOE.getLocalizedMessage());
		} catch (Exception ex) {
			throw new DriveException(ErrorCodes.INTERNAL_ERROR);
		}
		logger.info("authenticate() Exit::");
		//return files;
	}

}
