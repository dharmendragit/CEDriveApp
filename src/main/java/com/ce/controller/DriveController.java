package com.ce.controller;

import java.io.FileOutputStream;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.ce.entity.APIResponse;
import com.ce.exception.DriveException;
import com.ce.exception.ErrorCodes;
import com.ce.service.DriveService;
import com.google.api.services.drive.model.File;

/**
 * 
 * @author DKP This is Rest controller ,All rest APIs are exposed from here.
 *
 */
@RestController
public class DriveController {
	private static Logger logger = LoggerFactory.getLogger(DriveController.class);
	@Autowired
	DriveService driveService;

	// Test Api
	@GetMapping("/test")
	public String testApi() {
		logger.info("testApi() In::");
		return "Drive Api is working Fine!!";
	}
	
	//GetAll Folders Only
	@GetMapping("/getFolderList")
	public List<File> getFolderList() throws DriveException {
		List<File> files = null;
		logger.info("getFolderList() In::");
		try {
			files = driveService.getFolderList();
		} catch (DriveException de) {
			throw new DriveException(de.getErrorCode(), de.getErrorMessage());
		}
		logger.info("No of items found:" + files.size());
		logger.info("getFolderList() Exit::");
		return files;
	}

	// Get Names of the files and Folder of the given existing directory Name
	@GetMapping("/getDirectoryFiles")
	public List<File> getGoogleRootFoldersByName(@RequestParam("directoryName") String directoryName)
			throws DriveException {
		List<File> files = null, subDirectories = null;
		logger.info("getGoogleRootFoldersByName() In:directoryName:" + directoryName);
		try {
			if (!StringUtils.isEmpty(directoryName)) {// checking directory name
														// null or empty

				files = driveService.getGoogleRootFoldersByName(directoryName);
				if (files.size() > 0) {
					subDirectories = driveService.getGoogleSubFolder(files.get(0).getId());
				} else {
					throw new DriveException(ErrorCodes.DIRECTORY_NAME_DOES_NOT_EXIST);
				}
			} else {
				throw new DriveException(ErrorCodes.DIRECTORY_NAME_CANT_BE_EMPTY);
			}
		} catch (DriveException de) {
			throw new DriveException(de.getErrorCode(), de.getErrorMessage());
		}
		logger.info("No of items found:" + subDirectories.size());
		return subDirectories;
	}

	@GetMapping("/getAllfilesAndFolder")
	public List<File> getAllfilesAndFolder() throws DriveException {
		List<File> files = null, subDirectories = null;
		logger.info("getAllfilesAndFolder() In::");
		try {

			files = driveService.getGoogleAllFilesAndFolders();

		} catch (DriveException de) {
			throw new DriveException(de.getErrorCode(), de.getErrorMessage());
		}
		logger.info("No of items found:" + files.size());
		logger.info("getAllfilesAndFolder() Exit::");
		return files;
	}

	@GetMapping("/downloadfFile")
	public ResponseEntity<APIResponse> downloadfFile(@RequestParam("fileOrfolderid") String fileOrfolderid)
			throws DriveException {
		ResponseEntity<APIResponse> res = null;
		logger.info("downloadfFile() In::fileOrfolderid:" + fileOrfolderid);
		try {
			APIResponse response = null;
			if (!StringUtils.isEmpty(fileOrfolderid)) {// checking File Or //
														// directory name null
				// or empty
				driveService.downloadFile(fileOrfolderid);
				response = new APIResponse("SUCCESS", ErrorCodes.SUCCESS, "File Downloaded Successfully");
				res = new ResponseEntity<APIResponse>(response, HttpStatus.OK);
			} else {
				throw new DriveException(ErrorCodes.FILE_OR_DIRECTORY_NAME_CANT_BE_EMPTY);
			}
		} catch (DriveException de) {
			throw new DriveException(de.getErrorCode(), de.getErrorMessage());
		}
		return res;
	}

	@PostMapping("/uploadfFile")
	public ResponseEntity<APIResponse> uploadfFile(@RequestParam("googleFolderIdParent") String googleFolderIdParent,
			@RequestParam("contentType") String contentType, @RequestParam("customFileName") String customFileName,
			@RequestParam("file") MultipartFile uploadFile) throws DriveException {
		logger.info("uploadfFile() In::");
		ResponseEntity<APIResponse> res = null;
		try {
			APIResponse response = null;
			java.io.File convFile = new java.io.File(uploadFile.getOriginalFilename());
			convFile.createNewFile();
			FileOutputStream fos = new FileOutputStream(convFile);
			fos.write(uploadFile.getBytes());
			fos.close();

			driveService.uploadFile(googleFolderIdParent, contentType, customFileName, convFile);
			response = new APIResponse("SUCCESS", ErrorCodes.SUCCESS, "File Uploaded Successfully");
			res = new ResponseEntity<APIResponse>(response, HttpStatus.OK);
		} catch (DriveException de) {
			throw new DriveException(de.getErrorCode(), de.getErrorMessage());
		} catch (Exception e) {
			throw new DriveException(ErrorCodes.FILE_READING_ERROR, e.getMessage());
		}
		logger.info("uploadfFile() Exit::");
		return res;
	}
	@GetMapping("/authenticate")
	public ResponseEntity<APIResponse> authenticate()
			throws DriveException {
		ResponseEntity<APIResponse> res = null;
		APIResponse response = null;
		logger.info("authenticate() In::");
		try {
				driveService.authenticate();
				response = new APIResponse("SUCCESS", ErrorCodes.SUCCESS, "Authenticated Successfully!!");
				res = new ResponseEntity<APIResponse>(response, HttpStatus.OK);
		} catch (DriveException de) {
			response = new APIResponse("Failed", de.getErrorCode(), de.getErrorMessage());
			res = new ResponseEntity<APIResponse>(response, HttpStatus.UNAUTHORIZED);
	}
		return res;
	}
	
}
