package com.example.interviewday.models;

import java.util.ArrayList;
import java.util.List;

/**
 * คลาสสำหรับสร้าง Mock Data ทดสอบระบบทั้งหมด
 * อ้างอิงข้อมูลจากตารางแผนงาน (Level, Item, MiniGame, Quest)
 */
public class MockDataGenerator {

    public static void main(String[] args) {

        System.out.println("===== Mock Data: Level =====");
        List<Level> levels = new ArrayList<>();
        levels.add(new Level1());
        levels.add(new Level2());
        levels.add(new Level3());
        
        for (Level lvl : levels) {
            lvl.unlock();
            lvl.startLevel();
            lvl.completeLevel();
            System.out.println("--------------------");
        }

        System.out.println("\n===== Mock Data: Item (อ้างอิงตามตาราง) =====");
        List<Item> items = new ArrayList<>();
        // ด่าน 1
        items.add(new NormalItem(1, "Dev Laptop", "โน้ตบุ๊กติดสติ๊กเกอร์รวมเทคโนโลยี"));
        items.add(new SecretItem(101, "OOP Secret Flashcard", "ไอเทมลับด่าน 1", "เก็บครบเพื่อปลด True Ending"));
        
        // ด่าน 2
        items.add(new NormalItem(2, "Ethernet Cable", "สาย LAN"));
        items.add(new SecretItem(102, "Special Microcontroller Board", "ไอเทมลับด่าน 2", "บอร์ดทดลองคอมพิวเตอร์"));
        
        // ด่าน 3
        items.add(new NormalItem(3, "System Architecture Diagram", "ผังโครงสร้างระบบ"));
        items.add(new SecretItem(103, "Executive Recommendation Letter", "ไอเทมลับด่าน 3", "จดหมายรับรองจากผู้บริหาร"));

        for (Item item : items) {
            item.collect();
        }

        System.out.println("\n===== Mock Data: MiniGame (โจทย์จากตาราง) =====");
        List<MiniGame> games = new ArrayList<>();
        
        // มินิเกมด่าน 1 (OOP Concepts)
        games.add(new ListeningGame(1, "OOP Concept: Encapsulation", "bundling data and methods together while hiding internal details"));
        
        // มินิเกมด่าน 2 (Hardware)
        games.add(new ListeningGame(2, "Hardware: Microcontroller", "compact integrated circuit designed to govern a specific operation"));
        
        // มินิเกมด่าน 3 (Interview)
        games.add(new SpeakingGame(3, "Interview: Introduction", "I am a Computer Engineering graduate passionate about software development"));

        // ทดสอบตรวจคำตอบ
        games.get(0).checkAnswer("bundling data and methods together while hiding internal details");
        games.get(2).checkAnswer("I am a Computer Engineering graduate passionate about software development");

        System.out.println("\n===== Mock Data: Quest =====");
        List<Quest> quests = new ArrayList<>();
        quests.add(new MainQuest(1, "รวบรวมความรู้ OOP", 2));
        quests.add(new SideQuest(2, "ตามหา Flashcard ลับ", "OOP Secret Flashcard"));
        
        for (Quest q : quests) {
            q.updateProgress();
            q.completeQuest();
        }

        System.out.println("\n===== Mock Data: ฉากล้มเหลวระหว่างด่าน =====");
        Level2 stuckLevel = new Level2();
        System.out.println("เนื้อเรื่องเปิดด่าน: " + stuckLevel.getIntroStory());
        stuckLevel.triggerFail();

        System.out.println("\n===== Mock Data: ฉากล้มเหลวเพราะหมดเวลาหาของ (ด่าน 1) =====");
        Level1 timedOutLevel = new Level1();
        timedOutLevel.unlock();
        timedOutLevel.startLevel();
        // จำลองว่าผู้เล่นยังหาไอเทมไม่ครบ แล้วเวลาผ่านไปจนหมดเวลา (Time.LEVEL_TIME = 600 วินาที)
        boolean failedByTime = timedOutLevel.updateTime(Time.LEVEL_TIME + 1);
        System.out.println("แพ้เพราะหมดเวลาหรือไม่: " + failedByTime);

        System.out.println("\n===== Mock Data: หาของครบทันเวลา (ด่าน 1) =====");
        Level1 successLevel = new Level1();
        successLevel.unlock();
        successLevel.startLevel();
        // จำลองว่าผู้เล่นเก็บไอเทมที่ต้องใช้ของด่านนี้ครบก่อนหมดเวลา
        for (Item req : successLevel.getRequiredItems()) {
            req.collect();
        }
        boolean failedEvenThoughFound = successLevel.updateTime(300);
        System.out.println("แพ้เพราะหมดเวลาหรือไม่ (หาครบแล้ว): " + failedEvenThoughFound);
        if (successLevel.isAllItemsFound()) {
            successLevel.completeLevel();
        }

        System.out.println("\n===== Mock Data: Ending =====");
        Ending normalEnding = new NormalEnding(1);
        Ending trueEnding = new TrueEnding(2);


        // จำลองสถานการณ์ที่ 2: ผ่านแค่ 1 ด่าน แล้วล้มเหลวกลางทาง
        System.out.println("\n--- ผลลัพธ์ฉากจบ (ผู้เล่นล้มเหลวกลางทาง) ---");
        normalEnding.trigger(1, 0, 0);
        trueEnding.trigger(1, 0, 0);

    }
}
