/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package java314tushinsky_file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Sergii.Tushinskyi
 */
public class Java314Tushinsky_File {
    private static final Scanner scanner = new Scanner(System.in);
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        try {
//            System.out.println("Анализ файла");
//            fileAnalise();
            
//            System.out.println("Поиск и замена");
//            findAndReplace();
//            
//            System.out.println("Запись данных в файл");
//            writeDataToFile();
//            
            System.out.println("Поиск запрещённых слов в файле");
            searchForbiddenWords();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private static void fileAnalise() throws IOException {
        // анализ файла и подсчёт количества букв, чисел и знаков препинания
        System.out.println("Введите путь к файлу:");
        String filename = scanner.next();
        File file = new File(filename);
        System.out.println("exist = " + file.exists());
        if (file.exists()) {
            // если файл по заданному пути существует
            char[] numString = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};// массив чисел
            char[] punctuation = {'.', ',', ';', ':', '?', '!',};// массив знаков препинания
            char[] specialChar = {'~', '`', '@', '#', '$', '%', '^', '&', '*', '(', ')', '-', '_',
                    '=', '+', '/', '|','\''};// массив спец символов
            FileInputStream fis = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(fis,"Windows-1251");
            // начальные значения для подсчёта количества символов, знаков препинания, чисел и спецсимволов
            int charCount = 0;
            int punctuationCount = 0;
            int specCount = 0;
            int numCount = 0;
            try (BufferedReader reader = new BufferedReader(isr)) {
                String line;
                // читаем данные
                while((line = reader.readLine()) != null) {
                    // анализируем строку
                    charCount += line.length();//  общее количество символов в строке
                    for (int i = 0; i < line.length(); i++) {
                        char c = line.charAt(i);// получаем символ
                        boolean find = false;
                        for (char ch : punctuation) {
                            if (c == ch) {
                                punctuationCount ++;
                                find = true;
                                break;
                            }
                        }
                        if (!find) {
                            for (char ch : numString) {
                                if (c == ch) {
                                    numCount ++;
                                    find = true;
                                    break;
                                }
                            }
                            if (!find) {
                                for (char ch : specialChar) {
                                    if (c == ch) {
                                        specCount ++;
                                        break;
                                    }
                                }
                                
                            }
                        }
                    }
                }
            }
            // выводим информацию
            System.out.println("Общее количество символов в файле =" + 
                    charCount + ", количество букв =" + (charCount -
                    punctuationCount - numCount - specCount) + 
                    ", количество цифр =" + numCount + ", количество знаков препинания =" +
                    punctuationCount + ", количество спецсимволов =" + specCount);
        }
    }

    private static void findAndReplace() throws IOException {
        System.out.println("Введите путь к файлу:");
        String filename = scanner.next();
        File file = new File(filename);
        if (file.exists()) {
            // если файл по заданному пути существует
            System.out.println("Искомое слово:");
            String searchString = scanner.next();// искомое слово
            System.out.println("Слово для замены:");
            String replaceString = scanner.next();// слово для замены
            FileInputStream fis = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(fis, "Windows-1251");
            BufferedReader reader = new BufferedReader(isr);
            String line;
            int count = 0;// количество замен
            // читаем данные
            while ((line = reader.readLine()) != null) {
                boolean find = true;// флаг наличия искомого слова в считанной строке
                String string = line;// начальная позиция поиска
                while(find) {
                    find = string.contains(searchString);
                    if(find) {
                        // если строка найдена
                        string = string.replaceFirst(searchString, replaceString);
                        count++;// увеличиваем счётчик
                    }
//                    System.out.println(count);
                }
            }
            System.out.println("Слово <" + searchString + "> заменено на " + "<" + 
                    replaceString + "> " + count + " раз");
        }
    }

    private static void writeDataToFile() throws IOException {
        List<File> fileList = new ArrayList<>();
        // список в который будем читать байты из файлов
        for(int i = 0; i < 4; i++) {
            System.out.println("Введите путь к файлу:");
            String filename = scanner.next();
            File file = new File(filename);
            fileList.add(file);
        }
        FileOutputStream out;
        out = new FileOutputStream(fileList.get(3), true);
        // читаем первые 3 файла
        for (int i = 0; i < 3; i++) {
            File file = fileList.get(i);
            if (file.exists()) {

                try (InputStream in = Files.newInputStream(file.toPath());) {
                    
                    int count = 0;// счётчик байт
                    int byteReads;// количество прочитанных байт
                    // читаем файл
                    byte[] buffer = new byte[8 * 1024];// массив байт для чтения
                    while((byteReads = in.read(buffer)) != -1) {
                        count += byteReads;// увеличиваем счётчик байт
                        out.write(buffer, 0, byteReads);// записываем байты в файл
                    }
                    System.out.println("Файл <" + file.getName() + 
                            ">, количество байт=" + count);
                    in.close();// закрываем входной поток
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        }
        out.close();
    }
    
    private static void searchForbiddenWords() throws FileNotFoundException, 
            UnsupportedEncodingException, IOException {
        System.out.println("Введите путь к файлу:");
        String filename = scanner.next();
        File file = new File(filename);
        StringBuilder builder = new StringBuilder();
        if (file.exists()) {
            System.out.println("Введите перечень запрещённых слов, разделяя их точкой с запятой:");
            String[] forbidden = scanner.next().split(";");// массив запрещённых слов
            System.out.println("Введите путь к новому файлу:");
            String newfilename = scanner.next();
            File newfile = new File(newfilename);
            if(!newfile.exists()) {
                newfile.createNewFile();
            }
            // читаем заданный файл
//            FileInputStream fis = new FileInputStream(file);
            FileReader fr = new FileReader(file);
            try (BufferedReader reader = new BufferedReader(fr)) {
                String line;
                HashMap<String, Integer> forbiddenMap = new HashMap<>(forbidden.length);
                // читаем данные
                while ((line = reader.readLine()) != null) {
                    
                    for (String forbidden1 : forbidden) {
                        System.out.println("forbidden=" + forbidden1);
                        boolean find = true;// флаг наличия искомого слова в считанной строке
                        String string = line;// приводим строку к нижнему регистру
                        string = string.toLowerCase();// приводим строку к нижнему регистру
//                        System.out.println("line: " + line + ", string=" + string);
                        while (find) {
                            find = string.contains(forbidden1);

                            if (find) {
                                // если строка найдена
                                int pos = string.indexOf(forbidden1);// позиция найденной подстроки
                                string = string.replaceFirst(forbidden1, "");
                                int value;
                                if(forbiddenMap.containsKey(forbidden1)) {
                                    value = forbiddenMap.get(forbidden1);
                                } else {
                                    value = 0;
                                }
                                value++;
                                forbiddenMap.put(forbidden1, value);
                                // получаем подстроку для замены
                                String subString = line.substring(pos, pos + forbidden1.length());
                                line = line.replaceFirst(subString, "");// замена в исходной строке
                            }
                        }
                        
                    }
                    builder.append(line).append("\n");
                }
                System.out.println("найденные и удаленные слова:");
                System.out.println(forbiddenMap);
            }
//            System.out.println("builder=" + builder);
            try ( // записываем новый файл
                    FileWriter out = new FileWriter(newfile)) {
                out.write(builder.toString());
            }
            
        }
    }
}
