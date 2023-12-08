package com.ohgiraffers.fileupload;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
public class FileUploadController {

    @RequestMapping(value = {"/","/main"})
    public String index(){
        return "main";
    }

    @PostMapping("single-file")
    public String singleFileUpload(@RequestParam MultipartFile singleFile, String singleFileDescription, Model model) {

        System.out.println("single file : "+singleFile);
        System.out.println("원본 파일 이름: "+singleFile.getOriginalFilename());
        System.out.println("input name: "+singleFile.getName());
//        System.out.println("원본 파일 객체: "+singleFile.getBytes()); // 주소값이 나옴 - 안에 파일 DNA 저장되어 있음
        System.out.println("원본 파일 사이즈: "+singleFile.getSize());

        // 파일을 저장할 경로 설정

        // 이미지 서버를 별도로 이용
        // FTP, Node.JS , S3 Bucket
        String root = "c:/upoad-files";
        String filePath = root+"/single";

        File dir = new File(filePath);

        if (!dir.exists()){
            dir.mkdirs(); // 파일 경로가 없으면 만드는 구문
        }

        String originFileName = singleFile.getOriginalFilename();
        String ext = originFileName.substring(originFileName.lastIndexOf("."));
        String savedName = UUID.randomUUID().toString().replace("-","")+ext;

        try{
            System.out.println("filePath ======================="+filePath);
            singleFile.transferTo(new File(filePath+"/"+savedName));
            model.addAttribute("message","파일업로드성공");
        }catch (IOException e){
            model.addAttribute("message", "파일업로드실패");
        }

        System.out.println("singleFileDescription : "+singleFileDescription);

        return "result";
    }

    @PostMapping("multi-file")
    public String multiFileUpload(@RequestParam List<MultipartFile> multipartFiles, String multiFileDescription, Model model){
        System.out.println("multiFiles : "+multipartFiles);
        System.out.println("multiFileDescription : "+multiFileDescription);

        String root = "c:/upload-file";
        String filePath = root+"/multi";

        File dir = new File(filePath);

        if (!dir.exists()){
            dir.mkdirs(); // 폴더 없으면 폴더 만드셈 - mkdir(폴더 하나) / mkdirs - 폴더 다수 make directory(s)
        }

        List<FileDTO> files = new ArrayList<>();

        try{
            for (MultipartFile file :
                    multipartFiles) {
                String originFileName = file.getOriginalFilename();
                String ext = originFileName.substring(originFileName.lastIndexOf("."));
                String savedName = UUID.randomUUID().toString().replace("-","")+ext;
                files.add(new FileDTO(originFileName,savedName,filePath,multiFileDescription));
                file.transferTo(new File(filePath, "/"+savedName));
            }
            model.addAttribute("message","다중 파일 업로드 성공");
        }catch (IOException e){
            e.printStackTrace();

            for (FileDTO file: files){
                new File(filePath+"/"+file.getSavedName()).delete();
            }
            model.addAttribute("message","다중 파일 업로드 실패");
        }
        return "result";
    }

}
