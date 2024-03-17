import java.io.*;
import java.nio.file.Files;
import java.util.*;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {


        try {
//            System.out.println("Анализ файла");
//            fileAnalise();
//            System.out.println("Поиск и замена");
//            findAndReplace();
            System.out.println("Запись данных в файл");
            writeDataToFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private static void fileAnalise() throws IOException {
        // анализ файла и подсчёт количества букв, чисел и знаков препинания
        System.out.println("Введите путь к файлу:");
        String filename = scanner.next();
        File file = new File(filename);
        if (file.exists()) {
            // если файл по заданному пути существует
            char[] numString = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};// массив чисел
            char[] punctuation = {'.', ',', ';', ':', '?', '!',};// массив знаков препинания
            char[] specialChar = {'~', '`', '@', '#', '$', '%', '^', '&', '*', '(', ')', '-', '_',
                    '=', '+', '/', '|','\''};// массив спец символов
            FileInputStream fis = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(fis,"Windows-1251");
            BufferedReader reader = new BufferedReader(isr);
            String line;
            // начальные значения для подсчёта количества символов, знаков препинания, чисел и спецсимволов
            int charCount = 0;
            int punctuationCount = 0;
            int specCount = 0;
            int numCount = 0;
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
                System.out.println(line.replaceFirst("Задача", "Задание"));
                System.out.println();
            }
            reader.close();
            // выводим информацию
            System.out.println("Общее количество символов в файле =" + charCount + ", количество букв =" + (charCount -
                    punctuationCount - numCount - specCount) + ", количество цифр =" + numCount + ", количество знаков препинания =" +
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
            System.out.println("Слово <" + searchString + "> заменено на " + "<" + replaceString + "> " + count + " раз");
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
        FileWriter out;
        out = new FileWriter(fileList.get(3), true);
        // читаем первые 3 файла
        for (int i = 0; i < 3; i++) {
            File file = fileList.get(i);
            if (file.exists()) {

                try {
                    InputStream in = Files.newInputStream(file.toPath());
                    int count = 0;
                    int byteReads;
                    // читаем файл
                    byte[] buffer = new byte[8 * 1024];// массив байт для чтения
                    while((byteReads = in.read(buffer)) != -1) {
                        count += byteReads;
                        out.write(byteReads);
                    }
                    count += byteReads;
                    System.out.println("Файл <" + file.getName() + ">, количество байт=" + count);
                    in.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        }
        out.close();
    }
}