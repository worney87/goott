package org.rainbow.company.custMgmt.controller;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.rainbow.company.custMgmt.domain.AttachFileDTO;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
/*
 * MultipartFile의 메소드
 * 
 * String getname()                 파라미터의 이름 <input> 태그의 이름
 * String getOriginalFileName()     업로드 되는 파일의 이름
 * boolean isEmpty()                파일이 존재하지 않는 경우 true
 * long getSize()                   업로드 되는 파일의 크기
 * byte[] getBytes()                byte[]로 파일 데이터 반환
 * InputStream getInputStream()     파일 데이터와 연결된 InputStream
 * transferTo(File file)            파일의 저장 
 * 
 * */
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.log4j.Log4j;

@Log4j
@Controller
public class uploadController {
	
	private static final int ResponseEntity = 0;
	private static final int List = 0;
	private static final int AttachFileDTO = 0;
	private static final int Resource = 0;


	@GetMapping("/uploadForm")
	public String uploadForm() {
		log.info("upload Form");
		return "uploadForm";
	}
	
	
@PostMapping("/uploadFormAction")
public void uploadFormPost(MultipartFile[] uploadFile, Model model) {
	//uploadForm.jsp에서 multiple="multiple" 이기때문에 여러개 파일을 업로드 할 수 있다
	//그래서 MultipartFile[] 배열로 설정함!
	
	for(MultipartFile file : uploadFile) {
		log.info("--------------");
		log.info("upload File Name : " + file.getOriginalFilename());
		log.info("upload File Size : " + file.getSize());
	}
}

//파일 업로드 비동기 방식-------------------------------
@GetMapping("/uploadAsync")
public String uploadAsync() {
	log.info("uploadAsync");
	return "uploadAsync";
}


@ResponseBody 
@PostMapping(value="/uploadAsyncAction", produces = {
		MediaType.APPLICATION_JSON_VALUE})
public ResponseEntity<List<AttachFileDTO>> uploadAsyncPost(MultipartFile[] file, Model model) {
	List<AttachFileDTO> list = new ArrayList<AttachFileDTO>();
	
	log.info("파일 " + list);
	
	log.info("upload async post.........");
	//make Folder--------------------------
	
	File uploadPath = new File("C:\\upload",getFolder());
	log.info("uploadPath : " + uploadPath);
	
	if(!uploadPath.exists()) {
		uploadPath.mkdirs();
	}
	
	//make yyyy/MM/dd folder
	for(MultipartFile files : file) {
		
		//파일 정보를 담을 AttachFileDTO 객체 생성
		AttachFileDTO attachDTO = new AttachFileDTO();
		
		//log.info("upload Async post");
		//log.info("upload File Name : " + file.getOriginalFilename());
		//log.info("upload File Size : " + file.getSize());
		
		
		String uploadFileName = files.getOriginalFilename();
		uploadFileName = uploadFileName.substring(uploadFileName.lastIndexOf("\\")+1);
		log.info("only file name : " + uploadFileName);
		
		UUID uuid = UUID.randomUUID();
		uploadFileName = uuid.toString() + "_" + uploadFileName;
		//밀리초 단위를 랜덤값으로 사용함 = UUID
		
		
		// AttachFileDTO에 파일 정보 설정
		attachDTO.setFileName(uploadFileName);
		attachDTO.setUploadPath(getFolder());
		attachDTO.setUuid(uuid.toString());

		// 로그 추가
		log.info("파일이름: " + attachDTO.getFileName());
		log.info("경로: " + attachDTO.getUploadPath());
		log.info("UUID: " + attachDTO.getUuid());

		// AttachFileDTO 객체를 리스트에 추가
		list.add(attachDTO);
		
		//-------------------여기까지 attachDTO에 저장
		//attachDTO에 저장된 값을 list에 넣어주기
		
		
		File saveFile = new File(uploadPath, uploadFileName);
		
		// saveFile에 파일이 제대로 들어갔는지 로그로 출력
		log.info("saveFile 경로: " + saveFile.getAbsolutePath());

		try {
		    files.transferTo(saveFile);
		} catch (Exception e) {
		    log.error(e.getMessage());
		}
		
	}//end loop
	


return new ResponseEntity<List<AttachFileDTO>>(list,HttpStatus.OK ); 
}//end uploadAsyncPost()



//파일 다운로드 메소드
@GetMapping(value="/download", produces=MediaType.APPLICATION_OCTET_STREAM_VALUE)
@ResponseBody
public ResponseEntity<Resource> downloadFile(String fileName){
	log.info("download file..."+ fileName);
	Resource resource = new FileSystemResource("C:\\upload\\" + fileName);
	
	//get 방식이기 때문에
	//fileName을 던져줘야한다
	//어떻게?
	//쿼리스트링으로
	//즉, upload 폴더 안에 있는 파일의 이름을 쿼리스트링으로 던져주면 된다
	
	
	
	log.info("resource : " + resource);
	
	
	String resourceName = resource.getFilename();
	HttpHeaders headers = new HttpHeaders();
	
	try {
		headers.add("Content-Disposition",
				"attach; fileName=" + new String(resourceName.getBytes("utf-8"),
						"ISO-8859-1"));
		
	}catch (UnsupportedEncodingException e) {
		e.printStackTrace();
	}
	return new ResponseEntity<Resource>(resource, headers, HttpStatus.OK);
}

//파일 삭제
@PostMapping(value="/deleteFile", produces=MediaType.TEXT_PLAIN_VALUE)
@ResponseBody
public ResponseEntity<String> deleteFile(@RequestBody String fileName){
	log.info("호이 deleteFile" + fileName);
	
	File file = null;
	
	try {
		//본래는 file이 진짜 있는지 물어봐야 함 
		file = new File("C:\\upload\\" + URLDecoder.decode(fileName, "utf-8"));
		file.delete();
	}catch(Exception e) {
		e.printStackTrace();
		return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
	}
	
	return new ResponseEntity<String>("deleted" , HttpStatus.OK);
}


//오늘 날짜의 경로를 문자열로 생성
private String getFolder() {
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	Date date = new Date();
	String str = sdf.format(date);
	return str.replace("-", File.separator);
}


}
