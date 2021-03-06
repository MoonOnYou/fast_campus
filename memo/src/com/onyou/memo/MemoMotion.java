package com.onyou.memo;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;

/**
 *간편 메모장 함수들을 정의해 놓은 class
 */

public class MemoMotion {

    public static final String MEMO_DIR = "./temp/memo";    //지정해 놓은 디렉토리에 저장
    public static final String EXIT = "/종료";              // 종료 컨맨드 지정해 놓기
    String filename;
    Path path;
    File file = new File(MEMO_DIR);

    public MemoMotion(){                                    //생성자에서 저장할 디렉토리 만들어 주기
        File dir = new File(MEMO_DIR);
        if(!dir.exists()) { dir.mkdirs(); }
    }
    public void showMenu(){
        println("1.쓰기 \n2.읽기 \n3.수정 \n4.삭제 \n0.종료 \n");
        println("------- 0번 ~ 4번의 메뉴 중 하나를 입력해 주세요 -------");

    }
    public void showList(){
        String fileList[] =file.list();
        for(String list:fileList) println(list);
    }

    public void write(Scanner sc){
        println("------- 쓰기 모드입니다 -------");

        StringBuilder content = new StringBuilder();         //전체글을 저장할 변수

        while(true) {
            String line = sc.nextLine();
            if (line.equals(EXIT)) { break; } else { content.append(line+"\n"); }
        }
        if(!content.toString().equals("")){
            long now = System.currentTimeMillis();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_hhmmss");
            filename = sdf.format(now)+".txt";
            path = Paths.get(MEMO_DIR,filename);
            try {
                Files.write(path,content.toString().getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
            println("메모를 등록하였습니다.");
        }
    }

    public void read(Scanner sc){
        if(!file.exists()) { println("읽을 목록이 존재하지 않습니다."); return; }
        println("------- 읽기 모드입니다 -------");
        println(" 읽을 파일의 제목을 입력해 주세요 ");
        showList();
        String readFile = sc.nextLine();

        try {
            path = Paths.get(MEMO_DIR, readFile);
            List<String> readAllLines = Files.readAllLines(path);
            for(String lines: readAllLines)
                println(lines);
        } catch (IOException e) {
            println("파일을 읽지 못했습니다.");
        }



    }
    public void change(Scanner sc){
        if(!file.exists()) { println("목록이 존재하지 않습니다."); return; }
        println("------- 수정 모드입니다 -------");
        println(" 수정 할 파일의 제목을 입력해 주세요 ");
        showList();
        String ch = sc.nextLine();
        try {
            path = Paths.get(MEMO_DIR, ch);
            List<String> readAllLines = Files.readAllLines(path);

            StringBuilder content = new StringBuilder();         //전체글을 저장할 변수
            for(String line: readAllLines) {
                println(line);
                content.append(line + "\n");
            }

            println("수정할 글자를 입력해 주세요");
            String before = sc.nextLine();
            println("어떻게 수정 할 것인지 입력해 주세요");
            String after = sc.nextLine();

            String result = content.toString().replaceAll(before, after);

            println(result);
            Files.write(path, result.getBytes());

            println("수정이 완료 되었습니다.");

        } catch (IOException e) {
            println("파일을 읽지 못했습니다.");
        }





    }
    public void remove(Scanner sc){
        if(!file.exists()) { println("목록이 존재하지 않습니다."); return; }
        println("------- 삭제 모드입니다 -------");
        println(" 삭제 할 파일의 제목을 입력해 주세요 ");
        showList();
        String removedFileName = sc.nextLine();

        try {
            path = Paths.get(MEMO_DIR, removedFileName);
            Files.delete(path);
        } catch (IOException e) {
            println("파일을 삭제하지 못했습니다.");
        }
    }

    public void println(Object obj){
        System.out.println( obj );
    }
}
